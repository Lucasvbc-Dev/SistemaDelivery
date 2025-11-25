package Main;

import dao.*;
import model.*;
import Enum.*;
import java.sql.SQLException;
import java.util.Date;
import java.time.LocalDate;

public class MainAtualizacao{
    public static void main(String[] args) {

        try {
            System.out.println("=== TESTE DE ATUALIZAÇÃO DE DADOS EXISTENTES ===");

            // ⚠️ Defina os IDs que já existem no banco (gerados na inserção anterior)
            int idTipo = 1;
            int idRestaurante = 1;
            int idCliente = 1;
            int idEntregador = 1;
            int idProduto = 1;
            int idPedido = 1;
            int idItem = 1;
            int idPagamento = 1;
            int idEntrega = 1;

            // DAOs
            TipoRestauranteDAO tipoDAO = new TipoRestauranteDAO();
            RestauranteDAO restauranteDAO = new RestauranteDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            ProdutoDAO produtoDAO = new ProdutoDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();
            ItemPedidoDAO itemDAO = new ItemPedidoDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            EntregaDAO entregaDAO = new EntregaDAO();

            // 🔹 Atualizar TipoRestaurante
            TipoRestaurante tipo = new TipoRestaurante();
            tipo.setIdTipoRestaurante(idTipo);
            tipo.setNomeTipo("Pizzaria Gourmet");
            tipo.setDescricao("Restaurante com pizzas premium e artesanais");
            tipoDAO.atualizar(tipo);
            System.out.println("✅ TipoRestaurante atualizado!");

            // 🔹 Atualizar Restaurante
            Restaurante restaurante = new Restaurante();
            restaurante.setIdRestaurante(idRestaurante);
            restaurante.setNome("Pizza Premium");
            restaurante.setTipoCozinha("Italiana Moderna");
            restaurante.setTelefone("11999997777");
            restauranteDAO.atualizar(restaurante);
            System.out.println("✅ Restaurante atualizado!");

            // 🔹 Atualizar Cliente
            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            cliente.setNome("João Pedro Silva");
            cliente.setTelefone("11912345678");
            cliente.setEndereco("Rua Nova, 456");
            clienteDAO.atualizar(cliente);
            System.out.println("✅ Cliente atualizado!");

            // 🔹 Atualizar Entregador
            Entregador entregador = new Entregador();
            entregador.setIdEntregador(idEntregador);
            entregador.setNome("Carlos Almeida");
            entregador.setTelefone("11900001111");
            entregador.setTipoVeiculo("Carro");
            entregadorDAO.atualizar(entregador);
            System.out.println("✅ Entregador atualizado!");

            // 🔹 Atualizar Produto
            Produto produto = new Produto();
            produto.setIdProduto(idProduto);
            produto.setNome("Pizza Quatro Queijos");
            produto.setPreco(59.90);
            produto.setDescricao("Pizza com muçarela, parmesão, gorgonzola e provolone");
            produto.setCategoria("Pizza Especial");
            produto.setIdRestaurante(idRestaurante);
            produtoDAO.atualizar(produto);
            System.out.println("✅ Produto atualizado!");

            // 🔹 Atualizar Pedido
            Pedido pedido = new Pedido();
            pedido.setIdPedido(idPedido);
            pedido.setIdCliente(idCliente);
            pedido.setIdRestaurante(idRestaurante);
            pedido.setDataHora(new Date());
            pedido.setStatus(StatusPedido.ENTREGUE);
            pedidoDAO.atualizar(pedido);
            System.out.println("✅ Pedido atualizado!");

            // 🔹 Atualizar ItemPedido
            ItemPedido item = new ItemPedido();
            item.setIdItem(idItem);
            item.setDescricao("Pizza Quatro Queijos");
            item.setQuantidade(2);
            item.setPreco(59.90);
            itemDAO.atualizar(item);
            System.out.println("✅ ItemPedido atualizado!");

            // 🔹 Atualizar Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setIdPagamento(idPagamento);
            pagamento.setIdPedido(idPedido);
            pagamento.setValorTotal(59.90);
            pagamento.setMetodoPagamento(MetodoPagamento.CARTAO_DE_CREDITO);
            pagamento.setStatusPagamento(StatusPagamento.CONCLUIDO);
            pagamento.setDataPagamento(LocalDate.now());
            pagamentoDAO.atualizar(pagamento);
            System.out.println("✅ Pagamento atualizado!");

            // 🔹 Atualizar Entrega
            Entrega entrega = new Entrega();
            entrega.setIdEntrega(idEntrega);
            entrega.setIdPedido(idPedido);
            entrega.setIdEntregador(idEntregador);
            entrega.setStatusEntrega(StatusEntrega.ENTREGUE);
            entrega.setDataSaida(new Date());
            entrega.setDataEntrega(new Date());
            entrega.setTempoEstimado("30 minutos");
            entrega.setObservacao("Pedido entregue com sucesso!");
            entregaDAO.atualizar(entrega);
            System.out.println("✅ Entrega atualizada!");

            System.out.println("\n🎉 TODOS OS DADOS FORAM ATUALIZADOS COM SUCESSO!");

        } catch (SQLException e) {
            System.err.println("Erro no teste de atualização: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
