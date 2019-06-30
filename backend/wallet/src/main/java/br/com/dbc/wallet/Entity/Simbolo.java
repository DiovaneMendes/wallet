package br.com.dbc.wallet.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "SIMBOLOS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Simbolo.class)
public class Simbolo {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "SIMBOLO_SEQ", sequenceName = "SIMBOLO_SEQ")
    @GeneratedValue(generator = "SIMBOLO_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, name = "SIGLA", unique = true)
    private String sigla;

    @Column(nullable = false, name = "NOME")
    private String nome;

    @OneToMany(mappedBy = "simbolo")
    private List<Contratacao> contratacoes = new ArrayList<>();

    public Simbolo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public List<Contratacao> getContratacoes() {
        return contratacoes;
    }

    public void pushContratacoes(Contratacao... contratacoes){
        this.contratacoes.addAll( Arrays.asList(contratacoes) );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != Simbolo.class)
            return false;
        Simbolo otherSimbolo = (Simbolo) obj;
        if (otherSimbolo.getId() == this.getId() &&
                otherSimbolo.getSigla().equals( this.getSigla() ) &&
                otherSimbolo.getNome().equals( this.getNome() ) )
            return true;
        return false;
    }
}
