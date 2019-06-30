package br.com.dbc.wallet.Component;

import br.com.dbc.wallet.DTO.CotacaoDTO;
import br.com.dbc.wallet.DTO.GastoTotalMesDTO;
import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Entity.Pagamento;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Enuns.Mes;
import br.com.dbc.wallet.Enuns.Periodicidade;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class RelatorioComponentTest {

    private RelatorioComponent relatorioComponent;

    private List<Servico> servicos;

    private Servico servico;

    private List<GastoTotalMesDTO> listaGastos;

    private List<Servico> listaMesAtual;

    @Before
    public void setUp(){
        relatorioComponent = new RelatorioComponent( new ContratacaoComponent() );
        servicos = new ArrayList<>();
        servico = new Servico();
        listaGastos = new ArrayList<>();
        listaMesAtual = new ArrayList<>();

        Servico servicoUm = new Servico();
        Servico servicoDois = new Servico();
        Servico servicoTres = new Servico();
        Servico servicoQuatro = new Servico();
        Servico servicoCinco = new Servico();

        Contratacao contratacaoUm = new Contratacao();
        Contratacao contratacaoDois = new Contratacao();
        Contratacao contratacaoTres = new Contratacao();
        Contratacao contratacaoQuatro = new Contratacao();
        Contratacao contratacaoCinco = new Contratacao();

        Pagamento pagamentoUm = new Pagamento();
        Pagamento pagamentoDois = new Pagamento();
        Pagamento pagamentoTres = new Pagamento();
        Pagamento pagamentoQuatro = new Pagamento();
        Pagamento pagamentoCinco = new Pagamento();

        contratacaoUm.setValorBRL(250.50);
        contratacaoDois.setValorBRL(180.79);
        contratacaoTres.setValorBRL(197.05);
        contratacaoQuatro.setValorBRL(323.60);
        contratacaoCinco.setValorBRL(59.0);

        contratacaoUm.setValorUSD(65.06);
        contratacaoDois.setValorUSD(46.96);
        contratacaoTres.setValorUSD(51.18);
        contratacaoQuatro.setValorUSD(84.05);
        contratacaoCinco.setValorUSD(15.32);

        contratacaoUm.setPeriodicidade(Periodicidade.MENSAL);
        contratacaoDois.setPeriodicidade(Periodicidade.MENSAL);
        contratacaoTres.setPeriodicidade(Periodicidade.MENSAL);
        contratacaoQuatro.setPeriodicidade(Periodicidade.MENSAL);
        contratacaoCinco.setPeriodicidade(Periodicidade.MENSAL);

        LocalDate dataAtual = LocalDate.now();

        LocalDate dataUm = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), dataAtual.getDayOfMonth());
        LocalDate dataDois = LocalDate.of(dataAtual.getYear(), dataAtual.minusMonths(5).getMonth(), 2);
        LocalDate dataTres = LocalDate.of(dataAtual.getYear(), dataAtual.minusMonths(3).getMonth(), 2);
        LocalDate dataQuatro = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), dataAtual.getDayOfMonth());
        LocalDate dataCinco = LocalDate.of(dataAtual.getYear(), dataAtual.getMonth(), dataAtual.getDayOfMonth());

        servicoUm.setContratacao(contratacaoUm);
        servicoDois.setContratacao(contratacaoDois);
        servicoTres.setContratacao(contratacaoTres);
        servicoQuatro.setContratacao(contratacaoQuatro);
        servicoCinco.setContratacao(contratacaoCinco);

        servicoUm.getContratacao().setDataAniversario( dataUm );
        servicoDois.getContratacao().setDataAniversario( dataDois );
        servicoTres.getContratacao().setDataAniversario( dataTres );
        servicoQuatro.getContratacao().setDataAniversario( dataQuatro );
        servicoCinco.getContratacao().setDataAniversario( dataCinco );

        pagamentoUm.setDataPagamento(dataUm);
        pagamentoDois.setDataPagamento(dataDois);
        pagamentoTres.setDataPagamento(dataTres);
        pagamentoQuatro.setDataPagamento(dataQuatro);
        pagamentoCinco.setDataPagamento(dataCinco);

        pagamentoUm.setValorPago(250.50);
        pagamentoDois.setValorPago(180.79);
        pagamentoTres.setValorPago(197.05);
        pagamentoQuatro.setValorPago(323.60);
        pagamentoCinco.setValorPago(59.0);

        servicos.addAll( Arrays.asList( servicoUm, servicoDois, servicoTres, servicoQuatro, servicoCinco) );

        servico = servicoQuatro;

        listaMesAtual.addAll( Arrays.asList(servicoUm, servicoQuatro, servicoCinco) );

        listaGastos.addAll( Arrays.asList(
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual), 633.1),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(1)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(2)), 0),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(3)), 197.05 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(4)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(5)), 180.79 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(6)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(7)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(8)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(9)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(10)), 0 ),
                new GastoTotalMesDTO( relatorioComponent.escolhaDeMes(dataAtual.minusMonths(11)), 0 )
        ) );
    }

    @Test
    public void buscarCotacaoDoRealComBaseUSD(){
        Double valor = 0.0;
        try {
            CotacaoDTO cotacao = relatorioComponent.buscarCotacaoBRLComBaseUSD("2019-04-10");
            valor = (Double) PropertyUtils.getProperty(cotacao.getRates(), "BRL");
        } catch (Exception e) {
            System.out.println("Falha ao buscar propriedade rates no teste");
        }

        assertEquals(3.839, valor, 0.0001);
    }

    @Test
    public void calcularMediaDolar() {
        Double media = 0.0;
        LocalDate data = LocalDate.of(2019, 04, 12);
        try {
            media = relatorioComponent.calcularMediaDolar(data);
        } catch (Exception e) {
            System.out.println("Falha ao buscar propriedade rates no teste");
        }

        assertEquals(3.86, media, 0.01);
    }

    @Test
    public void deveRetornarValorTotalDoMes633ponto1(){
        double valorTotal = relatorioComponent.gastoTotalMensal(servicos);

        assertEquals(valorTotal , 633.1, 1 );
    }

    @Test
    public void deveRetornarServicoComMaiorValor(){
        Servico servicoTeste = relatorioComponent.servicoMaisCaro(servicos);

        assertEquals(servico, servicoTeste);
    }

    @Test
    public void deveRetornarMesNovembro(){
        LocalDate data = LocalDate.of(2019, 11,1);

        Mes mes = relatorioComponent.escolhaDeMes(data);

        assertEquals(Mes.NOVEMBRO, mes);
    }

    @Test
    public void deveRetornarServicosDoMesAtual(){
        LocalDate data = LocalDate.now();

        List<Servico> listaTeste = relatorioComponent.filtraPorMes(servicos, data);

        assertEquals(listaMesAtual, listaTeste);
    }

    @Test
    public void deveGerarListaComGastosDosUltimos12Meses(){
        List<GastoTotalMesDTO> listaTest = relatorioComponent.ultimosDozeGastosPorMes(servicos);

        assertEquals(listaGastos, listaTest);
    }

    @Test
    public void deveGerarEstimativa(){
        Double estimativa = 0.0;
        try {
            LocalDate data = LocalDate.of(2019, 4, 12);
            estimativa = relatorioComponent.calcularEstimativaProximoMes(servicos, data);
        } catch (Exception e) {
            System.out.println("Falha ao calcular a estimativa");
        }

        assertEquals(1013.52, estimativa, 0.01);
    }
}