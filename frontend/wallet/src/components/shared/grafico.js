import React, {Component} from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Legend } from 'recharts';
import ConteudoGrafico from '../../models/conteudoGrafico'

export default class Grafico extends Component {
  gastos = () => {
    return this.props.listaValores.map( e =>
      new ConteudoGrafico( this.formataMarco(e.mes), e.valor)
    )
  }

  formataMarco = mes => {
    return mes === 'Marco' ? 'Março' : mes
  }

  render () {
    return (
      <BarChart
        width={350}
        height={180}
        data={ this.gastos() }
        margin={{top: 15, right: 45, left: 0, bottom: 3}}
        >

        <CartesianGrid strokeDasharray='3 3'/>
        <YAxis dataKey='valor'/>
        <XAxis dataKey='mes'/>
        <Legend />
        <Bar dataKey="valor" stackId="a" fill="#82ca9d"/>
      </BarChart>
    )
  }
}