package br.com.dbc.wallet.Component;

import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Periodicidade;
import br.com.dbc.wallet.Exception.ApiRequestException;
import br.com.dbc.wallet.Repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ServicoComponent {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    public ContratacaoComponent contratacaoComponent;

    @Autowired
    public PagamentoComponent pagamentoComponent;

    public ServicoComponent(ContratacaoComponent contratacaoComponent) {
        this.contratacaoComponent = contratacaoComponent;
    }

    public Servico montarServico(ServicoDTO servicoDTO) throws ApiRequestException {
        Servico servico = new Servico();

        servico.setUsuario(servicoDTO.getUsuario());
        servico.setNome(servicoDTO.getNome());
        servico.setEstaAtivo(true);
        servico.setContratacao(contratacaoComponent.gerarContratacao(servicoDTO));
        servico = validarCamposOpcionais(servicoDTO, servico);

        return servico;
    }

    private Servico validarCamposOpcionais(ServicoDTO dto, Servico servico) {
        if( dto.getDescricao() != null )
            servico.setDescricao(dto.getDescricao());

        if( dto.getWebsite() != null )
            servico.setWebsite(dto.getWebsite());

        return servico;
    }

    public LocalDate geraDataVencimento(LocalDate dataInicio, Periodicidade periodicidade) {
        switch (periodicidade) {
            case TRIMESTRAL: return dataInicio.plusMonths(3);
            case SEMESTRAL: return dataInicio.plusMonths(6);
            case ANUAL: return dataInicio.plusYears(1);
            default: return dataInicio.plusMonths(1);
        }
    }

    public Servico editarServico(ServicoDTO dto, Servico servico) throws ApiRequestException {
        if( dto.getNome() != null )
            servico.setNome(dto.getNome());
        if( dto.getUsuario() != null )
            servico.getUsuario().setId(dto.getUsuario().getId());
        if( dto.getDescricao() != null )
            servico.setDescricao(dto.getDescricao());
        if( dto.getWebsite() != null )
            servico.setWebsite(dto.getWebsite());

        Contratacao contratacao = contratacaoComponent.atualizarContratacao(servico.getContratacao(), dto);
        contratacao.setId(servico.getContratacao().getId());
        servico.setContratacao(contratacao);

        return servico;
    }

    public List<Servico> selecionarServicos(Usuario usuario) {
        List<Servico> servicosPorUsuario = servicoRepository.findAllByUsuario(usuario);
        List<Servico> servicosValidos = new ArrayList<>();
        for( Servico servico : servicosPorUsuario ) {
            if( servico.getEstaAtivo() ) {
                servicosValidos.add(servico);
            } else {
                LocalDate ultimoPagamento = pagamentoComponent.selecionarUltimoPagamento(servico.getPagamentos());
                boolean servicoValido = validarVencimentoServico(servico, ultimoPagamento);
                if( servicoValido )
                    servicosValidos.add(servico);
            }
        }
        return servicosValidos;
    }

    public boolean validarVencimentoServico(Servico servico, LocalDate ultimoPagamento) {
        LocalDate dataVencimento = ultimoPagamento;
        LocalDate dataAtual = LocalDate.now();
        Periodicidade periodicidade = servico.getContratacao().getPeriodicidade();

        switch (periodicidade) {
            case MENSAL:
                dataVencimento.plusMonths(1);
                break;
            case TRIMESTRAL:
                dataVencimento.plusMonths(3);
                break;
            case SEMESTRAL:
                dataVencimento.plusMonths(6);
                break;
            case ANUAL:
                dataVencimento.plusMonths(12);
        }
        return dataVencimento.isAfter(dataAtual);
    }

    public List<Servico> ordenarServicos(List<Servico> servicos) {
        Double custoMensal;
        int index;

        for( Servico servico : servicos ) {
            index = servico.getPagamentos().size() - 1;
            custoMensal = contratacaoComponent.calcularCustoMensal(servico.getPagamentos().get(index).getValorPago(), servico.getContratacao().getPeriodicidade());
            servico.getContratacao().setValorBRL(custoMensal);
        }

        return servicos.stream().sorted(Comparator.comparingDouble(o -> o.getContratacao().getValorBRL())).collect(toList());
    }

}
