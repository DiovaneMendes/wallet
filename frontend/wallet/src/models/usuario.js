
export default class Usuario {
  constructor( id, perfil, nome, login, email, senha, situacao ){
    this.id = id;
    this.perfil = perfil;
    this.nome = nome;
    this.login = login;
    this.email = email;
    this.senha = senha;
    this.situacao = situacao;
  }
}