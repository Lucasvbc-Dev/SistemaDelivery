package Main;

import dao.*;
import model.*;
import Enum.*;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;

public class MainInsercao {
    public static void main(String[] args) {

        try {
            System.out.println("=== TESTE DE INSERÇÃO NO BANCO ===");

            // 🔹 1. TipoRestaurante
            TipoRestaurante tipo = new TipoRestaurante();
            tipo.setNomeTipo("Pizzaria");
            tipo.setDescricao("Restaurante especializado em pizzas");
            TipoRestauranteDAO tipoDAO = new TipoRestauranteDAO();
            int idTipo = tipoDAO.inserir(tipo);
            System.out.println("TipoRestaurante inserido com ID: " + idTipo);

            // 🔹 2. Restaurante
            Restaurante restaurante = new Restaurante();
            restaurante.setNome("Pizza Delícia");
            restaurante.setTipoCozinha("Italiana");
            restaurante.setTelefone("11999998888");
            RestauranteDAO restauranteDAO = new RestauranteDAO();
            int idRestaurante = restauranteDAO.inserir(restaurante);
            System.out.println("Restaurante inserido com ID: " + idRestaurante);

            // 🔹 3. Cliente
            Cliente cliente = new Cliente();
            cliente.setNome("João Silva");
            cliente.setTelefone("11987654321");
            cliente.setEndereco("Rua das Flores, 123");
            ClienteDAO clienteDAO = new ClienteDAO();
            int idCliente = clienteDAO.inserir(cliente);
            System.out.println("Cliente inserido com ID: " + idCliente);


            // 🔹 4. Entregador
            Entregador entregador = new Entregador();
            entregador.setNome("Carlos Souza");
            entregador.setTelefone("11988887777");
            entregador.setTipoVeiculo("Moto");
            entregador.setIdRestaurante(idRestaurante);
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            entregadorDAO.inserir(entregador);
            int idEntregador = entregador.getIdEntregador();
            System.out.println("Entregador inserido com ID: " + idEntregador);


            // 🔹 5. Produto
            Produto produto = new Produto();
            produto.setNome("Pizza Calabresa");
            produto.setPreco(49.90);
            produto.setDescricao("Pizza de calabresa com cebola e azeitonas");
            produto.setCategoria("Pizza Salgada");
            produto.setIdRestaurante(idRestaurante);
            ProdutoDAO produtoDAO = new ProdutoDAO();
            int idProduto = produtoDAO.inserir(produto);
            System.out.println("Produto inserido com ID: " + idProduto);

            // 🔹 6. Pedido
            Pedido pedido = new Pedido();
            pedido.setIdCliente(idCliente);
            pedido.setIdRestaurante(idRestaurante);
            pedido.setDataHora(new Date());
            pedido.setStatus(StatusPedido.EM_PREPARO);
            PedidoDAO pedidoDAO = new PedidoDAO();
            int idPedido = pedidoDAO.inserir(pedido);
            System.out.println("Pedido inserido com ID: " + idPedido);

            // 🔹 7. ItemPedido
            ItemPedido item = new ItemPedido();
            item.setDescricao("Pizza Calabresa");
            item.setQuantidade(1);
            item.setPreco(49.90);
            item.setIdPedido(idPedido);
            ItemPedidoDAO itemDAO = new ItemPedidoDAO();
            int idItem = itemDAO.inserir(item);
            System.out.println("ItemPedido inserido com ID: " + idItem);

            // 🔹 8. Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setIdPedido(idPedido);
            pagamento.setValorTotal(49.90);
            pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_DE_CREDITO);
            pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
            pagamento.setDataPagamento(LocalDate.now());
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            int idPagamento = pagamentoDAO.inserir(pagamento);
            System.out.println("Pagamento inserido com ID: " + idPagamento);

            // 🔹 9. Entrega
            Entrega entrega = new Entrega();
            entrega.setIdPedido(idPedido);
            entrega.setIdEntregador(idEntregador);
            entrega.setStatusEntrega(StatusEntrega.PENDENTE);
            entrega.setDataSaida(new Date());
            entrega.setTempoEstimado("30 minutos");
            entrega.setObservacao("Sem observações");
            EntregaDAO entregaDAO = new EntregaDAO();
            int idEntrega = entregaDAO.inserir(entrega);
            System.out.println("Entrega inserida com ID: " + idEntrega);

            System.out.println("\n✅ Inserções realizadas com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro no teste de inserção: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
