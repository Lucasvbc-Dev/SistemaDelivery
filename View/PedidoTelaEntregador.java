package View;

import model.Entrega;
import service.EntregaService;
import Enum.StatusEntrega;
import model.Pedido;
import service.PedidoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PedidoTelaEntregador extends JFrame {
    private EntregaService entregaService = new EntregaService();
    private PedidoService pedidoService = new PedidoService();
    private JTextField idEntregadorField;
    private int idEntregadorLogado = -1;

    // Colunas da tabela: ID Entrega, ID Pedido, Status Entrega, ID Entregador, Detalhes (Cliente, Endereço)
    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID Entrega", "ID Pedido", "Status", "ID Entregador", "Cliente", "Endereço"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    public PedidoTelaEntregador() {
        setTitle("Entregador - Gerenciamento de Entregas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Painel superior para o ID do Entregador
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.add(new JLabel("Seu ID de Entregador:"));
        idEntregadorField = new JTextField(5);
        topPanel.add(idEntregadorField);
        JButton loginBtn = new JButton("Filtrar Entregas");
        loginBtn.addActionListener(e -> {
            try {
                idEntregadorLogado = Integer.parseInt(idEntregadorField.getText());
                carregar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de Entregador inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                idEntregadorLogado = -1;
            }
        });
        topPanel.add(loginBtn);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tabela
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refresh = createStyledButton("Atualizar Lista (Todas as Entregas)", new Color(60, 140, 255));
        refresh.addActionListener(e -> { idEntregadorLogado = -1; idEntregadorField.setText(""); carregar(); });

        JButton pegar = createStyledButton("Assumir Entrega (EM TRÂNSITO)", new Color(40, 180, 99));
        pegar.addActionListener(e -> pegarEntrega());

        JButton entregue = createStyledButton("Marcar como ENTREGUE", new Color(255, 180, 60));
        entregue.addActionListener(e -> marcarEntregue());

        JButton voltar = createStyledButton("Voltar ao Menu Principal", new Color(200, 50, 50));
        voltar.addActionListener(e -> {
            new EscolhaPerfil().setVisible(true);
            dispose();
        });

        buttonPanel.add(refresh);
        buttonPanel.add(pegar);
        buttonPanel.add(entregue);
        buttonPanel.add(voltar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Carrega todas as entregas inicialmente
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
            List<Entrega> entregas;

            if (idEntregadorLogado != -1) {
                // Se o ID do entregador foi filtrado, mostra apenas as dele
                entregas = entregaService.listarEntregasPorEntregador(idEntregadorLogado);
                setTitle("Entregador - Entregas Atribuídas (ID: " + idEntregadorLogado + ")");
            } else {
                // Caso contrário, mostra todas as entregas
                entregas = entregaService.listarEntregas();
                setTitle("Entregador - Gerenciamento de Entregas (Todas)");
            }

            for (Entrega e : entregas) {
                String clienteInfo = "N/A";
                String endereco = "N/A";

                // Busca detalhes do pedido para exibir Cliente e Endereço
                // Isso requer que o PedidoDAO.buscarPorId() carregue o enderecoEntrega
                Pedido p = pedidoService.buscarPedidoPorId(e.getIdPedido());
                if (p != null) {
                    clienteInfo = "Cliente ID: " + p.getIdCliente();
                    endereco = p.getEnderecoEntrega();
                }

                model.addRow(new Object[]{
                        e.getIdEntrega(),
                        e.getIdPedido(),
                        e.getStatusEntrega().name(),
                        e.getIdEntregador(),
                        clienteInfo,
                        endereco
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar entregas: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pegarEntrega() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma entrega na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (idEntregadorLogado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, filtre as entregas com seu ID primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEntrega = (int) model.getValueAt(r, 0);
        int idEntregadorTabela = (int) model.getValueAt(r, 3);
        String statusAtual = (String) model.getValueAt(r, 2);

        if (idEntregadorTabela != idEntregadorLogado) {
            JOptionPane.showMessageDialog(this, "Você só pode assumir entregas atribuídas a você.", "Erro de Ação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (statusAtual.equals(StatusEntrega.ENTREGUE.name())) {
            JOptionPane.showMessageDialog(this, "Esta entrega já foi concluída.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (statusAtual.equals(StatusEntrega.EM_TRANSITO.name())) {
            JOptionPane.showMessageDialog(this, "Esta entrega já está em trânsito.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Ação de assumir a entrega e mudar o status para EM_TRANSITO
            entregaService.atualizarStatus(idEntrega, StatusEntrega.EM_TRANSITO.name());
            JOptionPane.showMessageDialog(this, "Entrega " + idEntrega + " marcada como EM TRÂNSITO.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao assumir entrega: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void marcarEntregue() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma entrega na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (idEntregadorLogado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, filtre as entregas com seu ID primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEntrega = (int) model.getValueAt(r, 0);
        int idEntregadorTabela = (int) model.getValueAt(r, 3);
        String statusAtual = (String) model.getValueAt(r, 2);

        if (idEntregadorTabela != idEntregadorLogado) {
            JOptionPane.showMessageDialog(this, "Você só pode marcar como entregue as entregas atribuídas a você.", "Erro de Ação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (statusAtual.equals(StatusEntrega.ENTREGUE.name())) {
            JOptionPane.showMessageDialog(this, "Esta entrega já foi concluída.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!statusAtual.equals(StatusEntrega.EM_TRANSITO.name())) {
            JOptionPane.showMessageDialog(this, "A entrega deve estar no status 'EM TRÂNSITO' para ser marcada como 'ENTREGUE'.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Ação de marcar como entregue
            entregaService.atualizarStatus(idEntrega, StatusEntrega.ENTREGUE.name());
            JOptionPane.showMessageDialog(this, "Entrega " + idEntrega + " marcada como ENTREGUE.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao marcar como entregue: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
