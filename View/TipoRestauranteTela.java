package View;

import model.TipoRestaurante;
import service.TipoRestauranteService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TipoRestauranteTela extends JFrame {
    private TipoRestauranteService tipoRestauranteService = new TipoRestauranteService();

    private JTextField idField = new JTextField();
    private JTextField nomeTipoField = new JTextField(); // NOVO CAMPO
    private JTextArea descricaoArea = new JTextArea(3, 20);

    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    public TipoRestauranteTela() {
        setTitle("Tipos de Restaurante");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de Tipos de Restaurante
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Listener para preencher campos ao selecionar linha


        // Painel de Edição (Direita)
        JPanel editPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        editPanel.setPreferredSize(new Dimension(200, 0));
        editPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Tipo"));

        editPanel.add(new JLabel("ID:"));
        idField.setEditable(false);
        editPanel.add(idField);

        editPanel.add(new JLabel("Nome do Tipo:")); // NOVO CAMPO
        editPanel.add(nomeTipoField);

        editPanel.add(new JLabel("Descrição:"));
        JScrollPane scrollDescricao = new JScrollPane(descricaoArea);
        editPanel.add(scrollDescricao);

        mainPanel.add(editPanel, BorderLayout.EAST);

        // Painel de Botões (Inferior)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton adicionar = createStyledButton("Adicionar", new Color(40, 180, 99));
        adicionar.addActionListener(e -> cadastrar());

        JButton editar = createStyledButton("Editar Selecionado", new Color(60, 140, 255));
        editar.addActionListener(e -> editar());

        JButton deletar = createStyledButton("Remover Selecionado", new Color(200, 50, 50));
        deletar.addActionListener(e -> deletar());

        JButton voltar = createStyledButton("Voltar", Color.DARK_GRAY);
        voltar.addActionListener(e -> dispose());

        buttonPanel.add(adicionar);
        buttonPanel.add(editar);
        buttonPanel.add(deletar);
        buttonPanel.add(voltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        carregarTipos();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregarTipos() {
        try {
            model.setRowCount(0);
            List<TipoRestaurante> tipos = tipoRestauranteService.listarTipoRestaurantes();

            for (TipoRestaurante t : tipos) {
                model.addRow(new Object[]{
                        t.getIdTipoRestaurante(),
                        t.getNomeTipo(), // NOVO CAMPO
                        t.getDescricao()
                });
            }
            limparCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar tipos de restaurante: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }



    private TipoRestaurante lerTipoDosCampos() {
        TipoRestaurante t = new TipoRestaurante();

        // CORREÇÃO: Lendo o novo campo
        t.setNomeTipo(nomeTipoField.getText().trim());
        t.setDescricao(descricaoArea.getText().trim());

        try {
            if (!idField.getText().isEmpty()) {
                t.setIdTipoRestaurante(Integer.parseInt(idField.getText()));
            }
        } catch (NumberFormatException ex) {
            // Ignorar, o ID será gerado na inserção
        }

        return t;
    }

    private void cadastrar() {
        TipoRestaurante t = lerTipoDosCampos();

        // Validação básica para o novo campo
        if (t.getNomeTipo().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: Nome do tipo do restaurante é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tipoRestauranteService.cadastrarTipoRestaurante(t);
            JOptionPane.showMessageDialog(this, "Tipo de restaurante cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarTipos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar tipo de restaurante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        TipoRestaurante t = lerTipoDosCampos();

        if (t.getIdTipoRestaurante() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um tipo para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação básica para o novo campo
        if (t.getNomeTipo().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: Nome do tipo do restaurante é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            tipoRestauranteService.atualizarTipoRestaurante(t);
            JOptionPane.showMessageDialog(this, "Tipo de restaurante atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarTipos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar tipo de restaurante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um tipo para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idTipo = (int) model.getValueAt(r, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o tipo " + idTipo + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                tipoRestauranteService.deletarTipoRestaurante(idTipo);
                JOptionPane.showMessageDialog(this, "Tipo de restaurante removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTipos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover tipo de restaurante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeTipoField.setText("");
        descricaoArea.setText("");
        tbl.clearSelection();
    }
}
