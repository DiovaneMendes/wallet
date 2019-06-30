package br.com.dbc.wallet.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PAGAMENTOS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Pagamento.class)
public class Pagamento {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "PAGAMENTO_SEQ", sequenceName = "PAGAMENTO_SEQ")
    @GeneratedValue(generator = "PAGAMENTO_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, name = "VALOR_PAGO")
    private Double valorPago;

    @Column(nullable = false, name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @ManyToOne
    @JoinColumn(nullable = false, name = "ID_SERVICO")
    private Servico servico;

    public Pagamento() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
