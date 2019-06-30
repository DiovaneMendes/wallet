import React, { Component } from 'react'
import { withRouter, Link } from "react-router-dom"
import { Col, Container, Card, Button, Input, Form, FormGroup, Label } from 'reactstrap'

import '../css/login.css'
import '../css/App.css'
import '../css/mensagens.css'
import '../css/imagem.css'
import HeaderPadrao from '../shared/HeaderPadrao'

import WalletApi from '../../services/walletApi'

class Login extends Component {

constructor(props) {
  super(props)
  this.walletApi = new WalletApi()
  this.state = {
    login: '',
    senha: '',
    erro: ''
  }  
}

logar = async e => {
  e.preventDefault();

  const { login, senha } = this.state;
  if (!login || !senha) {
    this.setState({ erro: "Preencha username e senha para continuar!" });
  } else {
    try {
      const response = await this.walletApi.login( { login, senha });      
      sessionStorage.setItem("wallets", response.headers.authorization)
      this.props.history.push("/registrar_servico");
    } catch (err) {
      this.setState({
        erro: "Houve um problema com o login, verifique suas credenciais!"
      });
    }
  }
}

cadastrar = async e => {
  e.preventDefault();                
      this.props.history.push("/registrar_usuario");
  }

render() {
  return (
    <React.Fragment>
      <HeaderPadrao />
      <Container style={{ maxWidth: '600px', marginTop:'70px'}}>
        <Card style={{ padding: '30px', border: '60px', borderRadius:'30px', boxShadow:' 4px 4px 11px rgba(0, 0, 0, 0.6)'}}>
          { this.state.erro && <p className="mensagem-erro">{ this.state.erro }</p> }
          <Form className = "form-login" onSubmit={ this.logar }>
            <Col>
              <div className = "box font-login">
                <h2 className = "h2-login">Iniciar Sessão</h2>
                <FormGroup>
                    <Label className ="Login-Body"> Usuario </Label>
                    <Input 
                      type="text" 
                      placeholder="usuario" 
                      className="Login-Body" 
                      value={ this.state.login } 
                      onChange={ e => this.setState({ login: e.target.value }) }
                    />                                        
                </FormGroup>
                <FormGroup>
                    <Label className ="Login-Body"> Senha </Label>
                    <Input 
                      type="password"
                      placeholder="senha"
                      className="Login-Body"
                      value={ this.state.senha }
                      onChange={ e => this.setState({ senha: e.target.value }) }
                    />
                </FormGroup>
                <div className = "buttom-login">
                  <Button color="success">Iniciar Sessão</Button>
                </div>
                <div className ="sem-cadastro">
                  <h6>Ainda não está cadastrado?</h6> 
                  <h6><Link to="/registrar_usuario">Clique aqui!</Link></h6>  
                </div>                
              </div>              
          </Col>          
            
        </Form>
        </Card>
      </Container>
    </React.Fragment>
    )
  }   
}

export default withRouter(Login);