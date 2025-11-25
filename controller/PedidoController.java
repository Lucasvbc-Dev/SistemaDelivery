package controller;

import model.Pedido;
import service.PedidoService;

import java.sql.SQLException;
import java.util.List;

public class PedidoController {
    public PedidoService pedidoService;

    public PedidoController() {
        this.pedidoService = new PedidoService();
    }

    public void registrarPedido(Pedido pedido) {
        try {
            pedidoService.registrarPedido(pedido);
            System.out.println("Pedido registrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(" Erro de validação: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" Erro ao registrar pedido: " + e.getMessage());
        }
    }

    public void listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidos();
            for (Pedido p : pedidos) {
                System.out.println(p);
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao listar pedidos: " + e.getMessage());
        }
    }

    public void listarPedidosPorCliente(int idCliente) {
        try {
            List<Pedido> pedidos = pedidoService.listarPorCliente(idCliente);
            for (Pedido p : pedidos) {
                System.out.println(p);
            }
        } catch (SQLException e) {
            System.out.println(" Erro ao listar pedidos do cliente: " + e.getMessage());
        }
    }

    public void atualizarStatusPedido(int idPedido, String novoStatus) {
        try {
            pedidoService.atualizarStatus(idPedido, novoStatus);
            System.out.println(" Status do pedido atualizado para: " + novoStatus);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(" Erro ao atualizar status: " + e.getMessage());
        }
    }

    public void cancelarPedido(int idPedido) {
        try {
            pedidoService.cancelarPedido(idPedido);
            System.out.println(" Pedido cancelado com sucesso!");
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(" Erro ao cancelar pedido: " + e.getMessage());
        }
    }

    public List<Pedido> listarPedidosRetorno() {
        try {
            return pedidoService.listarPedidos();
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

}
