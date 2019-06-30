import React, { Component } from 'react'
import {Navbar,NavbarBrand,Nav,NavItem,NavLink,} from 'reactstrap';
import wallet from '../images/wallet.png'
import icon_logout from "../images/icon_logout.png";
import icon_list from "../images/icon_list.png";
import add_service from "../images/add_service.png";
import icon_list_users from "../images/icon_list_avatar.png";
import { Link } from "react-router-dom";
import WalletApi from '../../services/walletApi'

class NavBar extends Component{    
  
  constructor(props) {
    super(props)
    this.walletApi = new WalletApi()
    this.state = {
      perfil: ''
    }  
  }

  componentDidMount() {
    this.walletApi.buscarUsuarioAtual()
      .then( u => {
            this.setState({ perfil: u.data.perfil })
        }   
      )
  }
  
  
  render(){
    

    
    return (
      <div style={{width:"96%", boxShadow:'3px 3px 11px rgba(0, 0, 0, 0.4)', marginTop:'10px'} }>
        <Navbar color="light" light expand="md">
          <NavbarBrand >
            <Link to="/dashboard"><img src={wallet} className="logo-navbar" title="Dashboard"></img></Link>
          </NavbarBrand>

          <Nav className="ml-auto" navbar>
            <NavItem>
              <NavLink>
                <Link to="/registrar_servico"><img src={add_service} className="logo-navbar" title="Registrar Serviço"></img></Link>
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink>
                <Link to="/listar_servicos"><img src={icon_list} className="logo-navbar" title="Lista de Serviços"></img></Link>
              </NavLink>
            </NavItem>

            {this.state.perfil === "ADMINISTRADOR" ? (<NavItem>
              <NavLink>
                <Link to="/listar_gerentes"><img src={icon_list_users} className="logo-navbar" title="Lista de Gerentes"></img></Link>
              </NavLink>
            </NavItem>) : ""}

            <NavItem>
              <NavLink>
                <Link to="/" onClick={ () => sessionStorage.removeItem("wallets") }><img src={icon_logout} className="logo-navbar" title="Logout"></img></Link>
              </NavLink>
            </NavItem>
          </Nav>        
        </Navbar>
      </div>
    )
  } 
}
  
export default NavBar