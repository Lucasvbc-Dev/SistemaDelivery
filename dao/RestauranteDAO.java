package dao;
import model.Restaurante;
import util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {


    public int inserir(Restaurante restaurante){
        String sql = "INSERT INTO restaurante (nome, tipo_cozinha, telefone) VALUES (?, ?, ?)";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, restaurante.getNome());
            stmt.setString(2, restaurante.getTipoCozinha());
            stmt.setString(3, restaurante.getTelefone());
            stmt.executeUpdate();


            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                int idGerado = rs.getInt(1);
                restaurante.setIdRestaurante(idGerado);
                return idGerado; // Retorna o ID
            }
        }catch (SQLException e){
            System.out.println("Erro ao inserir restaurante: " + e.getMessage());
        }
        return -1; // Retorna -1 em caso de erro
    }

    public List<Restaurante> listar(){
        List<Restaurante> lista = new ArrayList<>();
        String sql = "SELECT * FROM restaurante";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                Restaurante r = new Restaurante();
                r.setIdRestaurante(rs.getInt("idRestaurante"));
                r.setNome(rs.getString("nome"));
                r.setTipoCozinha(rs.getString("tipo_cozinha"));
                r.setTelefone(rs.getString("telefone"));
                lista.add(r);
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar restaurantes: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Restaurante restaurante) throws SQLException {
        String sql = "UPDATE restaurante SET nome=?, tipo_cozinha=?, telefone=? WHERE idRestaurante=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, restaurante.getNome());
        stmt.setString(2, restaurante.getTipoCozinha());
        stmt.setString(3, restaurante.getTelefone());
        stmt.setInt(4, restaurante.getIdRestaurante());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public void deletar(int idRestaurante) throws SQLException {
        String sql = "DELETE FROM restaurante WHERE idRestaurante=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idRestaurante);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public Restaurante buscarPorId(int idRestaurante) throws SQLException {
        String sql = "SELECT * FROM restaurante WHERE idRestaurante = ?";
        Restaurante restaurante = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRestaurante);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    restaurante = new Restaurante();
                    restaurante.setIdRestaurante(rs.getInt("idRestaurante"));
                    restaurante.setNome(rs.getString("nome"));
                    // Usando o nome da coluna do banco: tipo_cozinha
                    restaurante.setTipoCozinha(rs.getString("tipo_cozinha"));
                    restaurante.setTelefone(rs.getString("telefone"));
                }
            }
        }
        return restaurante;
    }
}