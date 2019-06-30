package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Situacao;
import br.com.dbc.wallet.Exception.AtributoObrigatorioException;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Exception.UsuarioInvalidoException;
import br.com.dbc.wallet.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;

    @Autowired
    public UtilService utilService;

    //Salva
    @Transactional(rollbackFor = Exception.class)
    public Usuario salvar(Usuario usuario) throws AtributoObrigatorioException {
        utilService.validarUsuarioNovo(usuario);
        usuario.setSenha( new BCryptPasswordEncoder().encode( usuario.getSenha() ) );
        return usuarioRepository.save(usuario);
    }

    //Busca
    public Usuario buscarPorID(long id) throws BuscaNullException {
        if (usuarioRepository.findById(id).isPresent())
            return usuarioRepository.findById(id).get();
        throw new BuscaNullException();
    }

    //Edita
    @Transactional(rollbackFor = Exception.class)
    public Usuario editar(long id, Usuario usuario) throws UsuarioInvalidoException {
        if( !usuarioRepository.findById(id).isPresent() ) throw new UsuarioInvalidoException();

        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    //Remove
    @Transactional(rollbackFor = Exception.class)
    public void remover( Usuario usuario) throws UsuarioInvalidoException {
        if( !usuarioRepository.findById(usuario.getId()).isPresent() ) throw new UsuarioInvalidoException();
        usuarioRepository.delete(usuario);
    }

    //BuscaTodos
    public List<Usuario> buscarTodos() throws BuscaNullException{
        List<Usuario> resultadoBusca = (List<Usuario>) usuarioRepository.findAll();
        if (resultadoBusca.isEmpty()) throw new BuscaNullException();
        return resultadoBusca;
    }

    public Usuario findByLogin(String login) throws UsuarioInvalidoException {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if ( usuario == null || usuario.getSituacao() == Situacao.INATIVO ) throw new UsuarioInvalidoException();
        return usuario;
    }

}

