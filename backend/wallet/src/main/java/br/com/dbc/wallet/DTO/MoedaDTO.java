package br.com.dbc.wallet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoedaDTO implements Serializable {

    private Boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private Object rates;

    public MoedaDTO() {}

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getRates() {
        return rates;
    }

    public void setRates(Object rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "MoedaDTO{" +
                "success=" + success +
                ", timestamp=" + timestamp +
                ", base='" + base  +
                ", date=" + date +
                ", rates=" + rates + '\'' +
                "}";
    }
}
