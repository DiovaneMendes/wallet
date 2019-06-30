package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Situacao;
import br.com.dbc.wallet.Exception.AtributoObrigatorioException;
import br.com.dbc.wallet.Exception.ServicoInvalidoException;
import br.com.dbc.wallet.Exception.UsuarioInvalidoException;
import br.com.dbc.wallet.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

    @Autowired
    public UsuarioRepository usuarioRepository;

    public boolean validarUsuarioNovo(Usuario usuario) throws AtributoObrigatorioException {
        boolean nomeValido = usuario.getNome() != null;
        boolean loginValido = usuario.getLogin() != null;
        boolean emailValido = usuario.getEmail() != null;
        boolean perfilValido = usuario.getPerfil() != null;

        if( !(nomeValido && loginValido && emailValido && perfilValido) ) throw new AtributoObrigatorioException();

        return true;
    }

    public Usuario validarUsuario(long idUsuario) throws UsuarioInvalidoException {
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        if( usuario == null || usuario.getSituacao() == Situacao.INATIVO ) throw new UsuarioInvalidoException();

        return usuario;
    }

    public boolean validarServico(ServicoDTO servicoDTO) throws ServicoInvalidoException {
        try {
            validarUsuario(servicoDTO.getUsuario().getId());

            boolean nomeValido = servicoDTO.getNome() != null;
            boolean valorValido = servicoDTO.getValor() != null;
            boolean periodoValido = servicoDTO.getPeriodicidade() != null;
            if( !(nomeValido && valorValido && periodoValido) ) throw new AtributoObrigatorioException();

            return true;
        } catch (UsuarioInvalidoException | AtributoObrigatorioException e) {
            throw new ServicoInvalidoException(e.getMessage());
        }
    }


}
