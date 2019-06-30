package br.com.dbc.wallet.Repository;

import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Perfil;
import br.com.dbc.wallet.Enuns.Situacao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByNome(String nome);
    List<Usuario> findAllByPerfil(Perfil perfil);
    Usuario findByLogin(String login);
    Usuario findByEmail(String email);
    List<Usuario> findAllBySituacao(Situacao situacao);
}
