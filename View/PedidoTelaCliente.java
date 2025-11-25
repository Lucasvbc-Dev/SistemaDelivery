package View;

import model.Pedido;
import service.PedidoService;
import Enum.StatusPedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

public class PedidoTelaCliente extends JFrame {
    private PedidoService pedidoService = new PedidoService();
    private JTextField idClienteField;
    private int idClienteLogado = -1;
    // Adicionado "Valor Total" para exibir o novo campo
    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID Pedido", "Restaurante ID", "Data/Hora", "Status", "Valor Total"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);
    private static final DecimalFormat df = new DecimalFormat("R$ #,##0.00");

    public PedidoTelaCliente() {
        setTitle("Cliente - Meus Pedidos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Painel superior para o ID do Cliente
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.add(new JLabel("Seu ID de Cliente:"));
        idClienteField = new JTextField(5);
        topPanel.add(idClienteField);
        JButton loginBtn = new JButton("Filtrar Meus Pedidos");
        loginBtn.addActionListener(e -> {
            try {
                idClienteLogado = Integer.parseInt(idClienteField.getText());
                carregar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de Cliente inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                idClienteLogado = -1;
            }
        });
        topPanel.add(loginBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tabela
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refresh = createStyledButton("Atualizar Lista", new Color(60, 140, 255));
        refresh.addActionListener(e -> carregar());

        JButton cancelar = createStyledButton("Cancelar Pedido", new Color(200, 50, 50));
        cancelar.addActionListener(e -> cancelar());

        JButton verEntrega = createStyledButton("Ver Entrega", new Color(40, 180, 99));
        verEntrega.addActionListener(e -> verEntrega());

        JButton fechar = createStyledButton("Fechar", Color.DARK_GRAY);
        fechar.addActionListener(e -> dispose());

        buttonPanel.add(refresh);
        buttonPanel.add(cancelar);
        buttonPanel.add(verEntrega);
        buttonPanel.add(fechar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
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
        if (idClienteLogado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, insira seu ID de Cliente para listar os pedidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            model.setRowCount(0);
            return;
        }

        try {
            model.setRowCount(0);
            List<Pedido> pedidos = pedidoService.listarPorCliente(idClienteLogado);

            for (Pedido p : pedidos) {
                model.addRow(new Object[]{
                        p.getIdPedido(),
                        p.getIdRestaurante(),
                        p.getDataHora(),
                        p.getStatus() != null ? p.getStatus().name() : "N/A",
                        df.format(p.getValorTotal())
                });
            }
            setTitle("Cliente - Meus Pedidos (ID: " + idClienteLogado + ")");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar pedidos: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = (int) model.getValueAt(r, 0);
        String statusAtual = (String) model.getValueAt(r, 3);

        if (statusAtual.equals(StatusPedido.ENTREGUE.name()) || statusAtual.equals(StatusPedido.CANCELADO.name())) {
            JOptionPane.showMessageDialog(this, "Não é possível cancelar um pedido com status: " + statusAtual, "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja cancelar o pedido " + idPedido + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // O método cancelarPedido no service já verifica se o pedido pode ser cancelado
                pedidoService.cancelarPedido(idPedido);
                JOptionPane.showMessageDialog(this, "Pedido " + idPedido + " cancelado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cancelar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verEntrega() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para ver a entrega.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = (int) model.getValueAt(r, 0);
        // Abre a tela de detalhes da entrega
        new EntregaClienteTela(idPedido).setVisible(true);
    }
}
