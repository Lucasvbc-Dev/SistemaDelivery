package model;

import java.sql.Time;
import java.util.Date;
import Enum.StatusEntrega;

public class Entrega {
    protected int idEntrega;
    protected int idPedido;
    protected int idEntregador;
    protected StatusEntrega statusEntrega;
    protected Date dataSaida;
    protected Date dataEntrega;
    protected String tempoEstimado;
    protected String observacao;

    public Entrega(){}

    public Entrega(int idEntrega, int idPedido, int idEntregador, StatusEntrega statusEntrega, Date dataSaida, Date dataEntrega, String tempoEstimado, String observacao) {
        this.idEntrega = idEntrega;
        this.idPedido = idPedido;
        this.idEntregador = idEntregador;
        this.statusEntrega = statusEntrega;
        this.dataSaida = dataSaida;
        this.dataEntrega = dataEntrega;
        this.tempoEstimado = tempoEstimado;
        this.observacao = observacao;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(int idEntregador) {
        this.idEntregador = idEntregador;
    }

    public StatusEntrega getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(StatusEntrega statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public java.util.Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(java.util.Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public java.util.Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(java.util.Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }


    public String getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(String tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
