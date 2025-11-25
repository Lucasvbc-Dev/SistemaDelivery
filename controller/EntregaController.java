package controller;
import model.Entrega;
import service.EntregaService;
import java.sql.SQLException;

public class EntregaController {
    private EntregaService entregaService;

    public EntregaController(){
        this.entregaService = new EntregaService();
    }

    public void cadastrarEntrega(Entrega entrega) {
        try {
            entregaService.cadastrarEntrega(entrega);
            System.out.println("Entrega cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar entrega: " + e.getMessage());
        }
    }



    public void atualizarStatusEntrega(int idEntrega, String novoStatus){
        try {
            entregaService.atualizarStatus(idEntrega, novoStatus);
            System.out.println("Entrega atualizado com sucesso!");
        }catch (SQLException e){
            System.out.println("Erro ao atualizar entrega:" + e.getMessage());
        }
    }

    public void deletarEntrega(int idEntrega){
        try {
            entregaService.deletarEntrega(idEntrega);
            System.out.println("Entrega removida com sucesso");
        }catch (SQLException e){
            System.out.println("Erro ao remover entrega:" + e.getMessage());
        }
    }

}

