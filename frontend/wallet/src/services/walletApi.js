import axios from 'axios'

export default class WalletApi{
  baseURL(){
    return "http://localhost:8080/api"
  }

  registraUsuario( {perfil, nome, login, email, senha, situacao} ){
    return axios.post( `${ this.baseURL() }/usuario/registrar`,
      {perfil, nome, login, email, senha, situacao})
  }

  login( {login, senha} ){
    return axios.post( `${ this.baseURL() }/usuario/login`, 
      { login, senha } )
  }

  buscarRelatorio(){
    return axios.get( `${ this.baseURL() }/servico/relatorio/`, { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

  buscarRelatorioPorID( id ){
    return axios.get( `${ this.baseURL() }/servico/relatorio/${id}`, { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

  registraServico( { usuario, nome, descricao, website, valor, periodicidade, simbolo, dataContratacao} ){
    return axios.post( `${ this.baseURL() }/servico/salvar`,
      { usuario, nome, descricao, website, valor, periodicidade, simbolo, dataContratacao},
      { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

  buscarTodosUsuarios(){
    return axios.get(`${ this.baseURL() }/usuario/`, 
      { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

  buscarUsuarioAtual(){
    return axios.get(`${ this.baseURL() }/usuario/atual`, 
      { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

  buscarSimbolos(){
    return axios.get(`${ this.baseURL() }/simbolo/`, 
      { headers: { Authorization: sessionStorage.getItem("wallets") }})
  }

  buscarTodosServicos() {
    return axios.get( `${ this.baseURL() }/servico/`, 
      { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

  buscarTodosServicosAtivos() {
    return axios.get( `${ this.baseURL() }/servico/ativos`,
      { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }
  
  cancelarServico( id ){
    return axios.post(`${ this.baseURL() }/servico/cancelar/${ id }`,
      { headers: { Authorization: sessionStorage.getItem("wallets") }} )
  }

}