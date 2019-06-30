package br.com.dbc.wallet.DTO;

import br.com.dbc.wallet.Entity.Servico;

import java.util.ArrayList;
import java.util.List;

public class RelatorioDTO {

    private String nomeUsuario;

    private double gastoMensalAtual;

    private double estimativaProximoMes;

    private Servico servicoMaisCaro;

    private List<Servico> servicosContratados = new ArrayList<>();

    private List<GastoTotalMesDTO> ultimosGastos = new ArrayList<>();

    public RelatorioDTO() {
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public double getGastoMensalAtual() {
        return gastoMensalAtual;
    }

    public void setGastoMensalAtual(double gastoMensalAtual) {
        this.gastoMensalAtual = gastoMensalAtual;
    }

    public double getEstimativaProximoMes() {
        return estimativaProximoMes;
    }

    public void setEstimativaProximoMes(double estimativaProximoMes) {
        this.estimativaProximoMes = estimativaProximoMes;
    }

    public Servico getServicoMaisCaro() {
        return servicoMaisCaro;
    }

    public void setServicoMaisCaro(Servico servicoMaisCaro) {
        this.servicoMaisCaro = servicoMaisCaro;
    }

    public List<Servico> getServicosContratados() {
        return servicosContratados;
    }

    public void setServicosContratados(List<Servico> servicosContratados) {
        this.servicosContratados = servicosContratados;
    }

    public List<GastoTotalMesDTO> getUltimosGastos() {
        return ultimosGastos;
    }

    public void setUltimosGastos(List<GastoTotalMesDTO> ultimosGastos) {
        this.ultimosGastos = ultimosGastos;
    }
}
