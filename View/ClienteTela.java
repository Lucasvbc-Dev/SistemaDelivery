package View;

import javax.swing.*;
import java.awt.*;

public class ClienteTela extends JFrame {

    public ClienteTela() {
        setTitle("Sistema Delivery - Menu do Cliente");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configuração do Painel Principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titleLabel = new JLabel("Bem-vindo(a) ao Sistema Delivery!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão 1: Ver Produtos e Fazer Pedido
        JButton produtosBtn = createStyledButton("Ver Produtos e Fazer Pedido", new Color(60, 140, 255));
        produtosBtn.addActionListener(e -> {
            new ProdutoTelaCliente().setVisible(true);
        });
        mainPanel.add(produtosBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botão 2: Ver Meus Pedidos
        JButton pedidosBtn = createStyledButton("Ver Meus Pedidos", new Color(60, 140, 255));
        pedidosBtn.addActionListener(e -> {
            new PedidoTelaCliente().setVisible(true);
        });
        mainPanel.add(pedidosBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // NOVO Botão: Gerenciar Meus Dados (CRUD de Cliente)
        JButton gerenciarDadosBtn = createStyledButton("Gerenciar Meu Cadastro", new Color(40, 180, 99));
        gerenciarDadosBtn.addActionListener(e -> {
            // Abre a nova tela de CRUD
            new ClienteTelaCadastro().setVisible(true);
        });
        mainPanel.add(gerenciarDadosBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botão 3: Voltar
        JButton voltarBtn = createStyledButton("Voltar para Escolha de Perfil", new Color(220, 50, 50));
        voltarBtn.addActionListener(e -> {
            // Fecha esta tela e volta para a tela de escolha de perfil (se houver)
            dispose();
            // new EscolhaPerfil().setVisible(true); // Descomente se quiser voltar para a EscolhaPerfil
        });
        mainPanel.add(voltarBtn);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Para expandir horizontalmente
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
