package dao;

import model.Entregador;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EntregadorDAO {

    public void inserir(Entregador entregador) throws SQLException {
        String sql = "INSERT INTO entregador (nome, telefone, tipo_veiculo, idRestaurante) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getTelefone()); // NOVO CAMPO
            stmt.setString(3, entregador.getTipoVeiculo()); // NOVO CAMPO
            stmt.setInt(4, entregador.getIdRestaurante());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entregador.setIdEntregador(rs.getInt(1));
            }
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void atualizar(Entregador entregador) throws SQLException {
        String sql = "UPDATE entregador SET nome = ?, telefone = ?, tipo_veiculo = ?, idRestaurante = ? WHERE idEntregador = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getTelefone()); // NOVO CAMPO
            stmt.setString(3, entregador.getTipoVeiculo()); // NOVO CAMPO
            stmt.setInt(4, entregador.getIdRestaurante());
            stmt.setInt(5, entregador.getIdEntregador());
            stmt.executeUpdate();
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public void deletar(int idEntregador) throws SQLException {
        String sql = "DELETE FROM entregador WHERE idEntregador = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEntregador);
            stmt.executeUpdate();
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
    }

    public Entregador buscarPorId(int idEntregador) throws SQLException {
        String sql = "SELECT * FROM entregador WHERE idEntregador = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Entregador entregador = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEntregador);
            rs = stmt.executeQuery();

            if (rs.next()) {
                entregador = new Entregador();
                entregador.setIdEntregador(rs.getInt("idEntregador"));
                entregador.setNome(rs.getString("nome"));
                entregador.setTelefone(rs.getString("telefone"));
                entregador.setTipoVeiculo(rs.getString("tipo_veiculo"));
                entregador.setIdRestaurante(rs.getInt("idRestaurante"));
            }
        } finally {
            // Fechamento seguro dos recursos na ordem inversa de abertura (rs, stmt, conn)
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }
            // A ConnectionFactory já lida com o fechamento da conexão, mas é bom garantir
            ConnectionFactory.closeConnection(conn);
        }
        return entregador;
    }

    public List<Entregador> listar() throws SQLException {
        String sql = "SELECT * FROM entregador";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entregador> entregadores = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Entregador entregador = new Entregador();
                entregador.setIdEntregador(rs.getInt("idEntregador"));
                entregador.setNome(rs.getString("nome"));
                entregador.setTelefone(rs.getString("telefone")); // NOVO CAMPO
                entregador.setTipoVeiculo(rs.getString("tipo_veiculo")); // NOVO CAMPO
                entregador.setIdRestaurante(rs.getInt("idRestaurante"));
                entregadores.add(entregador);
            }
        } finally {
            ConnectionFactory.closeConnection(conn);
        }
        return entregadores;
    }
}
