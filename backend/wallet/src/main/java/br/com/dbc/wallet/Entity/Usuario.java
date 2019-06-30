package br.com.dbc.wallet.Entity;

import br.com.dbc.wallet.Enuns.Perfil;
import br.com.dbc.wallet.Enuns.Situacao;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "USUARIOS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Usuario.class)
public class Usuario {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ")
    @GeneratedValue(generator = "USUARIO_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "PERFIL")
    private Perfil perfil;

    @Column(nullable = false, name = "NOME")
    private String nome;

    @Column(nullable = false, name = "LOGIN", unique = true)
    private String login;

    @Column(nullable = false, name = "EMAIL", unique = true)
    private String email;

    @Column(nullable = false, name = "SENHA")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "SITUACAO")
    private Situacao situacao;

    @OneToMany(mappedBy = "usuario")
    private List<Servico> servicos = new ArrayList<>();

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void pushServicos(Servico... servicos){
        this.servicos.addAll( Arrays.asList(servicos) );
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null ) return false;
        if( obj.getClass() != Usuario.class ) return false;
        if( obj == this ) return true;
        Usuario outro = (Usuario) obj;
        if( outro.getId() == id ) return true;
        return false;
    }

}
