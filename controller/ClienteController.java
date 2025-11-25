package controller;


import model.Cliente;
import service.ClienteService;

import java.sql.SQLException;

public class ClienteController {
   private ClienteService clienteService;

   public ClienteController(){
       this.clienteService = new ClienteService();
   }

   public void cadastrarCliente(Cliente cliente){
       try {
           clienteService.cadastrarCliente(cliente);
           System.out.println("Cliente cadastrado com sucesso!");
       }catch (SQLException e ){
           System.out.println("Erro ao cadastrar cliente:" +e.getMessage());
       }
   }

    public void listarClientes() {
        try {
            var clientes = clienteService.listarClientes();
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }


   public void atualizarCliente(Cliente cliente){
       try {
           clienteService.atualizarCliente(cliente);
           System.out.println("Cliente atualizado com sucesso!");
       }catch (SQLException e){
           System.out.println("Erro ao atualizar cliente:" + e.getMessage());
       }
   }



   public void deletarCliente(int idCliente){
       try {
           clienteService.deletarCliente(idCliente);
           System.out.println("Cliente removido com sucesso");
       }catch (SQLException e){
           System.out.println("Erro ao remover cliente:" + e.getMessage());
       }
   }

}
