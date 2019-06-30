package br.com.dbc.wallet.Component;

import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Simbolo;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Periodicidade;
import br.com.dbc.wallet.Exception.ApiRequestException;
import br.com.dbc.wallet.Repository.SimboloRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServicoComponentTest {

    @Mock
    private SimboloRepository simboloRepository;

    @Mock
    private ContratacaoComponent contratacaoComponent;

    @Mock
    private ServicoComponent servicoComponent;

    private ServicoDTO servicoDTO;

    private LocalDate dataContratacao;

    @Before
    public void setUp(){
        dataContratacao = LocalDate.of(2019,4, 15);
        Simbolo simbolo = new Simbolo();
        simbolo.setSigla("USD");
        simbolo.setNome("United States Dollar");

        servicoDTO = new ServicoDTO();

        servicoDTO.setUsuario( new Usuario() );
        servicoDTO.setNome("Slack");
        servicoDTO.setDescricao("Envio mensagem");
        servicoDTO.setWebsite("slack.com");
        servicoDTO.setValor( 159.90 );
        servicoDTO.setPeriodicidade( Periodicidade.MENSAL );
        servicoDTO.setSimbolo( simbolo );
        servicoDTO.setDataContratacao( LocalDate.now() );

        when(simboloRepository.save(simbolo)).thenReturn(simbolo);
    }

    @Test
    public void deveGerarDataVencimento2019_5_15APartirPeriodicidadeMensal(){
        LocalDate dataVencimento = servicoComponent.geraDataVencimento(dataContratacao, Periodicidade.MENSAL);

        LocalDate dataTeste = dataContratacao.plusMonths(1);

        when( servicoComponent.geraDataVencimento(dataContratacao, Periodicidade.MENSAL) ).thenReturn(dataTeste);

        assertThat(dataVencimento).isEqualTo( dataTeste );
    }

    @Test
    public void deveGerarServicoComCustoAtravesServicoDTO(){
        Servico servico = new Servico();
        try {
            servico = servicoComponent.montarServico( servicoDTO );
        } catch (ApiRequestException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull( servico.getContratacao() );
    }
}