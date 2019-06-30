import React, { Component, Fragment } from 'react'
import { withRouter } from "react-router-dom"
import { Row, Col, Button, Modal, ModalHeader, ModalBody, ModalFooter, ListGroup, ListGroupItem } from 'reactstrap'

import '../css/cards.css'
import '../css/dashboard.css'
import '../css/grafico.css'

import NavBar from '../shared/navBar'
import Grafico from '../shared/grafico'
import Carregando from '../shared/carregando'
import HeaderPadrao from '../shared/HeaderPadrao'
import GerenteLogo from '../images/avatar_gerente.png'
import ConteudoGrafico from '../../models/conteudoGrafico'
import WalletApi from '../../services/walletApi'
import formatadorEnuns from '../../services/formatadorEnuns'
import Servico from '../../models/servico';

class Dashboard extends Component {  
  constructor(props) {
    super(props)
    this.walletApi = new WalletApi()
    this.state = {
      nomeUsuario: '',
      gastoMensalAtual: 0,
      estimativaProximoMes: 0,
      servicoMaisCaro: null,
      servicosContratados: null,
      ultimosGastos: [],
      modal: false
    }
    this.toggle = this.toggle.bind(this)
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal
    }));
  }

  componentDidMount() {
    if( this.props.location.state ) {
      this.walletApi.buscarRelatorioPorID(this.props.location.state.id)
      .then( dados => this.montarDashboard(dados.data)        
      )
    } else {
      this.walletApi.buscarRelatorio()
      .then( dados => this.montarDashboard(dados.data)        
      )
    }    
  }

  montarDashboard = (r) => {
    console.log(this.props);
        
    if( r.servicoMaisCaro ) {
      this.setState({
        nomeUsuario: r.nomeUsuario,
        gastoMensalAtual: r.gastoMensalAtual,
        estimativaProximoMes: r.estimativaProximoMes,
        servicoMaisCaro: r.servicoMaisCaro,
        servicosContratados: r.servicosContratados,
        ultimosGastos: r.ultimosGastos.map(e =>
            new ConteudoGrafico( formatadorEnuns(e.mes), e.valor)
        )
      })
    } else {
      this.setState({
        nomeUsuario: r.nomeUsuario,
        gastoMensalAtual: r.gastoMensalAtual,
        estimativaProximoMes: r.estimativaProximoMes,
        servicoMaisCaro: new Servico(0, '-', '-', '-', 0, '-'),
        servicosContratados: r.servicosContratados,
        ultimosGastos: r.ultimosGastos.map(e =>
            new ConteudoGrafico( formatadorEnuns(e.mes), e.valor)
        )
      })    
    }
  }

  listaServicos = () => {
    return (
      <ListGroup>
        <ListGroupItem style={{margin:'1px 0px'}}>
          <div className='div-list-servicos-leg'>Nome</div>
          <div className='div-list-servicos-leg'>Valor</div>
        </ListGroupItem>
        { this.state.servicosContratados.map( s =>
          <ListGroupItem className="listagem" style={{margin:'1px 0px',border: 'none'}}>
            <div className='div-list-servicos'>{ s.nome }</div> 
            <div className='div-list-servicos'>{ s.contratacao.valorBRL.toLocaleString('pt-BR',{style:'currency',currency:'BRL'})}</div> 
          </ListGroupItem>
        )}
      </ListGroup>    
     )
  }

  render() {
    const { nomeUsuario, gastoMensalAtual, estimativaProximoMes, servicoMaisCaro, ultimosGastos } = this.state
    return (
      !nomeUsuario ? (
        <Carregando />
      ) : (
      <Fragment>
        <NavBar />
        <HeaderPadrao />
        <Row>
          <Col>
            <div className="div-card" onClick={this.toggle}>
              <Modal isOpen={this.state.modal} fade={false} toggle={this.toggle} className={this.props.className}>
              <ModalHeader toggle={this.toggle}>Grafico Gasto Mensal</ModalHeader>
                <ModalBody className="grafico-modal">
                  <Grafico 
                    listaValores={ ultimosGastos }
                  />
                  </ModalBody>
                <ModalFooter>
                  <Button color="secondary" onClick={this.toggle}>Sair</Button>
                </ModalFooter>
              </Modal>
              <div className="div-title"><h2 className="h2-title" >Gasto no Último Ano  </h2></div>
              <Grafico
                listaValores={ ultimosGastos }
              />
            </div>
          </Col>
          <Col>
            <div className="div-card div-left">
              <div className="div-title"><h2 className="h2-title">Lista de Serviços</h2></div>
              { this.listaServicos() }
            </div>
          </Col>
         
          <Col>
            <div className="div-card">
              <div className="div-title"><h2 className="h2-title"> Serviço Mais Caro </h2></div>
              <div className='mais-caro'> Nome: <span className='span-mais-caro'>{ servicoMaisCaro.nome }</span> </div>
              <div className='mais-caro'> Descrição: <span className='span-mais-caro'>{ servicoMaisCaro.descricao }</span> </div>
              <div className='mais-caro'> Valor: <span className='span-mais-caro'>{ servicoMaisCaro.contratacao.valorBRL.toLocaleString('pt-BR',{style:'currency',currency:'BRL'}) }</span> </div>
              <div className='mais-caro'> Periodo: <span className='span-mais-caro'>{ formatadorEnuns(servicoMaisCaro.contratacao.periodicidade) } </span></div>
            </div>
          </Col>
        </Row>
        <Row>
          <Col>
            <div className="div-rowTwo gasto-mensal">
            <div className="div-title"><h2 className="h2-title">Gasto Mês Atual </h2></div>
            <div className="div-valor">  { gastoMensalAtual.toLocaleString('pt-BR',{style:'currency',currency:'BRL'}) }</div>
            </div>
          </Col>
          <Col>
            <div className="div-ghost">
              <h4 className="name-manager">Gerente Responsável
                <div>
                  <img  className='logo-gerente'src={GerenteLogo}></img>
                </div> 
                { nomeUsuario }
              </h4>
            </div>
          </Col>
          <Col>
            <div className="div-rowTwo">
            <div className="div-title"><h2 className="h2-title">Estimativa Mês Seguinte</h2></div>
            <div className="div-valor">{ estimativaProximoMes.toLocaleString('pt-BR',{style:'currency',currency:'BRL'}) }</div>
            </div>
          </Col>
          
        </Row>
      </Fragment>
    )
  )}
}

export default withRouter(Dashboard);


         