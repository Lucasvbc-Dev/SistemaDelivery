package View;

import javax.swing.*;
import java.awt.*;

public class RestauranteTela extends JFrame {

    public RestauranteTela() {
        setTitle("Sistema Delivery - Menu do Restaurante");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel Principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título
        JLabel titleLabel = new JLabel("Gerenciamento do Restaurante");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botões de Funcionalidade

        // CORREÇÃO: Botão para abrir o CRUD de Restaurante
        JButton gerenciarDadosBtn = createStyledButton("Gerenciar Dados do Restaurante (CRUD)", new Color(40, 180, 99));
        gerenciarDadosBtn.addActionListener(e -> {
            new RestauranteTelaCRUD().setVisible(true); // Abre a tela de CRUD
        });
        mainPanel.add(gerenciarDadosBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botão: Gerenciar Tipos de Restaurante (Botão original da sua lista)
        JButton tiposBtn = createStyledButton("Gerenciar Tipos de Restaurante", new Color(60, 140, 255));
        tiposBtn.addActionListener(e -> {
            // Assumindo que a tela de tipos de restaurante se chama TipoRestauranteTela
            new TipoRestauranteTela().setVisible(true);
        });
        mainPanel.add(tiposBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botão: Gerenciar Produtos
        JButton produtosBtn = createStyledButton("Gerenciar Produtos", new Color(60, 140, 255));
        produtosBtn.addActionListener(e -> {
            new ProdutoTelaRestaurante().setVisible(true);
        });
        mainPanel.add(produtosBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botão: Gerenciar Entregadores
        JButton entregadoresBtn = createStyledButton("Gerenciar Entregadores", new Color(60, 140, 255));
        entregadoresBtn.addActionListener(e -> {
            new EntregadorTelaRestaurante().setVisible(true);
        });
        mainPanel.add(entregadoresBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botão: Gerenciar Pedidos
        JButton pedidosBtn = createStyledButton("Gerenciar Pedidos", new Color(60, 140, 255));
        pedidosBtn.addActionListener(e -> {
            new PedidoTelaRestaurante().setVisible(true);
        });
        mainPanel.add(pedidosBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));


        // Botão Voltar
        JButton voltarBtn = createStyledButton("Voltar para Escolha de Perfil", new Color(220, 50, 50));
        voltarBtn.addActionListener(e -> {
            dispose();
            // new EscolhaPerfil().setVisible(true); // Descomente se quiser voltar para a EscolhaPerfil
        });
        mainPanel.add(voltarBtn);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Para expandir horizontalmente
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
