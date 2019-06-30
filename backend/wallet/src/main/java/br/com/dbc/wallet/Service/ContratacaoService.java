package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContratacaoService {

    @Autowired
    public ContratacaoRepository contratacaoRepository;

    //Salva
    @Transactional(rollbackFor = Exception.class)
    public Contratacao salvar(Contratacao contratacao){
        return contratacaoRepository.save(contratacao);
    }

    //Busca
    public Contratacao buscarPorID(long id) throws BuscaNullException{
        if( contratacaoRepository.findById(id).isPresent() )
            return contratacaoRepository.findById(id).get();
        throw new BuscaNullException();
    }

    //Edita
    @Transactional(rollbackFor = Exception.class)
    public Contratacao editar(long id, Contratacao contratacao) {
        contratacao.setId(id);
        return contratacaoRepository.save(contratacao);
    }

    //Remove
    @Transactional(rollbackFor = Exception.class)
    public void remover( Contratacao contratacao)  {
        contratacaoRepository.delete(contratacao);
    }

    //BuscaTodos
    public List<Contratacao> buscarTodos() throws BuscaNullException{
        List<Contratacao> resultadoBusca = (List<Contratacao>) contratacaoRepository.findAll();
        if (resultadoBusca.isEmpty()) throw new BuscaNullException();
        return resultadoBusca;
    }
}
