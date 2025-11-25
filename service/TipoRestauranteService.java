package service;

import dao.TipoRestauranteDAO;
import model.TipoRestaurante;

import java.sql.SQLException;
import java.util.List;

public class TipoRestauranteService {
    private TipoRestauranteDAO  tipoRestauranteDAO;


    public TipoRestauranteService(){
        this.tipoRestauranteDAO = new TipoRestauranteDAO();
    }

    public void cadastrarTipoRestaurante(TipoRestaurante tipoRestaurante) throws SQLException{
        if(tipoRestaurante.getNomeTipo() == null || tipoRestaurante.getNomeTipo().isEmpty()){
            throw new IllegalArgumentException("Nome do tipo do restaurante é obrigatório");
        }
        if (tipoRestaurante.getDescricao() == null || tipoRestaurante.getDescricao().isEmpty()){
            throw new IllegalArgumentException("Descrição é obrigatório");
        }

        tipoRestauranteDAO.inserir(tipoRestaurante);
    }

    public List<TipoRestaurante> listarTipoRestaurantes() throws SQLException{
        return tipoRestauranteDAO.listar();
    }

    public void atualizarTipoRestaurante(TipoRestaurante tipoRestaurante) throws SQLException{
        if(tipoRestaurante.getIdTipoRestaurante() <= 0){
            throw new IllegalArgumentException("ID do tipo do restaurante inválido");
        }
        tipoRestauranteDAO.atualizar(tipoRestaurante);
    }

    public void deletarTipoRestaurante(int idTipoRestaurante) throws SQLException{
        if(idTipoRestaurante <= 0){
            throw new IllegalArgumentException("ID do tipo do restaurante inválido");
        }
        tipoRestauranteDAO.deletar(idTipoRestaurante);
    }
}
