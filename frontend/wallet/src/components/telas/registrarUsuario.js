import React, { Component, Fragment } from 'react'
import { withRouter } from "react-router-dom"
import { Container,Col, Row, Button, ButtonGroup, Form, FormGroup, Label, Input, Card } from 'reactstrap'
import { Link } from "react-router-dom";

import '../css/registrarUsuario.css'
import '../css/mensagens.css'

import HeaderPadrao from "../shared/HeaderPadrao";
import NavBar from '../shared/navBar'
import WalletApi from '../../services/walletApi'

class RegistrarUsuario extends Component {
  constructor(props) {
    super(props)
    this.walletApi = new WalletApi()
    this.escolhaPerfil = this.escolhaPerfil.bind(this)
    this.state = {
      perfil: 'ADMINISTRADOR',
      nome: '',
      login: '',
      email: '',
      senha: '',
      verificaSenha: '',
      erro: ''
    }  
  }

  escolhaPerfil(perfil) {
    this.setState({ perfil: perfil.toUpperCase() });
  }

  registrar = async e => {
    e.preventDefault();

    const { perfil, nome, login, email, senha, situacao} = this.state

    if ( this.validarCamposVazios() ) {
      this.setState({ erro: "Todos campos devem ser preenchidos!" })
    }
    else if ( this.validaSenhasIguais() ) {    
      this.setState({ erro: "Senhas incompatíveis!" });
    }
    else {
      try{
        await this.walletApi.registraUsuario( {perfil, nome, login, email, senha, situacao} );
        this.props.history.push("/registrar_servico");
      }catch (err) {
        this.setState({erro: "Credenciais já cadastradas!"})
      }
    }
  }

  validarCamposVazios = () => {
    const { perfil, nome, login, email, senha, verificaSenha} = this.state

    return perfil === '' || nome === '' || login === '' || email === '' ||
           senha === '' || verificaSenha === ''
  }

  validaSenhasIguais = () => {
    const { senha, verificaSenha } = this.state

    return senha !== verificaSenha
  }

  render(){
    const { perfil, nome, login, email, senha, verificaSenha, erro } = this.state
    return (
      <Fragment>
        <HeaderPadrao />
        <h3>Registrar Usuário</h3>
        <div>
          <Container style={{ maxWidth: '600px', color:'gray',marginBottom:'50px' }}>
            <Card style={{ padding: '10px 70px', border: '30px', borderRadius:'30px', boxShadow: '4px 4px 11px rgba(0, 0, 0, 1.0)'}}>
              <Form onSubmit={ this.registrar }>
                <Row form>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="name"> Nome Completo </Label>
                      <Input
                        type="name"
                        name="name"
                        value={ nome }
                        onChange={ e => this.setState({ nome: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="username"> Username </Label>
                      <Input
                        type="username"
                        name="username"
                        id="username"
                        value={ login }
                        onChange={ e => this.setState({ login: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                </Row>
                <FormGroup>
                  <Label for="email"> Email </Label>
                  <Input
                    type="email"
                    name="email"
                    id="email"
                    value={ email }
                    onChange={ e => this.setState({ email: e.target.value }) }
                  />
                </FormGroup>
                <Row form>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="pasword"> Senha </Label>
                      <Input
                        type="password"
                        name="password"
                        id="password"
                        value={ senha }
                        onChange={ e => this.setState({ senha: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                  <Col md={6}>
                    <FormGroup>
                      <Label for="password"> Confirme a senha </Label>
                      <Input
                        type="password"
                        name="password"
                        id="password"
                        value={ verificaSenha }
                        onChange={ e => this.setState({ verificaSenha: e.target.value }) }
                      />
                    </FormGroup>
                  </Col>
                </Row>
                { erro && <p className="mensagem-erro">{ erro }</p> }
                <Row id="perfil-block">
                  <Col md={8} >
                    <FormGroup check id="perfil">
                      <Label for="perfil"> Perfil: {perfil === "GERENTE" ? "Gerente" : "Administrador"} </Label>
                      <ButtonGroup >
                        <Button 
                          color="secondary"
                          onChange={ e => this.setState({ perfil: e.target.value }) }
                          onClick={() => this.escolhaPerfil('Administrador')}
                          active={perfil === 1}> Administrador
                        </Button>
                        <Button 
                          color="secondary"
                          onChange={ e => this.setState({ perfil: e.target.value }) }
                          onClick={() => this.escolhaPerfil('Gerente')}
                          active={perfil === 2}> Gerente
                        </Button>
                      </ButtonGroup>
                    </FormGroup>
                  </Col>
                </Row>
                <Row>
                <Col className='buttom' >
                  { !sessionStorage.getItem("wallets") ? 
                    <Link to="/" >
                      <Button color="success" type="submit"> Voltar </Button>
                    </Link>
                  : "" }
                  <Button  style={{ margin:'10px'}} color="success" type="submit"> Registrar </Button>
                </Col>
                </Row>
              </Form>
            </Card>
          </Container>
        </div>
      </Fragment>
    )
  }
}

export default withRouter(RegistrarUsuario);