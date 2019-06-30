package br.com.dbc.wallet.Component;

import br.com.dbc.wallet.DTO.CotacaoDTO;
import br.com.dbc.wallet.DTO.GastoTotalMesDTO;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Enuns.Mes;
import br.com.dbc.wallet.Exception.ApiRequestException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RelatorioComponent {

    @Autowired
    private PagamentoComponent pagamentoComponent;

    @Autowired
    private ContratacaoComponent contratacaoComponent;

    public RelatorioComponent(ContratacaoComponent contratacaoComponent) {
        this.contratacaoComponent = contratacaoComponent;
    }

    public CotacaoDTO buscarCotacaoBRLComBaseUSD(String data){
        String base = "?base=USD&symbols=BRL";
        RestTemplate buscador = new RestTemplate();
        return buscador.getForObject("https://api.exchangeratesapi.io/".concat(data).concat(base), CotacaoDTO.class);
    }

    public Double calcularMediaDolar(LocalDate data) throws ApiRequestException {
        try {
            List<CotacaoDTO> ultimoMes = new ArrayList<>();
            Double somaDasMedias = 0.0;
            Double media = 0.0;

            for( int i = 1; i <= 30; i++) {
                ultimoMes.add(buscarCotacaoBRLComBaseUSD(data.toString()));
                data = data.minusDays(1);
            }

            for(CotacaoDTO cotacao : ultimoMes)
                somaDasMedias += (Double) PropertyUtils.getProperty(cotacao.getRates(), "BRL");

            media = somaDasMedias / 30;

            return Math.round(media * 100.0) / 100.0;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ApiRequestException();
        }
    }

    public double gastoTotalMensal(List<Servico> servicos){
        LocalDate dataAtual = LocalDate.now();
        Double gastoMensal = servicos.stream()
                .filter(servico -> servico.getContratacao().getDataAniversario().getMonth() == dataAtual.getMonth() &&
                        servico.getContratacao().getDataAniversario().getDayOfMonth() <= dataAtual.getDayOfMonth() )
                .mapToDouble(servico -> contratacaoComponent.calcularCustoMensal(servico.getPagamentos()
                        .get(servico.getPagamentos().size() - 1).getValorPago(), servico.getContratacao().getPeriodicidade()))
                .sum();

        return contratacaoComponent.arredondarComDuasCasas(gastoMensal);
    }

    public Servico servicoMaisCaro(List<Servico> servicos){
        double maiorValor = servicos.stream()
                .mapToDouble(servico -> servico.getContratacao().getValorBRL())
                .max()
                .orElse(0);

        return servicos.stream()
                .filter(servico -> servico.getContratacao().getValorBRL() == maiorValor)
                .findFirst()
                .orElse(null);
    }

    public List<GastoTotalMesDTO> ultimosDozeGastosPorMes(List<Servico> servicos){
        List<GastoTotalMesDTO> gastoTotalMesDTOList = new ArrayList<>();

        LocalDate mesCalculado = LocalDate.now();

        for (int i=1; i<13; i++) {
            List<Servico> listaApoio = filtraPorMes(servicos, mesCalculado);

            double gastoDoMes = listaApoio.stream()
                    .mapToDouble(servico -> servico.getContratacao().getValorBRL())
                    .sum();

            gastoDoMes = contratacaoComponent.arredondarComDuasCasas(gastoDoMes);
            Mes mes = escolhaDeMes( mesCalculado );

            gastoTotalMesDTOList.add( new GastoTotalMesDTO(mes, gastoDoMes) );

            mesCalculado = mesCalculado.minusMonths(1);
        }

        return gastoTotalMesDTOList;
    }

    public Mes escolhaDeMes(LocalDate data){
        switch (data.getMonth()){
            case JANUARY: return Mes.JANEIRO;
            case FEBRUARY: return Mes.FEVEREIRO;
            case MARCH: return Mes.MARCO;
            case APRIL: return Mes.ABRIL;
            case MAY: return Mes.MAIO;
            case JUNE: return Mes.JUNHO;
            case JULY: return Mes.JULHO;
            case AUGUST: return Mes.AGOSTO;
            case SEPTEMBER: return Mes.SETEMBRO;
            case OCTOBER: return Mes.OUTUBRO;
            case NOVEMBER: return Mes.NOVEMBRO;
            case DECEMBER: return Mes.DEZEMBRO;
        }
        return null;
    }

    public List<Servico> filtraPorMes(List<Servico> servicos, LocalDate data){
        return servicos.stream()
                .filter(servico -> servico.getContratacao().getDataAniversario().getMonth() == data.getMonth())
                .collect( toList() );
    }

    public Double calcularEstimativaProximoMes(List<Servico> servicos, LocalDate data) throws ApiRequestException {
        Double totalContratado = 0.0;

        for(Servico servico : servicos)
            totalContratado += contratacaoComponent.calcularCustoMensal(
                    servico.getContratacao().getValorUSD(), servico.getContratacao().getPeriodicidade());

        Double mediaCotacaoDolar = calcularMediaDolar(data);

        Double estimativa = totalContratado * mediaCotacaoDolar;
        return Math.round(estimativa * 100.0) / 100.0;
    }

}
