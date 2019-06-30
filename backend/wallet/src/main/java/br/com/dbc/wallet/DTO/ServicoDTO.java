package br.com.dbc.wallet.DTO;

import br.com.dbc.wallet.Entity.Simbolo;
import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Periodicidade;

import java.time.LocalDate;

public class ServicoDTO {

    private Usuario usuario;

    private String nome;

    private String descricao;

    private String website;

    private Double valor;

    private Periodicidade periodicidade;

    private Simbolo simbolo;

    private LocalDate dataContratacao;

    public ServicoDTO(){}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
}
