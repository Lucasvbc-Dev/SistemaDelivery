package dao;


import model.TipoRestaurante;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoRestauranteDAO {

    public int inserir(TipoRestaurante tipoRestaurante) throws SQLException {
        String sql = "INSERT INTO tiporestaurante (nome_tipo, descricao) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tipoRestaurante.getNomeTipo());
            stmt.setString(2, tipoRestaurante.getDescricao());
            stmt.executeUpdate();

            // Recupera o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                tipoRestaurante.setIdTipoRestaurante(idGerado);
                return idGerado;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o tipo do restaurante: " + e.getMessage());
        }

        return -1; // Retorna -1 se falhar
    }


    public List<TipoRestaurante> listar() throws SQLException{
        List<TipoRestaurante> lista = new ArrayList<>();
        String sql = "SELECT * FROM tiporestaurante";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                TipoRestaurante t = new TipoRestaurante();
                t.setIdTipoRestaurante(rs.getInt("idTipoRestaurante"));
                t.setNomeTipo(rs.getString("nome_tipo"));
                t.setDescricao(rs.getString("descricao"));
                lista.add(t);
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar os tipos de restaurantes: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(TipoRestaurante tipoRestaurante) throws SQLException {
        String sql = "UPDATE tiporestaurante SET nome_tipo=?, descricao=? WHERE idTipoRestaurante=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, tipoRestaurante.getNomeTipo());
        stmt.setString(2, tipoRestaurante.getDescricao());
        stmt.setInt(3, tipoRestaurante.getIdTipoRestaurante());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    public void deletar(int idTipoRestaurante) throws SQLException {
        String sql = "DELETE FROM tiporestaurante WHERE idTipoRestaurante=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idTipoRestaurante);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}


