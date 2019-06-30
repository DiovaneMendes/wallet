package br.com.dbc.wallet.Controller;

import br.com.dbc.wallet.DTO.RelatorioDTO;
import br.com.dbc.wallet.DTO.ServicoDTO;
import br.com.dbc.wallet.Entity.Servico;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Exception.*;
import br.com.dbc.wallet.Service.PagamentoService;
import br.com.dbc.wallet.Service.ServicoService;
import br.com.dbc.wallet.Service.UsuarioService;
import br.com.dbc.wallet.Service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/servico")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private UtilService utilService;

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String salvar(@RequestBody ServicoDTO servicoDTO) {
        try {
            utilService.validarServico(servicoDTO);
            Servico servico = servicoService.salvar(servicoDTO);
            pagamentoService.salvar(servico);
            return "Serviço registrado com sucesso";

        } catch (ServicoInvalidoException | ApiRequestException | GerarPagamentoException e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/atualizar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String atualizar(@PathVariable long id, @RequestBody ServicoDTO servicoDTO) {
        try {
            servicoService.editar(id, servicoDTO);
            return "Serviço atualizado com sucesso.";
        } catch (BuscaNullException | ApiRequestException e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<Servico> buscarTodos() {
        try {
            return servicoService.buscarTodos();
        } catch (BuscaNullException e) {
            System.out.println( e.getMessage() );
            return null;
        }
    }

    @GetMapping(value = "/ativos")
    @ResponseBody
    public List<Servico> buscaTodosAtivos(HttpServletRequest request) {
        try {
            String login = request.getUserPrincipal().getName();
            Usuario usuario = usuarioService.findByLogin(login);
            return servicoService.buscarTodosAtivos(usuario.getId());
        } catch (UsuarioInvalidoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Servico buscarPorID(@PathVariable long id) {
        try {
            return servicoService.buscarPorID(id);
        }catch (BuscaNullException e) {
            System.out.println( e.getMessage() );
            return null;
        }
    }

    @DeleteMapping(value = "/deletar")
    public void deletar(@RequestBody Servico servico) {
        servicoService.remover(servico);
    }

    @GetMapping(value = "/relatorio/")
    @ResponseBody
    public RelatorioDTO relatorio(HttpServletRequest request){
        String login = request.getUserPrincipal().getName();
        LocalDate data = LocalDate.now();

        try {
            Usuario usuario = usuarioService.findByLogin(login);
            return servicoService.gerarRelatorio(usuario.getId(), data);
        } catch (UsuarioInvalidoException | ApiRequestException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/relatorio/{id}")
    @ResponseBody
    public RelatorioDTO relatorio(@PathVariable long id){
        LocalDate data = LocalDate.now();
        try {
            utilService.validarUsuario(id);
            return servicoService.gerarRelatorio(id, data);
        } catch (UsuarioInvalidoException | ApiRequestException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    @PostMapping(value = "/cancelar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String cancelar(@PathVariable long id){
        try {
            servicoService.cancelar(id);
            return "Serviço cancelado com sucesso";
        } catch (BuscaNullException e) {
            return e.getMessage();
        }
    }
}
