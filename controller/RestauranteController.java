package controller;
import dao.RestauranteDAO;
import model.Restaurante;
import java.sql.SQLException;
import java.util.List;

public class RestauranteController {
    private RestauranteDAO restauranteDAO;

    public RestauranteController() {
        this.restauranteDAO = new RestauranteDAO();
    }

    public void cadastrarRestaurante(Restaurante restaurante) {
            restauranteDAO.inserir(restaurante);
            System.out.println(" Restaurante cadastrado com sucesso!");

    }

    public void listarRestaurantes() {
        List<Restaurante> restaurantes = restauranteDAO.listar();
        for (Restaurante r : restaurantes) {
            System.out.println(r);
        }
    }

    public void atualizarRestaurante(Restaurante restaurante) {
        try {
            restauranteDAO.atualizar(restaurante);
            System.out.println(" Restaurante atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar restaurante: " + e.getMessage());
        }
    }

    public void deletarRestaurante(int idRestaurante) {
        try {
            restauranteDAO.deletar(idRestaurante);
            System.out.println(" Restaurante removido com sucesso!");
        } catch (SQLException e) {
            System.out.println(" Erro ao remover restaurante: " + e.getMessage());
        }
    }
}

