import Servico from './servico'

export default class ListaServicos {
  constructor( servicosDaApi ){
    this.todos = servicosDaApi.map( e => new Servico( e.id, e.nome, e.descricao,
        e.website, e.contratacao.valorBRL, e.contratacao.periodicidade ) )
  }
}