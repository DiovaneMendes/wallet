package br.com.dbc.wallet.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "SERVICOS")
public class Servico {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "SERVICO_SEQ", sequenceName = "SERVICO_SEQ")
    @GeneratedValue(generator = "SERVICO_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "ID_USUARIO")
    private Usuario usuario;

    @Column(nullable = false, name = "NOME")
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "WEBSITE")
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "ID_CONTRATACAO")
    private Contratacao contratacao;

    @Column(nullable = false, name = "ESTA_ATIVO")
    private boolean estaAtivo;

    @OneToMany(mappedBy = "servico")
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Servico() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Contratacao getContratacao() {
        return contratacao;
    }

    public void setContratacao(Contratacao contratacao) {
        this.contratacao = contratacao;
    }

    public boolean getEstaAtivo() {
        return estaAtivo;
    }

    public void setEstaAtivo(boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void pushPagamentos(Pagamento... pagamentos) {
        this.pagamentos.addAll( Arrays.asList(pagamentos) );
    }

}
