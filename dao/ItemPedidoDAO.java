package dao;
import model.ItemPedido;
import util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    public int inserir(ItemPedido item) {
        String sql = "INSERT INTO itemPedido (descricao, quantidade, preco, idPedido) VALUES (?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getDescricao());
            stmt.setInt(2, item.getQuantidade());
            stmt.setDouble(3, item.getPreco());
            stmt.setInt(4, item.getIdPedido());
            stmt.executeUpdate();

            // Recupera o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGerado = rs.getInt(1);
                item.setIdItem(idGerado);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir item do pedido: " + e.getMessage());
        }

        return idGerado;
    }


    public List<ItemPedido> listar(){
            List<ItemPedido> lista = new ArrayList<>();
            String sql = "SELECT * FROM itemPedido";

            try(Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
                while (rs.next()){
                    ItemPedido item = new ItemPedido();
                    item.setIdItem(rs.getInt("idItem"));
                    item.setDescricao(rs.getString("descricao"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setPreco(rs.getDouble("preco"));
                    lista.add(item);
                }
            }catch (SQLException e){
                System.out.println("Erro ao listar itens do pedido: " + e.getMessage());
            }
            return lista;
        }
    public void atualizar(ItemPedido itemPedido) throws SQLException {
        String sql = "UPDATE itemPedido SET descricao=?, quantidade=?, preco=? WHERE idItem=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, itemPedido.getDescricao());
        stmt.setInt(2, itemPedido.getQuantidade());
        stmt.setDouble(3, itemPedido.getPreco());
        stmt.setInt(4,itemPedido.getIdItem());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    public void deletar(int idItem) throws SQLException {
        String sql = "DELETE FROM itemPedido WHERE idItem=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idItem);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public List<ItemPedido> listarPorPedido(int idPedido) {
        List<ItemPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM itemPedido WHERE idPedido=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemPedido item = new ItemPedido();
                item.setIdItem(rs.getInt("idItem"));
                item.setDescricao(rs.getString("descricao"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPreco(rs.getDouble("preco"));
                lista.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar itens do pedido: " + e.getMessage());
        }
        return lista;
    }

}



