package View;

import javax.swing.*;
import java.awt.*;

public class EscolhaPerfil extends JFrame {
    public EscolhaPerfil() {
        setTitle("Sistema Delivery - Escolha de Perfil");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Escolha seu Perfil de Acesso", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel de botões central com GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));

        // Botões
        JButton clienteBtn = createStyledButton("Cliente", new Color(60, 140, 255)); // Azul
        clienteBtn.addActionListener(e -> {
            new ClienteTela().setVisible(true);
            dispose();
        });
        buttonPanel.add(clienteBtn);

        JButton restauranteBtn = createStyledButton("Restaurante", new Color(40, 180, 99)); // Verde
        restauranteBtn.addActionListener(e -> {
            new RestauranteTela().setVisible(true);
            dispose();
        });
        buttonPanel.add(restauranteBtn);

        JButton entregadorBtn = createStyledButton("Entregador", new Color(255, 180, 60)); // Laranja
        entregadorBtn.addActionListener(e -> {
            new PedidoTelaEntregador().setVisible(true);
            dispose();
        });
        buttonPanel.add(entregadorBtn);

        JButton sairBtn = createStyledButton("Sair do Sistema", new Color(200, 50, 50)); // Vermelho
        sairBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(sairBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        return button;
    }
}
