package dao;

import model.Produto;
import model.Restaurante;
import util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public int inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, descricao, categoria, idRestaurante) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getDescricao());
            stmt.setString(4, produto.getCategoria());
            stmt.setInt(5, produto.getIdRestaurante());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                produto.setIdProduto(idGerado);
                return idGerado;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
        return -1;
    }



    public List<Produto> listar () {
            List<Produto> lista = new ArrayList<>();
            String sql = "SELECT * FROM produto";

            try (Connection conn = ConnectionFactory.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setIdProduto(rs.getInt("idProduto"));
                    p.setNome(rs.getString("nome"));
                    p.setPreco(rs.getDouble("preco"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setIdRestaurante(rs.getInt("idRestaurante"));
                    lista.add(p);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao listar produtos: " + e.getMessage());
            }
            return lista;
        }

    public List<Produto> listarPorRestaurante(int idRestaurante) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE idRestaurante = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("idProduto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setCategoria(rs.getString("categoria"));
                p.setIdRestaurante(rs.getInt("idRestaurante")); // se tiver esse campo
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos por restaurante: " + e.getMessage());
        }

        return lista;
    }




    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET nome=?, preco=?, descricao=?, categoria=?, idRestaurante=? WHERE idProduto=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getDescricao());
            stmt.setString(4, produto.getCategoria());
            stmt.setInt(5, produto.getIdRestaurante());
            stmt.setInt(6, produto.getIdProduto());
            stmt.executeUpdate();
        }
    }

    public void deletar ( int idProduto) throws SQLException {
            String sql = "DELETE FROM produto WHERE idProduto=?";
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProduto);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }

    public Produto buscarPorId(int idProduto) throws SQLException {
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        Produto produto = null;

        // O try-with-resources garante que conn e stmt sejam fechados automaticamente
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            // O try-with-resources garante que rs seja fechado automaticamente
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdProduto(rs.getInt("idProduto"));
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setIdRestaurante(rs.getInt("idRestaurante"));
                }
            }

        } // Não é mais necessário o bloco finally

        return produto;
    }

}

