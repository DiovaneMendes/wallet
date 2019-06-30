package br.com.dbc.wallet.Service;

import br.com.dbc.wallet.Component.RelatorioComponent;
import br.com.dbc.wallet.Component.ServicoComponent;
import br.com.dbc.wallet.DTO.GastoTotalMesDTO;
import br.com.dbc.wallet.DTO.RelatorioDTO;
import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Exception.ApiRequestException;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Repository.ServicoRepository;
import br.com.dbc.wallet.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ServicoComponent servicoComponent;

    public RelatorioComponent relatorioComponent;

    public ServicoService(ServicoRepository servicoRepository,
                          ServicoComponent servicoComponent,
                          RelatorioComponent relatorioComponent) {
        this.servicoRepository = servicoRepository;
        this.servicoComponent = servicoComponent;
        this.relatorioComponent = relatorioComponent;
    }

    //Salva
    @Transactional(rollbackFor = Exception.class)
    public Servico salvar(ServicoDTO servicoDTO) throws ApiRequestException {
        Servico servico = servicoComponent.montarServico(servicoDTO);
        return servicoRepository.save(servico);
    }

    //Busca
    public Servico buscarPorID(long id) throws BuscaNullException {
        if (servicoRepository.findById(id).isPresent())
            return servicoRepository.findById(id).get();
        throw new BuscaNullException();
    }

    //Edita
    @Transactional(rollbackFor = Exception.class)
    public Servico editar(long id, ServicoDTO servicoDTO) throws BuscaNullException, ApiRequestException {
        if ( !servicoRepository.findById(id).isPresent() ) throw new BuscaNullException();

        Servico servico = servicoRepository.findById(id).get();
        servico = servicoComponent.editarServico(servicoDTO, servico);
        return servicoRepository.save(servico);
    }

    //Remove
    @Transactional(rollbackFor = Exception.class)
    public void remover( Servico servico)  {
        servicoRepository.delete(servico);
    }

    //BuscaTodos
    public List<Servico> buscarTodos() throws BuscaNullException{
        List<Servico> resultadoBusca = (List<Servico>) servicoRepository.findAll();
        if (resultadoBusca.isEmpty()) throw new BuscaNullException();
        return resultadoBusca;
    }

    //Busca todos ativos
    public List<Servico> buscarTodosAtivos(long id){
        return servicoRepository.findAllByUsuario_IdAndEstaAtivoTrue(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public RelatorioDTO gerarRelatorio(long idUsuario, LocalDate data) throws ApiRequestException{
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        RelatorioDTO relatorioDTO = new RelatorioDTO();

        List<Servico> servicosAtivos = servicoRepository.findAllByUsuario_IdAndEstaAtivoTrue(idUsuario);

        //todo atualizar pagamentos

        //garante apenas servicos ativos ou cancelados antes do vencimento
        List<Servico> servicosValidos = servicoComponent.selecionarServicos(usuario);

        //ordena os servicos levando em conta o custo mensal com base no ultimo valor pago
        List<Servico> servicosOrdenados = servicoComponent.ordenarServicos(servicosValidos);

        double gastoTotal = relatorioComponent.gastoTotalMensal(servicosAtivos);

        double estimativa = relatorioComponent.calcularEstimativaProximoMes(servicosOrdenados, data);

	Servico servicoMaisCaro = relatorioComponent.servicoMaisCaro(servicosAtivos);

        List<GastoTotalMesDTO> listaTotalCadaMes = relatorioComponent.ultimosDozeGastosPorMes(servicosOrdenados);

        relatorioDTO.setNomeUsuario(usuario.getNome());
        relatorioDTO.setGastoMensalAtual( gastoTotal );
        relatorioDTO.setEstimativaProximoMes( estimativa );
        relatorioDTO.setServicoMaisCaro( servicoMaisCaro );
        relatorioDTO.setServicosContratados( servicosOrdenados );
        relatorioDTO.setUltimosGastos( listaTotalCadaMes );

        return relatorioDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    public void cancelar(long id) throws BuscaNullException {
        if ( !servicoRepository.findById(id).isPresent() ) throw new BuscaNullException();

        Servico servico = servicoRepository.findById(id).get();
        servico.setEstaAtivo(false);

        servicoRepository.save(servico);
    }
}
