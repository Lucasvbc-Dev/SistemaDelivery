package View;

import model.Cliente;
import service.ClienteService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ClienteTelaCadastro extends JFrame {
    private ClienteService clienteService = new ClienteService();

    // Componentes para a tabela
    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone", "Endereço"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    // Componentes para os campos de entrada
    private JTextField idField = new JTextField(15);
    private JTextField nomeField = new JTextField(15);
    private JTextField telefoneField = new JTextField(15);
    private JTextArea enderecoArea = new JTextArea(3, 15);

    public ClienteTelaCadastro() {
        setTitle("CRUD de Clientes");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configuração da Tabela

        idField.setEditable(false); // ID não pode ser editado manualmente

        // Painel Principal (Tabela e Formulário)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Painel do Formulário (Direita)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionar campos ao formulário
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(telefoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(new JScrollPane(enderecoArea), gbc);

        // Painel de Botões do Formulário
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cadastrarBtn = createStyledButton("Cadastrar", new Color(40, 180, 99));
        JButton editarBtn = createStyledButton("Editar Selecionado", new Color(60, 140, 255));
        JButton deletarBtn = createStyledButton("Deletar Selecionado", new Color(220, 50, 50));
        JButton limparBtn = createStyledButton("Limpar", Color.GRAY);

        cadastrarBtn.addActionListener(e -> cadastrar());
        editarBtn.addActionListener(e -> editar());
        deletarBtn.addActionListener(e -> deletar());
        limparBtn.addActionListener(e -> limparCampos());

        formButtonPanel.add(cadastrarBtn);
        formButtonPanel.add(editarBtn);
        formButtonPanel.add(deletarBtn);
        formButtonPanel.add(limparBtn);

        // Adicionar painel de botões ao formulário
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(formButtonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.EAST);
        add(mainPanel);

        // Carregar dados iniciais
        carregarClientes();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregarClientes() {
        try {
            model.setRowCount(0);
            List<Cliente> clientes = clienteService.listarClientes();
            for (Cliente c : clientes) {
                model.addRow(new Object[]{c.getIdCliente(), c.getNome(), c.getTelefone(), c.getEndereco()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar clientes: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void cadastrar() {
        try {
            Cliente c = new Cliente();
            c.setNome(nomeField.getText());
            c.setTelefone(telefoneField.getText());
            c.setEndereco(enderecoArea.getText());

            clienteService.cadastrarCliente(c);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarClientes();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente c = new Cliente();
            c.setIdCliente(Integer.parseInt(idField.getText()));
            c.setNome(nomeField.getText());
            c.setTelefone(telefoneField.getText());
            c.setEndereco(enderecoArea.getText());

            clienteService.atualizarCliente(c);
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarClientes();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = Integer.parseInt(idField.getText());
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o cliente " + nomeField.getText() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                clienteService.deletarCliente(idCliente);
                JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarClientes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar cliente: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        telefoneField.setText("");
        enderecoArea.setText("");
        tbl.clearSelection();
    }
}
