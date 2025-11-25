package service;

import dao.RestauranteDAO;
import model.Restaurante;
import java.sql.SQLException;
import java.util.List;

public class RestauranteService {
    private RestauranteDAO restauranteDAO = new RestauranteDAO();

    public void cadastrarRestaurante(Restaurante restaurante) throws IllegalArgumentException, SQLException {
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório.");
        }
        if (restaurante.getTipoCozinha() == null || restaurante.getTipoCozinha().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de cozinha é obrigatório.");
        }
        if (restaurante.getTelefone() == null || restaurante.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do restaurante é obrigatório.");
        }
        restauranteDAO.inserir(restaurante);
    }

    public void atualizarRestaurante(Restaurante restaurante) throws IllegalArgumentException, SQLException {
        if (restaurante.getIdRestaurante() <= 0) {
            throw new IllegalArgumentException("ID do restaurante inválido.");
        }
        // ... (Mesmas validações de campos)
        restauranteDAO.atualizar(restaurante);
    }

    public void deletarRestaurante(int idRestaurante) throws IllegalArgumentException, SQLException {
        if (idRestaurante <= 0) {
            throw new IllegalArgumentException("ID do restaurante inválido.");
        }
        restauranteDAO.deletar(idRestaurante);
    }

    public Restaurante buscarRestaurantePorId(int idRestaurante) throws SQLException {
        return restauranteDAO.buscarPorId(idRestaurante);
    }

    public List<Restaurante> listarRestaurantes() throws SQLException {
        return restauranteDAO.listar();
    }
}
