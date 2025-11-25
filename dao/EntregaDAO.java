package dao;
import model.Entrega;
import util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Enum.StatusEntrega;

public class EntregaDAO {

    public int inserir(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO entrega (idPedido, idEntregador, status_entrega, data_saida, data_entrega, tempo_estimado, observacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdEntregador());
            stmt.setString(3, entrega.getStatusEntrega().name());
            stmt.setTimestamp(4, entrega.getDataSaida() != null ? new Timestamp(entrega.getDataSaida().getTime()) : null);
            stmt.setTimestamp(5, entrega.getDataEntrega() != null ? new Timestamp(entrega.getDataEntrega().getTime()) : null);
            stmt.setString(6, entrega.getTempoEstimado());
            stmt.setString(7, entrega.getObservacao());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                entrega.setIdEntrega(idGerado);
                return idGerado;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir entrega: " + e.getMessage());
            throw e; // Lançar a exceção para ser tratada no service
        }
        return -1;
    }

    public List<Entrega> listar() throws SQLException {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrega";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entrega e = new Entrega();
                e.setIdEntrega(rs.getInt("idEntrega"));
                e.setIdPedido(rs.getInt("idPedido"));
                e.setIdEntregador(rs.getInt("idEntregador"));
                e.setStatusEntrega(StatusEntrega.valueOf(rs.getString("status_entrega").toUpperCase()));
                e.setDataSaida(rs.getTimestamp("data_saida"));
                e.setDataEntrega(rs.getTimestamp("data_entrega"));
                e.setTempoEstimado(rs.getString("tempo_estimado"));
                e.setObservacao(rs.getString("observacao"));
                lista.add(e);
            }
        }
        return lista;
    }
    public void atualizar(Entrega entrega) throws SQLException {
        String sql = "UPDATE entrega SET idPedido=?, idEntregador=?, status_entrega=?, data_saida=?, data_entrega=?, tempo_estimado=?, observacao=? WHERE idEntrega=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, entrega.getIdPedido());
        stmt.setInt(2, entrega.getIdEntregador());
        stmt.setString(3, entrega.getStatusEntrega().name());
        stmt.setTimestamp(4, entrega.getDataSaida() != null ? new Timestamp(entrega.getDataSaida().getTime()) : null);
        stmt.setTimestamp(5, entrega.getDataEntrega() != null ? new Timestamp(entrega.getDataEntrega().getTime()) : null);
        stmt.setString(6, entrega.getTempoEstimado());
        stmt.setString(7, entrega.getObservacao());
        stmt.setInt(8, entrega.getIdEntrega());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    
    public void deletar(int idEntrega) throws SQLException {
        String sql = "DELETE FROM entrega WHERE idEntrega=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idEntrega);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }


    public void atualizarStatus(int idEntrega, String novoStatus) throws SQLException {
        String sql = "UPDATE entrega SET status_entrega = ? WHERE idEntrega = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idEntrega);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha ao atualizar status: nenhuma entrega encontrada com o ID informado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da entrega: " + e.getMessage());
            throw e;
        }
    }

    public Entrega buscarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT * FROM entrega WHERE idPedido = ?";
        Entrega e = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                e = new Entrega();
                e.setIdEntrega(rs.getInt("idEntrega"));
                e.setIdPedido(rs.getInt("idPedido"));
                e.setIdEntregador(rs.getInt("idEntregador"));
                e.setStatusEntrega(StatusEntrega.valueOf(rs.getString("status_entrega").toUpperCase()));
                e.setDataSaida(rs.getTimestamp("data_saida"));
                e.setDataEntrega(rs.getTimestamp("data_entrega"));
                e.setTempoEstimado(rs.getString("tempo_estimado"));
                e.setObservacao(rs.getString("observacao"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar entrega por pedido: " + ex.getMessage());
            throw ex;
        }
        return e;
    }

    public List<Entrega> listarPorEntregador(int idEntregador) throws SQLException {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrega WHERE idEntregador = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEntregador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Entrega e = new Entrega();
                e.setIdEntrega(rs.getInt("idEntrega"));
                e.setIdPedido(rs.getInt("idPedido"));
                e.setIdEntregador(rs.getInt("idEntregador"));
                e.setStatusEntrega(StatusEntrega.valueOf(rs.getString("status_entrega").toUpperCase()));
                e.setDataSaida(rs.getTimestamp("data_saida"));
                e.setDataEntrega(rs.getTimestamp("data_entrega"));
                e.setTempoEstimado(rs.getString("tempo_estimado"));
                e.setObservacao(rs.getString("observacao"));
                lista.add(e);
            }
        }
        return lista;
    }

}
