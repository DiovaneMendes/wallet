import React, { Component, Fragment } from 'react'
import { Link, withRouter } from "react-router-dom"
import { ListGroup, ListGroupItem } from 'reactstrap'

import '../css/lista.css'
import '../css/imagem.css'
import Carregando from '../shared/carregando'

import edit from '../images/icon_edit.png'
import excluir from '../images/icon_delete.png'
import plus from '../images/icon_plus.png'

import NavBar from '../shared/navBar'
import HeaderPadrao from "../shared/HeaderPadrao"
import WalletApi from '../../services/walletApi'
import formatadorEnuns from '../../services/formatadorEnuns'
import ListaServicos from '../../models/listaServicos'

class ListagemServicos extends Component {

  constructor(props) {
    super(props)
    this.walletApi = new WalletApi()
    this.state = {
      listaServicos: ''
    }
  }

  componentDidMount() {
    this.listaServico()
  }

  cancelar = id => {
    this.walletApi.cancelarServico(id);
    window.location.reload();
  }

  listaServico = () => {
    this.walletApi.buscarTodosServicosAtivos()
      .then( todosServicos => {
          this.setState({ listaServicos: new ListaServicos( todosServicos.data ) })
        }
      )
  }

  renderizarServicos() {
    return (
      <ListGroup>
        <ListGroupItem style={{margin:'1px 0px'}}>
          <div className='div-list-legenda'>Nome</div>
          <div className='div-list-legenda'>Periodicidade</div>
          <div className='div-list-legenda'>Valor</div>
        </ListGroupItem>
        { this.state.listaServicos.todos.map( s =>
          <ListGroupItem className="listagem" style={{margin:'1px 0px'}}>
            <div className='div-list'>{ s.nome }</div>
            <div className='div-list'>{ formatadorEnuns(s.periodicidade) }</div>
            <div className='div-list'> {s.valor.toLocaleString('pt-BR',{style:'currency',currency:'BRL'})}</div>
            <Link onClick={ () => { this.cancelar( s.id ) } }>
              <img src={excluir} className="img" title="Cancelar"></img>
            </Link>
          </ListGroupItem> )}
      </ListGroup> )
  }

  render() {
    return (
      !this.state.listaServicos ? (
          <Carregando />
        ) : (
      <Fragment>
        <NavBar />
          <HeaderPadrao />
          <h2> Lista de Serviços Cadastrados</h2>
        <div className="fonts-list ">
          { this.renderizarServicos() }
        </div>
        <span>
          <Link to="/registrar_servico">
            <img src={plus} className="logo_plus" title="Registrar Serviço"></img>
          </Link>
        </span>
      </Fragment>
    )
  )}
}

export default withRouter(ListagemServicos);
