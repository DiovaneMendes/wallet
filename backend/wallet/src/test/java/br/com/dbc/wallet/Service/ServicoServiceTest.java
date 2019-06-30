package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Component.RelatorioComponent;
import br.com.dbc.wallet.Component.ServicoComponent;
import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Simbolo;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Perfil;
import br.com.dbc.wallet.Enuns.Periodicidade;
import br.com.dbc.wallet.Enuns.Situacao;
import br.com.dbc.wallet.Exception.ApiRequestException;
import br.com.dbc.wallet.Repository.ServicoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private ServicoComponent servicoComponent;

    @Mock
    private RelatorioComponent relatorioComponent;

    @Mock
    private ServicoService servicoService;

    private ServicoDTO servicoDTO = new ServicoDTO();

    private Usuario usuario = new Usuario();

    private Simbolo simbolo = new Simbolo();

    @Before
    public void setUp(){
//        servicoService = new ServicoService();

        usuario.setNome("Everton Sousa");
        usuario.setLogin("peleverton");
        usuario.setEmail("peleverton@gmail.com");
        usuario.setSenha("tricolor");
        usuario.setPerfil(Perfil.GERENTE);
        usuario.setSituacao(Situacao.ATIVO);

        simbolo.setId(150);

        servicoDTO.setUsuario(usuario);
        servicoDTO.setNome("treinamento");
        servicoDTO.setPeriodicidade(Periodicidade.MENSAL);
        servicoDTO.setValor(10.0);
        servicoDTO.setSimbolo(simbolo);
        servicoDTO.setDataContratacao(LocalDate.now());
    }

    @Test
    public void deveSalvarServicoRecebendoDTO() {
//        when(servicoService.salvar(servicoDTO)).thenReturn();
        Servico servicoSalvo = new Servico();
        try {
            servicoSalvo = servicoService.salvar(servicoDTO);
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
        }

        assertEquals("treinamento", servicoSalvo.getNome());
    }
}