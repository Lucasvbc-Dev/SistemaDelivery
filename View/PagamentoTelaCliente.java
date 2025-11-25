package View;

import model.Pedido;
import service.PedidoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PagamentoTelaCliente extends JFrame {

    private PedidoService pedidoService = new PedidoService();

    private DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID Pedido", "Status", "Data/Hora"}, 0
    ) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    private JTable tbl = new JTable(model);

    public PagamentoTelaCliente() {
        setTitle("Pagamentos - Cliente");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JScrollPane(tbl), BorderLayout.CENTER);

        JPanel b = new JPanel();
        JButton refresh = new JButton("Atualizar");
        refresh.addActionListener(e -> carregar());

        JButton marcarPago = new JButton("Marcar como Pago");
        marcarPago.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Selecione um pedido!");
                return;
            }

            int id = (int) model.getValueAt(r, 0);
            try {

                pedidoService.atualizarStatus(id, "PAGO");
                JOptionPane.showMessageDialog(this, "Pedido marcado como PAGO!");
                carregar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(e -> dispose());

        b.add(refresh);
        b.add(marcarPago);
        b.add(fechar);
        add(b, BorderLayout.SOUTH);

        carregar();
    }

    private void carregar() {
        try {
            model.setRowCount(0);


            List<Pedido> pedidos = pedidoService.listarPedidos();

            for (Pedido p : pedidos) {
                model.addRow(new Object[]{
                        p.getIdPedido(),
                        (p.getStatus() != null ? p.getStatus().name() : "--"),
                        (p.getDataHora() != null ? p.getDataHora().toString() : "--")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pedidos: " + ex.getMessage());
        }
    }
}
