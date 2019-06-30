package br.com.dbc.wallet.Controller;

import br.com.dbc.wallet.Entity.Simbolo;
import br.com.dbc.wallet.Exception.BuscaNullException;
import br.com.dbc.wallet.Service.SimboloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/simbolo")
public class SimboloController {

    @Autowired
    private SimboloService simboloService;

    @PostMapping(value = "/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Simbolo salvar(@RequestBody Simbolo simbolo) {
        return simboloService.salvar(simbolo);
    }

    @PutMapping(value = "/atualizar/{id}")
    @ResponseBody
    public Simbolo atualizar(@PathVariable long id, @RequestBody Simbolo simbolo) {
        return simboloService.editar(id, simbolo);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<Simbolo> buscarTodos() {
        try {
            return simboloService.buscarTodos();
        } catch (BuscaNullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Simbolo buscarPorID(@PathVariable long id) {
        try {
            return simboloService.buscarPorID(id);
        }catch (BuscaNullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping(value = "/deletar")
    public void deletar(@RequestBody Simbolo simbolo) {
        simboloService.remover(simbolo);
    }
}
