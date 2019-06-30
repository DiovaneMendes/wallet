package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Entity.Simbolo;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Repository.SimboloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimboloService {

    @Autowired
    public SimboloRepository simboloRepository;

    //Salva
    @Transactional(rollbackFor = Exception.class)
    public Simbolo salvar(Simbolo simbolo){
        return simboloRepository.save(simbolo);
    }

    //Busca
    public Simbolo buscarPorID(long id) throws BuscaNullException{
        if( simboloRepository.findById(id).isPresent() )
            return simboloRepository.findById(id).get();
        throw new BuscaNullException();
    }

    //Edita
    @Transactional(rollbackFor = Exception.class)
    public Simbolo editar(long id, Simbolo simbolo) {
        simbolo.setId(id);
        return simboloRepository.save(simbolo);
    }

    //Remove
    @Transactional(rollbackFor = Exception.class)
    public void remover(Simbolo simbolo)  {
        simboloRepository.delete(simbolo);
    }

    //BuscaTodos
    public List<Simbolo> buscarTodos() throws BuscaNullException{
        List<Simbolo> resultadoBusca = (List<Simbolo>) simboloRepository.findAll();
        if( resultadoBusca.isEmpty() ) throw new BuscaNullException();
        return resultadoBusca;
    }
}
