package controller;
import dao.TipoRestauranteDAO;
import model.TipoRestaurante;
import java.sql.SQLException;
import java.util.List;

public class TipoRestauranteController {
    TipoRestauranteDAO tipoRestauranteDAO;


    public TipoRestauranteController() {
        this.tipoRestauranteDAO = new TipoRestauranteDAO();
    }

    public void cadastrarTipoRestaurante(TipoRestaurante tipoRestaurante) {
        try{
            tipoRestauranteDAO.inserir(tipoRestaurante);
            System.out.println("Tipo de restaurante cadastrado com sucesso!");
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar tipo de restaurante: " + e.getMessage());
        }
    }

    public void listarTipoRestaurantes() {
       try {
           List<TipoRestaurante> tiposRestaurantes = tipoRestauranteDAO.listar();
           for (TipoRestaurante t : tiposRestaurantes) {
               System.out.println(t);
           }
       }catch (SQLException e){
           System.out.println("Erro ao listar tipos de restaurante: " + e.getMessage());
       }
    }

    public void atualizarRestaurante(TipoRestaurante tipoRestaurante) {
        try {
            tipoRestauranteDAO.atualizar(tipoRestaurante);
            System.out.println("Tipo de restaurante atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar tipo de restaurante: " + e.getMessage());
        }
    }

    public void deletarRestaurante(int idTipoRestaurante) {
        try {
            tipoRestauranteDAO.deletar(idTipoRestaurante);
            System.out.println(" Tipo de Restaurante removido com sucesso!");
        } catch (SQLException e) {
            System.out.println(" Erro ao remover tipo de restaurante: " + e.getMessage());
        }
    }
}
