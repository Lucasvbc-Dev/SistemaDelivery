package model;

public class Restaurante {
    protected int idRestaurante;
    protected String nome;
    protected String tipoCozinha;
    protected TipoRestaurante tipoRestaurante;
    protected String telefone;


    public Restaurante(){}

    public Restaurante(int idRestaurante, String nome, String tipoCozinha, TipoRestaurante tipoRestaurante, String telefone) {
        this.idRestaurante = idRestaurante;
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.tipoRestaurante = tipoRestaurante;
        this.telefone = telefone;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public TipoRestaurante getTipoRestaurante() {
        return tipoRestaurante;
    }

    public void setTipoRestaurante(TipoRestaurante tipoRestaurante) {
        this.tipoRestaurante = tipoRestaurante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
