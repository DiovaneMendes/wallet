package br.com.dbc.wallet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CotacaoDTO {

    private String base;

    private Object rates;

    private LocalDate date;

    public CotacaoDTO() {}

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Object getRates() {
        return rates;
    }

    public void setRates(Object rates) {
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CotacaoDTO{" +
                "base='" + base + '\'' +
                ", rates=" + rates +
                ", date=" + date +
                '}';
    }
}
