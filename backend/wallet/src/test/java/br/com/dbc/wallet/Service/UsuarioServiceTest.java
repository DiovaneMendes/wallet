package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Perfil;
import br.com.dbc.wallet.Enuns.Situacao;
import br.com.dbc.wallet.Exception.AtributoObrigatorioException;
import br.com.dbc.wallet.Repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UtilService utilService;

    private Usuario usuario = new Usuario();

    @Before
    public void setUp(){

        usuario.setPerfil(Perfil.ADMINISTRADOR);
        usuario.setNome("Maria");
        usuario.setLogin("Maria123");
        usuario.setEmail("maria@gmail.com");
        usuario.setSenha("59.com");
        usuario.setSituacao(Situacao.ATIVO);
    }

    @Test
    public void deveSalvarSenhaCriptografada(){
        try {
            usuarioService.salvar(usuario);
        } catch (AtributoObrigatorioException e) {
            System.out.println(e.getMessage());
        }


        assertTrue( new BCryptPasswordEncoder().matches( "59.com", usuario.getSenha() ));

        verify(usuarioRepository).save(usuario);
    }
}
