package br.com.dbc.wallet.Repository;

import br.com.dbc.wallet.Entity.Contratacao;
import br.com.dbc.wallet.Enuns.Periodicidade;
import br.com.dbc.wallet.Entity.Simbolo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContratacaoRepository extends CrudRepository<Contratacao, Long> {
    List<Contratacao> findAllByPeriodicidade(Periodicidade periodicidade);
    List<Contratacao> findAllBySimbolo(Simbolo simbolo);
    List<Contratacao> findAllByValorBase(double valorBase);
    List<Contratacao> findAllByValorUSD(double valorUSD);
    List<Contratacao> findAllByValorBRL(double valorBRL);
    List<Contratacao> findAllByDataAniversario(LocalDate data);
}
