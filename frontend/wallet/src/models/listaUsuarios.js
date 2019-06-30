import Usuario from './usuario'

export default class ListaUsuarios {
  constructor( usuariosDaApi ){
    this.todos = usuariosDaApi.map( e => new Usuario( e.id, e.perfil, e.nome, e.login, e.email, e.senha, e.situacao))
  }

  get gerentes(){
    return this.todos.filter( e => e.perfil === "GERENTE" && e.situacao === "ATIVO")
  }
}