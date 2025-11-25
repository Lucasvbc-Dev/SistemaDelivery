package service;
import dao.ItemPedidoDAO;
import model.ItemPedido;
import java.sql.SQLException;
import java.util.List;

public class ItemPedidoService {
    private ItemPedidoDAO  itemPedidoDAO;


    public ItemPedidoService(){
        this.itemPedidoDAO = new ItemPedidoDAO();
    }

    public void cadastrarItemPedido(ItemPedido itemPedido) throws SQLException{
        if(itemPedido.getIdPedido() <= 0){
            throw new IllegalArgumentException("Pedido é obrigatório");
        }
        if (itemPedido.getDescricao() == null || itemPedido.getDescricao().isEmpty()){
            throw new IllegalArgumentException("Descrição é obrigatório");
        }
        if (itemPedido.getQuantidade() <= 0){
            throw new IllegalArgumentException("Quantidade é obrigatório");
        }
        if (itemPedido.getPreco() <= 0){
            throw new IllegalArgumentException("Preço é obrigatório");
        }


        itemPedidoDAO.inserir(itemPedido);
    }

    public List<ItemPedido> listarItemPedido() throws SQLException{
        return itemPedidoDAO.listar();
    }

    public void atualizarItemPedido(ItemPedido itemPedido) throws SQLException{
        if(itemPedido.getIdItem() <= 0){
            throw new IllegalArgumentException("ID do item pedido inválido");
        }
        itemPedidoDAO.atualizar(itemPedido);
    }

    public void deletarItemPedido(int idItem) throws SQLException{
        if(idItem <= 0){
            throw new IllegalArgumentException("ID do item inválido");
        }
        itemPedidoDAO.deletar(idItem);
    }
}
