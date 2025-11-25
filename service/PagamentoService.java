package service;

import dao.PagamentoDAO;
import model.Pagamento;


import java.sql.SQLException;
import java.util.List;

public class PagamentoService {
    private PagamentoDAO pagamentoDAO;

    public PagamentoService(){
        this.pagamentoDAO = new PagamentoDAO();
    }

    public void registrarPagamento(Pagamento pagamento) throws SQLException{
        if(pagamento.getIdPedido() <= 0){
            throw new IllegalArgumentException("Pedido inválido");
        }
        if (pagamento.getValorTotal() <= 0){
            throw new IllegalArgumentException("Valor total deve ser maior que zero");
        }
        if (pagamento.getMetodoPagamento() == null){
            throw new IllegalArgumentException("Método de pagamento é obrigatório");
        }
        pagamentoDAO.inserir(pagamento);
    }

    public List<Pagamento> listarPagamentos() throws SQLException{
        return pagamentoDAO.listar();
    }

    public void atualizarStatusPagamento(int idPagamento, String novoStatus) throws SQLException {
        if (idPagamento <= 0) {
            throw new IllegalArgumentException("ID da pagamento inválido!");
        }
        if (novoStatus == null || novoStatus.isEmpty()) {
            throw new IllegalArgumentException("Status inválido!");
        }
        pagamentoDAO.atualizarStatusPagamento(idPagamento, novoStatus);
    }

    public void deletarPagamento(int idPagamento) throws SQLException{
        if(idPagamento <= 0){
            throw new IllegalArgumentException("ID do pagamento inválido");
        }
        pagamentoDAO.deletar(idPagamento);
    }
}
