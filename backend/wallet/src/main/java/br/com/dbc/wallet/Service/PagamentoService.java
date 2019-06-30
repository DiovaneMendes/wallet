package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Component.PagamentoComponent;
import br.com.dbc.wallet.Entity.Pagamento;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Exception.GerarPagamentoException;
import br.com.dbc.wallet.Repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoComponent pagamentoComponent;

    //Salva
    @Transactional(rollbackFor = Exception.class)
    public Pagamento salvar(Servico servico) throws GerarPagamentoException {
        Pagamento pagamento = pagamentoComponent.gerarPagamento(servico);

        boolean pagamentoValido = pagamentoComponent.validarPagamento(pagamento);
        if( !pagamentoValido ) throw new GerarPagamentoException();

        return pagamentoRepository.save(pagamento);
    }

    //Busca
    public Pagamento buscarPorID(long id) throws BuscaNullException {
        if( pagamentoRepository.findById(id).isPresent() )
            return pagamentoRepository.findById(id).get();
        throw new BuscaNullException();
    }

    //BuscaTodos
    public List<Pagamento> buscarTodos() throws BuscaNullException{
        List<Pagamento> resultadoBusca = (List<Pagamento>) pagamentoRepository.findAll();
        if( resultadoBusca.isEmpty() ) throw new BuscaNullException();
        return resultadoBusca;
    }
}
