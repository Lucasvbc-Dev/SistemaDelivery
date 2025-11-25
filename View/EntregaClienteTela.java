package View;

import model.Entrega;
import service.EntregaService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class EntregaClienteTela extends JFrame {
    private EntregaService entregaService = new EntregaService();
    private int idPedido;
    private JTextArea txt = new JTextArea();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public EntregaClienteTela(int idPedido) {
        this.idPedido = idPedido;
        setTitle("Detalhes da Entrega - Pedido " + idPedido);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titleLabel = new JLabel("Status da Entrega para o Pedido #" + idPedido, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        txt.setEditable(false);
        txt.setFont(new Font("Monospaced", Font.PLAIN, 14));
        mainPanel.add(new JScrollPane(txt), BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refresh = createStyledButton("Atualizar Status", new Color(60, 140, 255));
        refresh.addActionListener(e -> carregar());
        JButton fechar = createStyledButton("Fechar", Color.DARK_GRAY);
        fechar.addActionListener(e -> dispose());
        
        buttonPanel.add(refresh);
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
            Entrega en = entregaService.buscarEntregaPorPedido(idPedido);
            StringBuilder sb = new StringBuilder();
            
            if (en == null) {
                sb.append("Nenhuma entrega encontrada para o Pedido #").append(idPedido).append(".\n");
                sb.append("O pedido pode ainda estar em preparo no restaurante.");
            } else {
                sb.append("--------------------------------------------------\n");
                sb.append(String.format("%-15s %d\n", "ID Entrega:", en.getIdEntrega()));
                sb.append(String.format("%-15s %s\n", "STATUS:", en.getStatusEntrega().name()));
                sb.append(String.format("%-15s %d\n", "ID Entregador:", en.getIdEntregador()));
                sb.append("--------------------------------------------------\n");
                sb.append(String.format("%-15s %s\n", "Saída:", en.getDataSaida() != null ? sdf.format(en.getDataSaida()) : "Aguardando"));
                sb.append(String.format("%-15s %s\n", "Entrega Est.:", en.getTempoEstimado() != null ? en.getTempoEstimado() : "N/A"));
                sb.append(String.format("%-15s %s\n", "Entrega Real:", en.getDataEntrega() != null ? sdf.format(en.getDataEntrega()) : "Pendente"));
                sb.append("--------------------------------------------------\n");
                sb.append(String.format("%-15s %s\n", "Observação:", en.getObservacao() != null ? en.getObservacao() : "Nenhuma"));
                sb.append("--------------------------------------------------\n");
            }
            
            txt.setText(sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar detalhes da entrega: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            txt.setText("Erro ao carregar dados.");
        }
    }
}
