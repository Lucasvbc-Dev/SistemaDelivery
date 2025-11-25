package Main;

import dao.*;
import java.sql.SQLException;

public class MainDelete {
    public static void main(String[] args) {

        try {
            System.out.println("=== TESTE DE DELETE (EXCLUSÃO) ===");

            // ⚠️ IDs que você deseja deletar (ajuste se necessário)
            int idEntrega = 1;
            int idPagamento = 1;
            int idItem = 1;
            int idPedido = 1;
            int idProduto = 1;
            int idEntregador = 1;
            int idCliente = 1;
            int idRestaurante = 1;
            int idTipo = 1;

            // Criação dos DAOs
            EntregaDAO entregaDAO = new EntregaDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            ItemPedidoDAO itemDAO = new ItemPedidoDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();
            ProdutoDAO produtoDAO = new ProdutoDAO();
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            RestauranteDAO restauranteDAO = new RestauranteDAO();
            TipoRestauranteDAO tipoDAO = new TipoRestauranteDAO();

            // =======================================================
            // ⚙️ Ordem correta de exclusão (de dependente → independente)
            // =======================================================

            entregaDAO.deletar(idEntrega);
            System.out.println("✅ Entrega deletada com sucesso!");

            pagamentoDAO.deletar(idPagamento);
            System.out.println("✅ Pagamento deletado com sucesso!");

            itemDAO.deletar(idItem);
            System.out.println("✅ ItemPedido deletado com sucesso!");

            pedidoDAO.deletar(idPedido);
            System.out.println("✅ Pedido deletado com sucesso!");

            produtoDAO.deletar(idProduto);
            System.out.println("✅ Produto deletado com sucesso!");

            entregadorDAO.deletar(idEntregador);
            System.out.println("✅ Entregador deletado com sucesso!");

            clienteDAO.deletar(idCliente);
            System.out.println("✅ Cliente deletado com sucesso!");

            restauranteDAO.deletar(idRestaurante);
            System.out.println("✅ Restaurante deletado com sucesso!");

            tipoDAO.deletar(idTipo);
            System.out.println("✅ TipoRestaurante deletado com sucesso!");

            System.out.println("\n🎉 DELETE executado com sucesso! Todos os dados foram removidos.");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
