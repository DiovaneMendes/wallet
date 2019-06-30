package br.com.dbc.wallet.Component;

import br.com.dbc.wallet.DTO.MoedaDTO;
import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Simbolo;
import br.com.dbc.wallet.Enuns.Periodicidade;
import br.com.dbc.wallet.Exception.ApiRequestException;
import br.com.dbc.wallet.Repository.SimboloRepository;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

@Component
public class ContratacaoComponent {

    @Autowired
    public SimboloRepository simboloRepository;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private MoedaDTO buscarCotacaoMoedas(String siglas){
        RestTemplate buscador = new RestTemplate();
            return buscador.getForObject("http://data.fixer.io/api/latest?access_key=18daa866b964e604c842275095a07527&symbols=".concat(siglas), MoedaDTO.class);
    }

    public Contratacao gerarContratacao(ServicoDTO servicoDTO) throws ApiRequestException {
        try {
            Contratacao contratacao = gerarValoresContratacao(servicoDTO.getValor(), servicoDTO.getSimbolo());

            contratacao.setValorBase(servicoDTO.getValor());
            contratacao.setPeriodicidade(servicoDTO.getPeriodicidade());
            contratacao.setSimbolo(servicoDTO.getSimbolo());

            if( servicoDTO.getDataContratacao() != null )
                contratacao.setDataAniversario(servicoDTO.getDataContratacao());
            else
                contratacao.setDataAniversario(LocalDate.now());

            return contratacao;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ApiRequestException();
        }
    }

    private String buscarSigla(long id) {
        Simbolo simbolo = simboloRepository.findById(id).get();
        return simbolo.getSigla();
    }

    public Contratacao gerarValoresContratacao(Double valorBase, Simbolo simbolo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Contratacao contratacao = new Contratacao();

        String sigla = buscarSigla(simbolo.getId());
        MoedaDTO moedas = buscarCotacaoMoedas( "BRL,USD,".concat(sigla));

        Double cotacaoReal = (Double) PropertyUtils.getProperty( moedas.getRates(), "BRL" );
        Double cotacaoDolar = (Double) PropertyUtils.getProperty( moedas.getRates(), "USD" );

        switch (sigla) {
            case "BRL":
                contratacao.setValorBRL(valorBase);
                contratacao.setValorUSD(valorBase * cotacaoDolar / cotacaoReal);
                break;
            case "USD":
                contratacao.setValorUSD(valorBase);
                contratacao.setValorBRL(valorBase * cotacaoReal / cotacaoDolar);
                break;
            case "EUR":
                contratacao.setValorBRL(valorBase * cotacaoReal);
                contratacao.setValorUSD(valorBase * cotacaoDolar);
                break;
            default:
                Double cotacaoBase = (Double) PropertyUtils.getProperty( moedas.getRates(), sigla);
                contratacao.setValorBRL(valorBase * cotacaoReal / cotacaoBase);
                contratacao.setValorUSD(valorBase * cotacaoDolar / cotacaoBase);
                break;
        }

        contratacao.setValorUSD( arredondarComDuasCasas(contratacao.getValorUSD()) );
        contratacao.setValorBRL( arredondarComDuasCasas(contratacao.getValorBRL()) );

        return contratacao;
    }

    public Double calcularCustoMensal(Double custoTotal, Periodicidade periodicidade) {
        switch (periodicidade) {
            case MENSAL: return custoTotal;
            case TRIMESTRAL: return custoTotal / 3;
            case SEMESTRAL: return custoTotal / 6;
            case ANUAL: return custoTotal / 12;
            default: return custoTotal;
        }
    }

    public Double arredondarComDuasCasas(Double valor){
        return Math.round(valor * 100.0) / 100.0;
    }

    public Contratacao atualizarContratacao(Contratacao contratacao, ServicoDTO dto) throws ApiRequestException {
        if( dto.getValor() == null )
            dto.setValor(contratacao.getValorBase());
        if( dto.getPeriodicidade() == null )
            dto.setPeriodicidade(contratacao.getPeriodicidade());
        if( dto.getSimbolo() == null )
            dto.setSimbolo(contratacao.getSimbolo());
        if( dto.getDataContratacao() == null )
            dto.setDataContratacao(contratacao.getDataAniversario());

        return gerarContratacao(dto);
    }
}
