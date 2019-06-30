import React from 'react'
import '../css/login.css'
import '../css/imagem.css'
import walletHeader from '../images/logo-header.png'


export default class HeaderPadrao extends React.Component{
    render(){ 
        return (
            <div style={{marginTop:'-25px'}}>
                <img src= {walletHeader} className = "Logo-img-logo"></img>   
            </div> 
        )
    }
}
