import React, { Component } from 'react'
import Routes from './services/routes'

import './components/css/App.css'

export default class App extends Component {
  render() {
    return (
        <React.Fragment className="App">
            <section className="screen">
                <Routes/>
            </section>
        </React.Fragment>
    );
  }
}