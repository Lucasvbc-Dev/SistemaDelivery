package View;

import model.Restaurante;
import service.RestauranteService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RestauranteTelaCRUD extends JFrame {
    private RestauranteService restauranteService = new RestauranteService();

    // Componentes para a tabela
    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Tipo Cozinha", "Telefone"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    // Componentes para os campos de entrada
    private JTextField idField = new JTextField(15);
    private JTextField nomeField = new JTextField(15);
    private JTextField tipoCozinhaField = new JTextField(15);
    private JTextField telefoneField = new JTextField(15);

    public RestauranteTelaCRUD() {
        setTitle("CRUD de Restaurantes");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configuração da Tabela
        tbl.getSelectionModel().addListSelectionListener(e -> preencherCampos());
        idField.setEditable(false); // ID não pode ser editado manualmente

        // Painel Principal (Tabela e Formulário)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Painel do Formulário (Direita)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Restaurante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adicionar campos ao formulário
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Tipo Cozinha:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(tipoCozinhaField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(telefoneField, gbc);

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
        carregarRestaurantes();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregarRestaurantes() {
        try {
            model.setRowCount(0);
            List<Restaurante> restaurantes = restauranteService.listarRestaurantes();
            for (Restaurante r : restaurantes) {
                model.addRow(new Object[]{r.getIdRestaurante(), r.getNome(), r.getTipoCozinha(), r.getTelefone()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar restaurantes: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        int r = tbl.getSelectedRow();
        if (r >= 0) {
            int idRestaurante = (int) model.getValueAt(r, 0);
            try {
                Restaurante res = restauranteService.buscarRestaurantePorId(idRestaurante);
                if (res != null) {
                    idField.setText(String.valueOf(res.getIdRestaurante()));
                    nomeField.setText(res.getNome());
                    tipoCozinhaField.setText(res.getTipoCozinha());
                    telefoneField.setText(res.getTelefone());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar restaurante: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cadastrar() {
        try {
            Restaurante res = new Restaurante();
            res.setNome(nomeField.getText());
            res.setTipoCozinha(tipoCozinhaField.getText());
            res.setTelefone(telefoneField.getText());

            restauranteService.cadastrarRestaurante(res);
            JOptionPane.showMessageDialog(this, "Restaurante cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarRestaurantes();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar restaurante: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um restaurante para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Restaurante res = new Restaurante();
            res.setIdRestaurante(Integer.parseInt(idField.getText()));
            res.setNome(nomeField.getText());
            res.setTipoCozinha(tipoCozinhaField.getText());
            res.setTelefone(telefoneField.getText());

            restauranteService.atualizarRestaurante(res);
            JOptionPane.showMessageDialog(this, "Restaurante atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarRestaurantes();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar restaurante: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um restaurante para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idRestaurante = Integer.parseInt(idField.getText());
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o restaurante " + nomeField.getText() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                restauranteService.deletarRestaurante(idRestaurante);
                JOptionPane.showMessageDialog(this, "Restaurante deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarRestaurantes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar restaurante: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        tipoCozinhaField.setText("");
        telefoneField.setText("");
        tbl.clearSelection();
    }
}
