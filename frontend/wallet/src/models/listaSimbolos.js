import Simbolo from './simbolo'

export default class ListaSimbolos {
  constructor( simbolosDaApi ) {
    this.todos = simbolosDaApi.map(s => new Simbolo( s.id, s.sigla, s.nome ))
  }

  get todosSimbolos(){
    return this.todos
  }
}