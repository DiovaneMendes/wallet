import React, { Component, Fragment } from 'react'
import { withRouter } from "react-router-dom"
import {Container, Col, Row, Button, Form, FormGroup, Label, Input, InputGroup, InputGroupAddon, Card} from 'reactstrap'

import '../css/registrarUsuario.css'
import '../css/mensagens.css'

import ListaUsuarios from '../../models/listaUsuarios'
import ListaSimbolos from '../../models/listaSimbolos'
import Usuario from "../../models/usuario"
import Simbolo from "../../models/simbolo"

import WalletApi from '../../services/walletApi'
import NavBar from '../shared/navBar'
import Carregando from '../shared/carregando'
import HeaderPadrao from "../shared/HeaderPadrao";


class RegistrarServico extends Component {
  constructor(props) {
    super(props)
    this.walletApi = new WalletApi()
    this.state = {
      listaUsuarios: null,
      listaSimbolos: null,
      idUsuario: 1,
      nome: '',
      descricao: '',
      website: '',
      valor: '',
      periodicidade: 'MENSAL',
      idSimbolo: 1,
      dataContratacao: '',
      erro:''
    }
  }

  componentDidMount() {
    const requisicoes = [ this.walletApi.buscarTodosUsuarios(), this.walletApi.buscarSimbolos() ]
    Promise.all( requisicoes )
      .then( resultados => {
        this.setState({
          listaUsuarios: new ListaUsuarios( resultados[0].data ).gerentes,
          listaSimbolos: new ListaSimbolos( resultados[1].data ).todosSimbolos
        })
      })
  }

  registrar = async e => {
    e.preventDefault();

    const { idUsuario, nome, descricao, website,
            valor, periodicidade, idSimbolo, dataContratacao } = this.state

    if ( this.validarCamposVazios() ) {
      this.setState({ erro: "Todos campos devem ser preenchidos!" })
    }
    else {
      const usuario = new Usuario(idUsuario);
      const simbolo = new Simbolo(idSimbolo);
      await this.walletApi.registraServico( { usuario, nome, descricao, website,
                            valor, periodicidade, simbolo, dataContratacao} );
      this.props.history.push("/listar_servicos");
    }
  }

  validarCamposVazios = () => {
    const { nome, valor, dataContratacao }= this.state

    return nome === '' || valor < 0  || dataContratacao === null
  }

  opcoesGerentes = () => {
    return this.state.listaUsuarios.map(g =>
      <option key={g.id} value={g.id}> {g.nome} </option>
    )
  }

  opcoesMoedas = () => {
    return this.state.listaSimbolos.map(s =>
      <option key={s.id} value={s.id} > {s.sigla} - {s.nome} </option>
    )
  }

  render() {
    const { listaUsuarios, nome, descricao, website, valor, dataContratacao, erro } = this.state

    return (
      !listaUsuarios ? (
        <Carregando/>
        ) : (
        <Fragment>
          <NavBar />
          <HeaderPadrao />
          <h3 style={{margin:'0px'}}>Registrar Novo Serviço</h3>
          <div>
            <Container style={{ maxWidth: '800px', color:'gray'}}>
            <Card style={{ padding: '10px 70px', border: '30px', marginTop : '10px', borderRadius:'30px' , boxShadow: '4px 4px 11px rgba(0, 0, 0, 1.0)'}}>
              <Form onSubmit={ this.registrar }>
                <Row form>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="servico"> Nome do Serviço </Label>
                      <Input
                        type="name"
                        name="servico"
                        value={ nome }
                        onChange={ e => this.setState({ nome: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="gerente">  Gerente Responsável </Label>
                      <Input
                        name="gerente"
                        type="select"
                        onChange={ e => this.setState({ idUsuario: e.target.value }) }>
                        { this.opcoesGerentes() }

                      </Input>
                    </FormGroup>
                  </Col>
                </Row>
                <FormGroup>
                  <Label for="text"> Website </Label>
                  <Input
                    type="text"
                    name="text"
                    id="text"
                    value={ website }
                    onChange={ e => this.setState({ website: e.target.value }) }
                  />
                </FormGroup>
                <Row form>
                  <Col md={12}>
                    <FormGroup>
                      <Label for="textarea"> Descrição </Label>
                      <Input
                        type="textarea"
                        name="textarea"
                        id="textarea"
                        value={ descricao }
                        onChange={ e => this.setState({ descricao: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                </Row>

                <Row form>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="moedas"> Moeda de Pagamento </Label>
                      <Input name="moedas" type="select" 
                      onChange={ e => this.setState({ idSimbolo: e.target.value }) }>
                        { this.opcoesMoedas() }
                      </Input>
                    </FormGroup>
                  </Col>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="valor"> Valor </Label>
                      <InputGroup>
                        <Input name="valor" id="textarea" min={0} type="number" step="1" value={ valor }
                          dir="rtl" onChange={ e => this.setState({ valor: e.target.value }) }/>
                        <InputGroupAddon addonType="append">.00</InputGroupAddon>
                      </InputGroup>
                    </FormGroup>
                  </Col>

                </Row>

                <Row form>
                <Col md={6}>
                      <FormGroup>
                        <Label for="periodo"> Periodicidade </Label>
                        <Input type="select" name="periodo" onChange={ e => this.setState({ periodicidade: e.target.value }) }>
                          <option value="MENSAL"> Mensal </option>
                          <option value="TRIMESTRAL"> Trimestral </option>
                          <option value="SEMESTRAL"> Semestral </option>
                          <option value="ANUAL"> Anual </option>
                        </Input>
                      </FormGroup>
                  </Col>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="data"> Data da Contratação </Label>
                      <Input
                        name="data"
                        type="date"
                        value={ dataContratacao }
                        onChange={ e => this.setState({ dataContratacao: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                </Row>

                { erro && <p className="mensagem-erro"> { erro } </p> }
                <div className='buttom'>
                  <Button color="success" type="submit" onClick={ this.registrar }> Registrar </Button>
                </div>
              </Form>
            </Card>
          </Container>
        </div>
      </Fragment>
    )
  )}
}

export default withRouter(RegistrarServico);