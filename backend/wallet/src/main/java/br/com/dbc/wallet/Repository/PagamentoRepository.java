package br.com.dbc.wallet.Repository;

import br.com.dbc.wallet.Entity.Pagamento;
import br.com.dbc.wallet.Entity.Servico;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PagamentoRepository extends CrudRepository<Pagamento, Long> {
    List<Pagamento> findAllByValorPago(Double valorPago);
    List<Pagamento> findAllByDataPagamento(LocalDate data);
    List<Pagamento> findAllByServico(Servico servico);
}
