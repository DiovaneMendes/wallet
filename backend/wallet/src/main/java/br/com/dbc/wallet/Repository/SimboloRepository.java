package br.com.dbc.wallet.Repository;

import br.com.dbc.wallet.Entity.Simbolo;
import org.springframework.data.repository.CrudRepository;

public interface SimboloRepository extends CrudRepository<Simbolo, Long> {
    Simbolo findBySigla(String sigla);
    Simbolo findByNome(String nome);
}
