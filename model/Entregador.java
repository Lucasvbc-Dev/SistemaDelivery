package model;

public class Entregador {
    private int idEntregador;
    private String nome;
    private String telefone; // Adicionado
    private String tipoVeiculo; // Adicionado
    private int idRestaurante;

    public Entregador() {}

    // Construtor completo
    public Entregador(int idEntregador, String nome, String telefone, String tipoVeiculo, int idRestaurante) {
        this.idEntregador = idEntregador;
        this.nome = nome;
        this.telefone = telefone;
        this.tipoVeiculo = tipoVeiculo;
        this.idRestaurante = idRestaurante;
    }

    // Getters e Setters
    public int getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(int idEntregador) {
        this.idEntregador = idEntregador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    // Novos Getters e Setters
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
}
