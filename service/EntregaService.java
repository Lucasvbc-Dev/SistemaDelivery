package service;

import dao.EntregaDAO;
import model.Entrega;
import java.sql.SQLException;
import java.util.List;

public class EntregaService {
    private EntregaDAO  entregaDAO;

    public EntregaService(){
        this.entregaDAO = new EntregaDAO();
    }

    public void cadastrarEntrega(Entrega entrega) throws SQLException{
        if(entrega.getIdPedido() <= 0){
            throw new IllegalArgumentException("Pedido inválido");
        }
        if (entrega.getIdEntregador() <= 0){
            throw new IllegalArgumentException("Entregador inválido");
        }
        entregaDAO.inserir(entrega);
    }

    public List<Entrega> listarEntregas() throws SQLException{
        return entregaDAO.listar();
    }

    public List<Entrega> listarEntregasPorEntregador(int idEntregador) throws SQLException {
        if (idEntregador <= 0) {
            throw new IllegalArgumentException("ID do Entregador inválido!");
        }
        return entregaDAO.listarPorEntregador(idEntregador);
    }

    public Entrega buscarEntregaPorPedido(int idPedido) throws SQLException {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("ID do Pedido inválido!");
        }
        return entregaDAO.buscarPorPedido(idPedido);
    }

    public void atualizarStatus(int idEntrega, String novoStatus) throws SQLException {
        if (idEntrega <= 0) {
            throw new IllegalArgumentException("ID da entrega inválido!");
        }
        if (novoStatus == null || novoStatus.isEmpty()) {
            throw new IllegalArgumentException("Status inválido!");
        }
        entregaDAO.atualizarStatus(idEntrega, novoStatus);
    }

    public void deletarEntrega(int idEntrega) throws SQLException{
        if(idEntrega <= 0){
            throw new IllegalArgumentException("ID da entrega inválido");
        }
        entregaDAO.deletar(idEntrega);
    }
}
