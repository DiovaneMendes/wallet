package br.com.dbc.wallet.Repository;

import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServicoRepository extends CrudRepository<Servico, Long> {
    List<Servico> findAllByNome(String nome);
    Servico findByContratacao(Contratacao contratacao);
    List<Servico> findAllByUsuario(Usuario usuario);
    List<Servico> findAllByEstaAtivoTrue();
    List<Servico> findAllByEstaAtivoFalse();
    List<Servico> findAllByUsuario_IdAndEstaAtivoTrue(long id);
}
