import React from "react";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";

import Login from "../components/telas/login";
import Dashboard from '../components/telas/dashboard';
import RegistrarUsuario from '../components/telas/registrarUsuario';
import RegistrarServico from '../components/telas/registrarServico';
import ListarGerentes from '../components/telas/listarGerentes';
import ListagemServicos from '../components/telas/listarServicos';


const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route {...rest} render = { props => sessionStorage.getItem("wallets") ? 
      ( <Component {...props} /> ) : 
      ( <Redirect to={{ pathname: "/", state: { from: props.location } }} /> ) } />
);

const Routes = () => (
  <BrowserRouter>
    <Switch>
      <Route exact path="/" component={ Login } />
      <Route exact path="/registrar_usuario" component={ RegistrarUsuario } />

      <PrivateRoute exact path="/dashboard" component={ Dashboard } />      
      <PrivateRoute exact path="/registrar_servico" component={ RegistrarServico } />      
      <PrivateRoute exact path="/listar_gerentes" component={ ListarGerentes } />
      <PrivateRoute exact path="/listar_servicos" component={ ListagemServicos } />
      <Route path="*" component={() => <h1>Página inexistente!</h1>} />    
    </Switch>
  </BrowserRouter>
);

export default Routes;