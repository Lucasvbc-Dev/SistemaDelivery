package Main;

import dao.*;
import model.*;
import Enum.*;
import java.sql.SQLException;
import java.util.List;

public class MainLeitura {
    public static void main(String[] args) {
        try {
            System.out.println("=== TESTE DE LEITURA (READ) ===");

            // Cria instâncias dos DAOs
            TipoRestauranteDAO tipoDAO = new TipoRestauranteDAO();
            RestauranteDAO restauranteDAO = new RestauranteDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            ProdutoDAO produtoDAO = new ProdutoDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();
            ItemPedidoDAO itemDAO = new ItemPedidoDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            EntregaDAO entregaDAO = new EntregaDAO();

            // ===============================================================
            // 🔹 1. TipoRestaurante
            System.out.println("\n📂 Tipos de Restaurante:");
            List<TipoRestaurante> tipos = tipoDAO.listar();
            for (TipoRestaurante t : tipos) {
                System.out.println("ID: " + t.getIdTipoRestaurante() + " | Nome: " + t.getNomeTipo() + " | Descrição: " + t.getDescricao());
            }

            // 🔹 2. Restaurante
            System.out.println("\n📂 Restaurantes:");
            List<Restaurante> restaurantes = restauranteDAO.listar();
            for (Restaurante r : restaurantes) {
                System.out.println("ID: " + r.getIdRestaurante() + " | Nome: " + r.getNome() + " | Cozinha: " + r.getTipoCozinha() + " | Tel: " + r.getTelefone());
            }

            // 🔹 3. Cliente
            System.out.println("\n📂 Clientes:");
            List<Cliente> clientes = clienteDAO.listar();
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getIdCliente() + " | Nome: " + c.getNome() + " | Tel: " + c.getTelefone() + " | Endereço: " + c.getEndereco());
            }

            // 🔹 4. Entregador
            System.out.println("\n📂 Entregadores:");
            List<Entregador> entregadores = entregadorDAO.listar();
            for (Entregador e : entregadores) {
                System.out.println("ID: " + e.getIdEntregador() + " | Nome: " + e.getNome() + " | Tel: " + e.getTelefone() + " | Veículo: " + e.getTipoVeiculo());
            }

            // 🔹 5. Produto
            System.out.println("\n📂 Produtos:");
            List<Produto> produtos = produtoDAO.listar();
            for (Produto p : produtos) {
                System.out.println("ID: " + p.getIdProduto() + " | Nome: " + p.getNome() + " | Preço: R$" + p.getPreco() + " | Categoria: " + p.getCategoria());
            }

            // 🔹 6. Pedido
            System.out.println("\n📂 Pedidos:");
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            for (Pedido ped : pedidos) {
                System.out.println("ID: " + ped.getIdPedido() + " | ClienteID: " + ped.getIdCliente() +
                        " | RestauranteID: " + ped.getIdRestaurante() + " | Status: " + ped.getStatus() +
                        " | Data: " + ped.getDataHora());
            }

            // 🔹 7. Itens do Pedido
            System.out.println("\n📂 Itens do Pedido:");
            List<ItemPedido> itens = itemDAO.listar();
            for (ItemPedido i : itens) {
                System.out.println("ID: " + i.getIdItem() + " | PedidoID: " + i.getIdPedido() +
                        " | Descrição: " + i.getDescricao() + " | Qtd: " + i.getQuantidade() + " | Preço: R$" + i.getPreco());
            }

            // 🔹 8. Pagamento
            System.out.println("\n📂 Pagamentos:");
            List<Pagamento> pagamentos = pagamentoDAO.listar();
            for (Pagamento pag : pagamentos) {
                System.out.println("ID: " + pag.getIdPagamento() + " | PedidoID: " + pag.getIdPedido() +
                        " | Valor: R$" + pag.getValorTotal() + " | Método: " + pag.getMetodoPagamento() +
                        " | Status: " + pag.getStatusPagamento() + " | Data: " + pag.getDataPagamento());
            }

            // 🔹 9. Entrega
            System.out.println("\n📂 Entregas:");
            List<Entrega> entregas = entregaDAO.listar();
            for (Entrega ent : entregas) {
                System.out.println("ID: " + ent.getIdEntrega() + " | PedidoID: " + ent.getIdPedido() +
                        " | EntregadorID: " + ent.getIdEntregador() + " | Status: " + ent.getStatusEntrega() +
                        " | Observação: " + ent.getObservacao());
            }

            System.out.println("\n✅ LEITURA (READ) REALIZADA COM SUCESSO!");

        } catch (SQLException e) {
            System.err.println("Erro ao realizar leitura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
