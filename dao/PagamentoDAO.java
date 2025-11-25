package dao;

import model.Pagamento;
import util.ConnectionFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Enum.StatusPagamento;
import Enum.MetodoPagamento;

public class PagamentoDAO {

    public int inserir(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO Pagamento (idPedido, valor_total, metodo_pagamento, status_pagamento, data_pagamento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pagamento.getIdPedido());
            stmt.setDouble(2, pagamento.getValorTotal());
            stmt.setString(3, pagamento.getMetodoPagamento().name());
            stmt.setString(4, pagamento.getStatusPagamento().name());
            stmt.setDate(5, java.sql.Date.valueOf(pagamento.getDataPagamento()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                pagamento.setIdPagamento(idGerado);
                return idGerado;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pagamento: " + e.getMessage());
        }
        return -1;
    }


    public List<Pagamento> listar() throws SQLException {
        List<Pagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pagamento";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pagamento p = new Pagamento();
                p.setIdPagamento(rs.getInt("idPagamento"));
                p.setIdPedido(rs.getInt("idPedido"));
                p.setValorTotal(rs.getDouble("valor_total"));
                p.setMetodoPagamento(MetodoPagamento.valueOf(rs.getString("metodo_pagamento").toUpperCase()));
                p.setStatusPagamento(StatusPagamento.valueOf(rs.getString("status_pagamento").toUpperCase()));
                p.setDataPagamento(rs.getDate("data_pagamento").toLocalDate());
                lista.add(p);
            }
        }
        return lista;
    }

    public void atualizar(Pagamento pagamento) throws SQLException {
        String sql = "UPDATE Pagamento SET idPedido=?, valor_total=?, metodo_pagamento=?, status_pagamento=?, data_pagamento=? WHERE idPagamento=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pagamento.getIdPedido());
            stmt.setDouble(2, pagamento.getValorTotal());
            stmt.setString(3, pagamento.getMetodoPagamento().name());
            stmt.setString(4, pagamento.getStatusPagamento().name());
            stmt.setDate(5, java.sql.Date.valueOf(pagamento.getDataPagamento()));
            stmt.setInt(6, pagamento.getIdPagamento());

            stmt.executeUpdate();
        }
    }

    public void atualizarStatusPagamento(int idPagamento, String novoStatusPagamento) throws SQLException {
        String sql = "UPDATE Pagamento SET status_pagamento=? WHERE idPagamento=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatusPagamento);
            stmt.setInt(2, idPagamento);
            stmt.executeUpdate();
        }
    }

    public void deletar(int idPagamento) throws SQLException {
        String sql = "DELETE FROM Pagamento WHERE idPagamento=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPagamento);
            stmt.executeUpdate();
        }
    }
    public List<Pagamento> listarPorCliente(int idCliente) {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamento WHERE id_cliente = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setIdPagamento(rs.getInt("id"));
                pagamento.setIdPedido(rs.getInt("id_pedido"));
                pagamento.setIdCLiente(rs.getInt("id_cliente"));
                pagamento.setValorTotal(rs.getDouble("valor"));
                pagamento.setStatusPagamento(StatusPagamento.valueOf(rs.getString("status_pagamento")));
                Timestamp ts = rs.getTimestamp("data_pagamento");
                if (ts != null) {
                    pagamento.setDataPagamento(ts.toLocalDateTime().toLocalDate());
                }

                pagamentos.add(pagamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }

}
