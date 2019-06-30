package br.com.dbc.wallet.Entity;

import br.com.dbc.wallet.Enuns.Periodicidade;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CONTRATACOES")
public class Contratacao {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "CONTRATACAO_SEQ", sequenceName = "CONTRATACAO_SEQ")
    @GeneratedValue(generator = "CONTRATACAO_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, name = "VALOR_BASE")
    private double valorBase;

    @Column(nullable = false, name = "VALOR_USD")
    private double valorUSD;

    @Column(nullable = false, name = "VALOR_BRL")
    private Double valorBRL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "PERIODICIDADE")
    private Periodicidade periodicidade;

    @Column(nullable = false, name = "DATA_ANIVERSARIO")
    private LocalDate dataAniversario;

    @ManyToOne
    @JoinColumn(nullable = false, name = "ID_SIMBOLO")
    private Simbolo simbolo;

    public Contratacao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public double getValorUSD() {
        return valorUSD;
    }

    public void setValorUSD(double valorDolar) {
        this.valorUSD = valorDolar;
    }

    public Double getValorBRL() {
        return valorBRL;
    }

    public void setValorBRL(Double valorReal) {
        this.valorBRL = valorReal;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

    public LocalDate getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(LocalDate dataAniversario) {
        this.dataAniversario = dataAniversario;
    }
}
