package model;

import java.time.LocalDate;
import Enum.StatusPagamento;
import Enum.MetodoPagamento;

public class Pagamento {
    protected int idPagamento;
    protected int idPedido;
    protected int idCLiente;
    protected Pedido pedido;
    protected double valorTotal;
    protected MetodoPagamento metodoPagamento;
    protected StatusPagamento statusPagamento;
    protected LocalDate dataPagamento;


    public Pagamento() {
    }

    public Pagamento(int idPagamento, int idPedido, int idCLiente,Pedido pedido, double valorTotal, MetodoPagamento metodoPagamento, StatusPagamento statusPagamento, LocalDate dataPagamento) {
        this.idPagamento = idPagamento;
        this.idPedido = idPedido;
        this.idCLiente = idCLiente;
        this.pedido = pedido;
        this.valorTotal = valorTotal;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = statusPagamento;
        this.dataPagamento = dataPagamento;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCLiente() {
        return idCLiente;
    }

    public void setIdCLiente(int idCLiente) {
        this.idCLiente = idCLiente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}