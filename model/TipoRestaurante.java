package model;

public class TipoRestaurante {
    protected int idTipoRestaurante;
    protected String nomeTipo;
    protected String descricao;


    public TipoRestaurante(){}

    public TipoRestaurante(int idTipoRestaurante, String nomeTipo, String descricao) {
        this.idTipoRestaurante = idTipoRestaurante;
        this.nomeTipo = nomeTipo;
        this.descricao = descricao;
    }

    public int getIdTipoRestaurante() {
        return idTipoRestaurante;
    }

    public void setIdTipoRestaurante(int idTipoRestaurante) {
        this.idTipoRestaurante = idTipoRestaurante;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
