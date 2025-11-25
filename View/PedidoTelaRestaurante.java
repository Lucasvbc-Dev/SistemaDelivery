package View;

import model.Pedido;
import model.Entrega; // Importado para uso na associação de entregador
import service.PedidoService;
import service.EntregaService;
import Enum.StatusPedido;
import Enum.StatusEntrega; // Importado para uso na associação de entregador
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PedidoTelaRestaurante extends JFrame {
    private PedidoService pedidoService = new PedidoService();
    private EntregaService entregaService = new EntregaService();

    // Adicionado "Valor Total" para exibir o novo campo
    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Cliente ID", "Status", "Restaurante ID", "Valor Total"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    public PedidoTelaRestaurante() {
        setTitle("Pedidos - Restaurante");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton refresh = createStyledButton("Refresh", Color.GRAY);
        refresh.addActionListener(e -> carregar());

        JButton marcarPreparo = createStyledButton("Marcar EM_PREPARO", new Color(255, 180, 60));
        marcarPreparo.addActionListener(e -> atualizarStatus(StatusPedido.EM_PREPARO));

        // CORREÇÃO: Usando o status correto do Pedido (SAIU_PARA_ENTREGA)
        JButton marcarTransito = createStyledButton("Marcar A_CAMINHO", new Color(60, 140, 255));
        marcarTransito.addActionListener(e -> atualizarStatus(StatusPedido.A_CAMINHO));

        JButton marcarEntregue = createStyledButton("Marcar ENTREGUE", new Color(40, 180, 99));
        marcarEntregue.addActionListener(e -> atualizarStatus(StatusPedido.ENTREGUE));

        JButton associarEntregador = createStyledButton("Associar Entregador", new Color(150, 50, 200));
        associarEntregador.addActionListener(e -> associarEntregador());

        JButton fechar = createStyledButton("Fechar", Color.DARK_GRAY);
        fechar.addActionListener(e -> dispose());

        buttonPanel.add(refresh);
        buttonPanel.add(marcarPreparo);
        buttonPanel.add(marcarTransito);
        buttonPanel.add(marcarEntregue);
        buttonPanel.add(associarEntregador);
        buttonPanel.add(fechar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        carregar();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregar() {
        try {
            model.setRowCount(0);
            List<Pedido> pedidos = pedidoService.listarPedidos();

            for (Pedido p : pedidos) {
                model.addRow(new Object[]{
                        p.getIdPedido(),
                        p.getIdCliente(),
                        p.getStatus() != null ? p.getStatus().name() : "N/A",
                        p.getIdRestaurante(),
                        p.getValorTotal()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar pedidos: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarStatus(StatusPedido novoStatus) {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = (int) model.getValueAt(r, 0);

        try {
            pedidoService.atualizarStatus(idPedido, novoStatus.name());
            JOptionPane.showMessageDialog(this, "Status do Pedido " + idPedido + " atualizado para " + novoStatus.name(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar status: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void associarEntregador() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para associar um entregador.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = (int) model.getValueAt(r, 0);
        String statusAtual = (String) model.getValueAt(r, 2);

        if (!statusAtual.equals(StatusPedido.EM_PREPARO.name())) {
            JOptionPane.showMessageDialog(this, "O pedido deve estar em 'EM_PREPARO' para associar um entregador.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idEntregadorStr = JOptionPane.showInputDialog(this, "Digite o ID do Entregador para o Pedido " + idPedido + ":");
        if (idEntregadorStr == null) return;

        try {
            int idEntregador = Integer.parseInt(idEntregadorStr.trim());

            // 1. Buscar o pedido para obter dados necessários (Restaurante ID e Cliente ID)
            Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);
            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Criar o objeto Entrega
            Entrega entrega = new Entrega();
            entrega.setIdPedido(idPedido);
            entrega.setIdEntregador(idEntregador);
            entrega.setIdEntrega(pedido.getIdRestaurante());
            entrega.setStatusEntrega(StatusEntrega.PENDENTE); // Começa como PENDENTE

            // 3. Cadastrar a entrega (usando o método existente no seu EntregaService)
            entregaService.cadastrarEntrega(entrega);

            // 4. Atualizar o status do pedido para indicar que a entrega foi criada
            pedidoService.atualizarStatus(idPedido, StatusPedido.A_CAMINHO.name());

            JOptionPane.showMessageDialog(this, "Entregador " + idEntregador + " associado ao Pedido " + idPedido + ".\nStatus do Pedido atualizado para 'SAIU_PARA_ENTREGA'.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregar();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID do Entregador inválido. Deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao associar entregador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
