package View;

import model.Entregador;
import service.EntregadorService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EntregadorTelaRestaurante extends JFrame {
    private EntregadorService entregadorService = new EntregadorService();

    private JTextField idField = new JTextField();
    private JTextField nomeField = new JTextField();
    private JTextField telefoneField = new JTextField(); // NOVO CAMPO
    private JTextField tipoVeiculoField = new JTextField(); // NOVO CAMPO
    private JTextField idRestauranteField = new JTextField();

    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone", "Tipo Veículo"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    public EntregadorTelaRestaurante() {
        setTitle("Restaurante - Gerenciar Entregadores");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de Entregadores
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Listener para preencher campos ao selecionar linha
        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                preencherCampos();
            }
        });

        // Painel de Edição (Direita)
        JPanel editPanel = new JPanel(new GridLayout(12, 1, 5, 5)); // Aumentado para 12 linhas
        editPanel.setPreferredSize(new Dimension(200, 0));
        editPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Entregador"));

        editPanel.add(new JLabel("ID:"));
        idField.setEditable(false);
        editPanel.add(idField);

        editPanel.add(new JLabel("Nome:"));
        editPanel.add(nomeField);

        // Novos campos
        editPanel.add(new JLabel("Telefone:"));
        editPanel.add(telefoneField);

        editPanel.add(new JLabel("Tipo Veículo:"));
        editPanel.add(tipoVeiculoField);
        // Fim dos novos campos

        editPanel.add(new JLabel("ID Restaurante:"));
        editPanel.add(idRestauranteField);

        mainPanel.add(editPanel, BorderLayout.EAST);

        // Painel de Botões (Inferior)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cadastrar = createStyledButton("Cadastrar", new Color(40, 180, 99));
        cadastrar.addActionListener(e -> cadastrar());

        JButton editar = createStyledButton("Editar Selecionado", new Color(60, 140, 255));
        editar.addActionListener(e -> editar());

        JButton deletar = createStyledButton("Deletar Selecionado", new Color(200, 50, 50));
        deletar.addActionListener(e -> deletar());

        JButton voltar = createStyledButton("Voltar", Color.DARK_GRAY);
        voltar.addActionListener(e -> dispose());

        buttonPanel.add(cadastrar);
        buttonPanel.add(editar);
        buttonPanel.add(deletar);
        buttonPanel.add(voltar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        carregarEntregadores();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregarEntregadores() {
        try {
            model.setRowCount(0);
            List<Entregador> entregadores = entregadorService.listarEntregadores();

            for (Entregador e : entregadores) {
                model.addRow(new Object[]{
                        e.getIdEntregador(),
                        e.getNome(),
                        e.getTelefone(), // NOVO CAMPO
                        e.getTipoVeiculo() // NOVO CAMPO
                });
            }
            limparCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar entregadores: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        int r = tbl.getSelectedRow();
        if (r >= 0) {
            int idEntregador = (int) model.getValueAt(r, 0);
            try {
                // CORREÇÃO: O método correto é buscarEntregadorPorId
                Entregador e = entregadorService.buscarEntregadorPorId(idEntregador);
                if (e != null) {
                    idField.setText(String.valueOf(e.getIdEntregador()));
                    nomeField.setText(e.getNome());
                    telefoneField.setText(e.getTelefone());
                    tipoVeiculoField.setText(e.getTipoVeiculo());
                    idRestauranteField.setText(String.valueOf(e.getIdRestaurante()));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar entregador: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private Entregador lerEntregadorDosCampos() {
        try {
            Entregador e = new Entregador();
            e.setNome(nomeField.getText().trim());
            e.setTelefone(telefoneField.getText().trim()); // NOVO CAMPO
            e.setTipoVeiculo(tipoVeiculoField.getText().trim()); // NOVO CAMPO

            String idRestauranteStr = idRestauranteField.getText().trim();
            if (idRestauranteStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID do Restaurante é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            e.setIdRestaurante(Integer.parseInt(idRestauranteStr));

            if (!idField.getText().isEmpty()) {
                e.setIdEntregador(Integer.parseInt(idField.getText()));
            }

            return e;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: ID Restaurante deve ser um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void cadastrar() {
        Entregador e = lerEntregadorDosCampos();
        if (e == null) return;

        try {
            entregadorService.cadastrarEntregador(e);
            JOptionPane.showMessageDialog(this, "Entregador cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarEntregadores();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar entregador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        Entregador e = lerEntregadorDosCampos();
        if (e == null || e.getIdEntregador() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um entregador para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            entregadorService.atualizarEntregador(e);
            JOptionPane.showMessageDialog(this, "Entregador atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarEntregadores();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar entregador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um entregador para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEntregador = (int) model.getValueAt(r, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o entregador " + idEntregador + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                entregadorService.deletarEntregador(idEntregador);
                JOptionPane.showMessageDialog(this, "Entregador deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarEntregadores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar entregador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        telefoneField.setText("");
        tipoVeiculoField.setText("");
        idRestauranteField.setText("");
        tbl.clearSelection();
    }
}
