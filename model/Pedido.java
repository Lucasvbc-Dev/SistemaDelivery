package model;
import Enum.StatusPedido;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Pedido {
    protected int idPedido;
    protected int idCliente;
    protected int idRestaurante;
    protected java.sql.Timestamp dataHora;
    protected StatusPedido status;
    protected double valorTotal; // Adicionado campo de valor total
    protected String enderecoEntrega; // Adicionado campo de endereço de entrega
    private List<Produto> produtos;
    protected List<ItemPedido> itens;


    public Pedido(){
        this.produtos = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.status = StatusPedido.EM_PREPARO;
    }

    public Pedido(int idPedido, int idCliente, int idRestaurante, Timestamp dataHora, StatusPedido status, List<Produto> produtos, List<ItemPedido> itens) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.dataHora = dataHora;
        this.status = status;
        this.produtos = new ArrayList<>();
        this.itens = itens;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public java.sql.Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(java.util.Date dataHora) {
        this.dataHora = new java.sql.Timestamp(dataHora.getTime());
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        if (produtos == null){
            produtos = new ArrayList<>();   //Isso garante que, mesmo que algum DAO crie um Pedido sem inicializar a lista,a chamada sempre retorna uma lista válida.
        }
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<ItemPedido> getItens() {
        if(this.itens == null){
            this.itens = new ArrayList<>();
        }
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens != null ? itens : new ArrayList<>();
    }

    public void setValorTotal(double total) {
        this.valorTotal = total;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}