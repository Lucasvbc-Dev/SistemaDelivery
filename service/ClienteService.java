package service;

import dao.ClienteDAO;
import model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    private ClienteDAO  clienteDAO;


    public ClienteService(){
        this.clienteDAO = new ClienteDAO();
    }

    public void cadastrarCliente(Cliente cliente) throws SQLException{
        if(cliente.getNome() == null || cliente.getNome().isEmpty()){
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().isEmpty()){
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        if (cliente.getEndereco() == null || cliente.getEndereco().isEmpty()){
            throw new IllegalArgumentException("Endereço do cliente é obrigatório");
        }
        clienteDAO.inserir(cliente);
    }

    public List<Cliente> listarClientes() throws SQLException{
        return clienteDAO.listar();
    }

    public void atualizarCliente(Cliente cliente) throws SQLException{
        if(cliente.getIdCliente() <= 0){
            throw new IllegalArgumentException(".ID do cliente inválido");
        }
        clienteDAO.atualizar(cliente);
    }

    public void deletarCliente(int idCliente) throws SQLException{
        if(idCliente <= 0){
            throw new IllegalArgumentException("ID do cliente inválido");
        }
        clienteDAO.deletar(idCliente);
    }
}
