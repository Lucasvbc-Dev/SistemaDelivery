package dao;
import model.Pedido;
import util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Enum.StatusPedido;

public class PedidoDAO {

    public int inserir(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (idCliente, idRestaurante, data_hora, status, valorTotal, enderecoEntrega) VALUES (?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdRestaurante());
            stmt.setTimestamp(3, new java.sql.Timestamp(pedido.getDataHora().getTime()));
            stmt.setString(4, pedido.getStatus().name());
            stmt.setDouble(5, pedido.getValorTotal());
            stmt.setString(6, pedido.getEnderecoEntrega());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGerado = rs.getInt(1);
                pedido.setIdPedido(idGerado);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
            throw e;
        }
        return idGerado;
    }


    public List<Pedido> listar() throws SQLException{
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setIdCliente(rs.getInt("idCliente"));
                p.setIdRestaurante(rs.getInt("idRestaurante"));
                p.setStatus(StatusPedido.valueOf(rs.getString("status").toUpperCase()));
                p.setDataHora(rs.getTimestamp("data_hora"));
                p.setValorTotal(rs.getDouble("valorTotal"));
                p.setEnderecoEntrega(rs.getString("enderecoEntrega"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET idCliente=?, idRestaurante=?, data_hora=?, status=?, valorTotal=?, enderecoEntrega=? WHERE idPedido=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdRestaurante());
            stmt.setTimestamp(3, new Timestamp(pedido.getDataHora().getTime()));
            stmt.setString(4, pedido.getStatus().name());
            stmt.setDouble(5, pedido.getValorTotal());
            stmt.setString(6, pedido.getEnderecoEntrega());
            stmt.setInt(7,pedido.getIdPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
            throw e;
        }
    }

    public void deletar(int idPedido) throws SQLException {
        String sql = "DELETE FROM pedido WHERE idPedido=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPedido);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }


    public List<Pedido> listarTodos() throws SQLException {
        return listar();
    }

    public List<Pedido> listarPorCliente(int idCliente) throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE idCliente=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setIdCliente(rs.getInt("idCliente"));
                p.setIdRestaurante(rs.getInt("idRestaurante"));
                p.setStatus(StatusPedido.valueOf(rs.getString("status").toUpperCase()));
                p.setDataHora(rs.getTimestamp("data_hora"));
                p.setValorTotal(rs.getDouble("valorTotal"));
                p.setEnderecoEntrega(rs.getString("enderecoEntrega"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos do cliente: " + e.getMessage());
            throw e;
        }
        return lista;
    }


    public void atualizarStatus(int idPedido, String novoStatus) {
        String sql = "UPDATE pedido SET status=? WHERE idPedido=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status: " + e.getMessage());
        }
    }


    public Pedido buscarPorId(int idPedido) throws SQLException {
            String sql = "SELECT * FROM pedido WHERE idPedido=?";
            Pedido p = null;

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idPedido);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    p = new Pedido();
                    p.setIdPedido(rs.getInt("idPedido"));
                    p.setIdCliente(rs.getInt("idCliente"));
                    p.setIdRestaurante(rs.getInt("idRestaurante"));
                    p.setStatus(StatusPedido.valueOf(rs.getString("status").toUpperCase()));
                    p.setDataHora(rs.getTimestamp("data_hora"));
                    p.setValorTotal(rs.getDouble("valorTotal"));
                    p.setEnderecoEntrega(rs.getString("enderecoEntrega"));
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar pedido: " + e.getMessage());
                throw e;
            }
            return p;
        }



}



