
export default class Servico {
  constructor( id, nome, descricao, website, valor, periodicidade ){
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
    this.website = website;
    this.valor = valor;
    this.periodicidade = periodicidade;
    this.contratacao = { valorBRL: 0, periodicidade: '-' };
  }
}