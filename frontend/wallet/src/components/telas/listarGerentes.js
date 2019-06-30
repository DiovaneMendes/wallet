
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
import ListaUsuarios from '../../models/listaUsuarios'

class ListagemGerentes extends Component {    
  constructor(props) {
    super(props)
    this.walletApi = new WalletApi()
    this.state = {
      listaUsuarios: ''
    }
  }
  
  componentDidMount() {
    this.walletApi.buscarTodosUsuarios()
      .then( todosUsuarios => {
            this.setState({ listaUsuarios: new ListaUsuarios( todosUsuarios.data ) })
        }   
      )
  }
  
  renderizarGerentes() {
    return ( 
      <ListGroup>
        <ListGroupItem style={{margin:'1px 0px'}}>
          <div className='div-list-Gerente-leg'>Nome</div>
          <div className='div-list-Gerente-leg'>Email</div>
        </ListGroupItem>
        { this.state.listaUsuarios.gerentes.map( g => 
          <ListGroupItem className="listagem" style={{margin:'1px 0px'}}><div className='div-list-gerentes'>{g.nome}</div> <div className='div-list-gerentes'>{g.email}</div>
            <Link to={{ pathname: "/dashboard", state: {id: g.id} }} >
              <img src={edit} className="img" title="Ver Relatório"></img>
            </Link>
          </ListGroupItem> )}
      </ListGroup> )        
  }    
    
  render() {
    return (
      !this.state.listaUsuarios ? (
        <Carregando />
      ) : (
        <Fragment>
          <NavBar />
          <HeaderPadrao />
          <h2> Lista de Gerentes Cadastrados</h2>
          <div className = "fonts-list ">
            { this.renderizarGerentes() }
          </div>
          
        </Fragment>
      )
    )
  }
}

export default withRouter(ListagemGerentes)
