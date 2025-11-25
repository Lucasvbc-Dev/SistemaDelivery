package dao;

import model.Cliente;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public int inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, telefone, endereco) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                cliente.setIdCliente(idGerado);
                return idGerado;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
        return -1;
    }


    public List<Cliente> listar(){
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));
                lista.add(c);
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome=?, telefone=?, endereco=? WHERE idCliente=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getTelefone());
        stmt.setString(3, cliente.getEndereco());
        stmt.setInt(4, cliente.getIdCliente());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    public void deletar(int idCliente) throws SQLException {
        String sql = "DELETE FROM clientes WHERE idCliente=?";
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idCliente);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
