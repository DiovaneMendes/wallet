package br.com.dbc.wallet.DTO;

import br.com.dbc.wallet.Enuns.Mes;

public class GastoTotalMesDTO {

    private Mes mes;

    private double valor;

    public GastoTotalMesDTO(Mes mes, double valor) {
        this.mes = mes;
        this.valor = valor;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GastoTotalMesDTO)) return false;
        GastoTotalMesDTO that = (GastoTotalMesDTO) o;
        return Double.compare(that.getValor(), getValor()) == 0 &&
                getMes() == that.getMes();
    }
}
