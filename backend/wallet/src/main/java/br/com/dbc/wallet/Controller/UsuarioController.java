package br.com.dbc.wallet.Controller;

import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Situacao;
import br.com.dbc.wallet.Exception.AtributoObrigatorioException;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Exception.UsuarioInvalidoException;
import br.com.dbc.wallet.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String registrar(@RequestBody Usuario usuario) {
        try {
            usuario.setSituacao(Situacao.ATIVO);
            usuarioService.salvar(usuario);
            return "Usuario registrado com sucesso.";
        } catch (AtributoObrigatorioException e) {
            return e.getMessage();
        }
    }

    @PostMapping(value = "/login")
    public void login(){
    }

    @PostMapping(value = "/atualizar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String atualizar(@PathVariable long id, @RequestBody Usuario usuario) {
        try {
            usuarioService.editar(id, usuario);
            return "Usuario alterado com sucesso";
        } catch (UsuarioInvalidoException e) {
            return e.getMessage();
        }
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<Usuario> buscarTodos() {
        try {
            return usuarioService.buscarTodos();
        } catch (BuscaNullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Usuario buscarPorID(@PathVariable long id) {
        try {
            return usuarioService.buscarPorID(id);
        }catch (BuscaNullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/atual")
    @ResponseBody
    public Usuario buscarAtual(HttpServletRequest request) {
        try {
            String login = request.getUserPrincipal().getName();
            return usuarioService.findByLogin(login);
        }catch (UsuarioInvalidoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping(value = "/deletar")
    @ResponseBody
    public String deletar(@RequestBody Usuario usuario){
        try {
            usuarioService.remover(usuario);
            return "Usuario removido com sucesso";
        } catch (UsuarioInvalidoException e) {
            return e.getMessage();
        }
    }


}
