package br.com.dbc.wallet.Controller;

import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Service.ContratacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/contratacao")
public class ContratacaoController {

    @Autowired
    private ContratacaoService contratacaoService;

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Contratacao salvar(@RequestBody Contratacao contratacao) {
        return contratacaoService.salvar(contratacao);
    }

    @PutMapping(value = "/atualizar/{id}")
    @ResponseBody
    public Contratacao atualizar(@PathVariable long id, @RequestBody Contratacao contratacao) {
        return contratacaoService.editar(id, contratacao);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<Contratacao> buscarTodos() {
        try {
            return contratacaoService.buscarTodos();
        } catch (BuscaNullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Contratacao buscarPorID(@PathVariable long id) {
        try {
            return contratacaoService.buscarPorID(id);
        }catch (BuscaNullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping(value = "/deletar")
    public void deletar(@RequestBody Contratacao contratacao) {
        contratacaoService.remover(contratacao);
    }
}
