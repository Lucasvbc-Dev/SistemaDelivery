package model;

public class ItemPedido {
    protected int idItem;
    protected int idPedido;
    protected int idProduto;
    protected String descricao;
    protected int quantidade;
    protected double preco;
    protected String nomeProduto;
    protected int idRestaurante; // Adicionado para rastrear o restaurante do item no carrinho

    public ItemPedido(){}


    public ItemPedido(int idItem, int idPedido,int idProduto, String descricao, int quantidade, double preco, String nomeProduto, int idRestaurante) {
        this.idItem = idItem;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
        this.nomeProduto = nomeProduto;
        this.idRestaurante = idRestaurante;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }
}
