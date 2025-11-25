package service;

import dao.PedidoDAO;
import dao.ItemPedidoDAO;
import model.ItemPedido;
import model.Pedido;
import java.sql.SQLException;
import java.util.List;
import Enum.StatusPedido;

public class PedidoService {
    private PedidoDAO pedidoDAO;
    private ItemPedidoDAO itemPedidoDAO;

    public PedidoService(){
        this.pedidoDAO = new PedidoDAO();
        this.itemPedidoDAO = new ItemPedidoDAO();
    }


    public void registrarPedido(Pedido pedido) throws SQLException{
        if(pedido.getIdCliente() == 0 || pedido.getIdRestaurante() == 0){
            throw  new IllegalArgumentException("Cliente e restuarante são obrigatórios!");
        }
        if (pedido.getItens() == null || pedido.getItens().isEmpty()){
            throw new IllegalArgumentException("O pedido deve conter ao menos um item!");
        }
        double total = 0;
        for (ItemPedido item : pedido.getItens()){
            total += item.getPreco() * item.getQuantidade();
        }
        pedido.setValorTotal(total);

        int idPedidoGerado = pedidoDAO.inserir(pedido);

        for (ItemPedido item : pedido.getItens()){
            item.setIdPedido(idPedidoGerado);
            itemPedidoDAO.inserir(item);
        }
    }
    public List<Pedido> listarPedidos() throws SQLException{
        return pedidoDAO.listarTodos();
    }

    public Pedido buscarPedidoPorId(int idPedido) throws SQLException {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("ID do Pedido inválido!");
        }
        return pedidoDAO.buscarPorId(idPedido);
    }
    public List<Pedido> listarPorCliente(int idCliente) throws SQLException{
        return pedidoDAO.listarPorCliente(idCliente);
    }



    public void atualizarStatus(int idPedido, String novoStatus) throws SQLException{
        if (novoStatus == null || novoStatus.isEmpty()){
        throw new IllegalArgumentException("Status inválido!");
        }
        pedidoDAO.atualizarStatus(idPedido, novoStatus);
    }

    public void cancelarPedido(int idPedido) throws SQLException{
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null){
            throw new IllegalArgumentException("Pedido não encontrado!");
        }
        if (pedido.getStatus() == StatusPedido.ENTREGUE){
            throw new IllegalArgumentException("Não é possivel cancelar um pedido já entregue!");
        }
        pedidoDAO.atualizarStatus(idPedido, "Cancelado");
    }
}
