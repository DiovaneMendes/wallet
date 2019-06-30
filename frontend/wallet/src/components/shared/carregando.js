import React, {Component} from 'react';

import wallet from '../images/wallet.png'
import '../css/carregando.css'

export default class Carregando extends Component {

  render() {
    return(
      <div id="loading-wrapper">
        <div id="loading-text">
          <img
            src= {wallet}
            className = "Logo-img"
            style={{margin: '-35px 0px 0px 20px '}}>
          </img>

          <span> Carregando... </span>
        </div>
        <div id="loading-content"></div>
      </div>
    )
  }
}