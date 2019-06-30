package br.com.dbc.wallet.Component;

import br.com.dbc.wallet.Entity.Pagamento;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PagamentoComponent {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PagamentoComponent(){}

    public Pagamento gerarPagamento(Servico servico) {
        Pagamento pagamento = new Pagamento();
        Servico servicoRelacional = new Servico();
        servicoRelacional.setId(servico.getId());

        pagamento.setValorPago(servico.getContratacao().getValorBRL());
        pagamento.setDataPagamento(servico.getContratacao().getDataAniversario());
        pagamento.setServico(servicoRelacional);

        return pagamento;
    }

    public boolean validarPagamento(Pagamento pagamento) {
        if( pagamento.getValorPago() == null ) return false;
        if( pagamento.getDataPagamento() == null ) return false;
        if( pagamento.getServico() == null ) return false;

        return true;
    }


    public LocalDate selecionarUltimoPagamento(List<Pagamento> pagamentos) {
        int ultimoPagamento = pagamentos.size() - 1;
        return pagamentos.get(ultimoPagamento).getDataPagamento();
    }
}
