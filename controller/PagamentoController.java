package controller;
import model.Pagamento;
import service.PagamentoService;
import java.sql.SQLException;
import java.util.List;

public class PagamentoController {
    private PagamentoService pagamentoService;

    public PagamentoController() {
        this.pagamentoService = new PagamentoService();
    }

    public void registrarPagamento(Pagamento pagamento) {
        try {
            pagamentoService.registrarPagamento(pagamento);
            System.out.println("Pagamento registrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(" Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    public void listarPagamentos() {
        try {
            List<Pagamento> pagamentos = pagamentoService.listarPagamentos();
            for (Pagamento p : pagamentos) {
                System.out.println(p);
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao listar pagamentos: " + e.getMessage());
        }
    }


    public void atualizarStatusPagamento(int idPagamento, String novoStatus) {
        try {
            pagamentoService.atualizarStatusPagamento(idPagamento, novoStatus);
            System.out.println(" Status do pagamento atualizado para: " + novoStatus);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(" Erro ao atualizar status de pagamento: " + e.getMessage());
        }
    }

    public void cancelarPagamento(int idPagamento) {
        try {
            pagamentoService.deletarPagamento(idPagamento);
            System.out.println(" Pagamento cancelado com sucesso!");
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(" Erro ao cancelar pagamento: " + e.getMessage());
        }
    }
}
