package controller;
import dao.EntregadorDAO;
import model.Entregador;
import java.sql.SQLException;

public class EntregadorController {
    private EntregadorDAO entregadorDAO;

    public EntregadorController(){
        this.entregadorDAO = new EntregadorDAO();
    }

    public void cadastrarEntregador(Entregador entregador){
        try {
            entregadorDAO.inserir(entregador);
            System.out.println("Entregador cadastrado com sucesso!");
        }catch (SQLException e ){
            System.out.println("Erro ao cadastrar entregador:" +e.getMessage());
        }
    }


    public void atualizarEntregador(Entregador entregador){
        try {
            entregadorDAO.atualizar(entregador);
            System.out.println("Entregador atualizado com sucesso!");
        }catch (SQLException e){
            System.out.println("Erro ao atualizar entregador:" + e.getMessage());
        }
    }



    public void deletarEntregador(int idEntregador){
        try {
            entregadorDAO.deletar(idEntregador);
            System.out.println("Cliente removido com sucesso");
        }catch (SQLException e){
            System.out.println("Erro ao remover cliente:" + e.getMessage());
        }
    }

}

