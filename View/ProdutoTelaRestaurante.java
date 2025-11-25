package View;

import model.Produto;
import service.ProdutoService;
import service.RestauranteService;
import model.Restaurante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProdutoTelaRestaurante extends JFrame {
    private ProdutoService produtoService = new ProdutoService();
    private RestauranteService restauranteService = new RestauranteService();

    private JTextField idField = new JTextField();
    private JTextField nomeField = new JTextField();
    private JTextField precoField = new JTextField();
    private JTextField descricaoField = new JTextField();
    private JTextField idRestauranteField = new JTextField(); // Campo que estava com erro de leitura

    // Mapa para armazenar ID do Restaurante -> Nome do Restaurante
    private Map<Integer, String> mapaRestaurantes;

    private DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Restaurante"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private JTable tbl = new JTable(model);

    public ProdutoTelaRestaurante() {
        setTitle("Restaurante - Produtos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela de Produtos
        mainPanel.add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Listener para preencher campos ao selecionar linha
        tbl.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbl.getSelectedRow() != -1) {
                preencherCampos();
            }
        });

        // Painel de Edição (Direita)
        JPanel editPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        editPanel.setPreferredSize(new Dimension(200, 0));
        editPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Produto"));

        editPanel.add(new JLabel("ID:"));
        idField.setEditable(false);
        editPanel.add(idField);

        editPanel.add(new JLabel("Nome:"));
        editPanel.add(nomeField);

        editPanel.add(new JLabel("Preço:"));
        editPanel.add(precoField);

        editPanel.add(new JLabel("Descrição:"));
        editPanel.add(descricaoField);

        editPanel.add(new JLabel("ID Restaurante:"));
        editPanel.add(idRestauranteField); // O campo ID Restaurante

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

        carregarRestaurantes(); // Carrega o mapa de restaurantes
        carregarProdutos(); // Carrega a tabela
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregarRestaurantes() {
        try {
            List<Restaurante> restaurantes = restauranteService.listarRestaurantes();
            mapaRestaurantes = restaurantes.stream()
                    .collect(Collectors.toMap(Restaurante::getIdRestaurante, Restaurante::getNome));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar restaurantes: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            mapaRestaurantes = Map.of(); // Inicializa como vazio em caso de erro
        }
    }

    private void carregarProdutos() {
        try {
            model.setRowCount(0);
            List<Produto> produtos = produtoService.listarProdutos();

            for (Produto p : produtos) {
                String nomeRestaurante = mapaRestaurantes.getOrDefault(p.getIdRestaurante(), "ID " + p.getIdRestaurante() + " (Não Encontrado)");
                model.addRow(new Object[]{
                        p.getIdProduto(),
                        p.getNome(),
                        p.getPreco(),
                        nomeRestaurante
                });
            }
            limparCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar produtos: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        int r = tbl.getSelectedRow();
        if (r >= 0) {
            // O ID do produto está na coluna 0
            int idProduto = (int) model.getValueAt(r, 0);
            try {
                // CORREÇÃO: O método correto é buscarProdutoPorId
                Produto p = produtoService.buscarProdutoPorId(idProduto);
                if (p != null) {
                    idField.setText(String.valueOf(p.getIdProduto()));
                    nomeField.setText(p.getNome());
                    precoField.setText(String.valueOf(p.getPreco()));
                    descricaoField.setText(p.getDescricao());
                    idRestauranteField.setText(String.valueOf(p.getIdRestaurante()));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Produto lerProdutoDosCampos() {
        try {
            Produto p = new Produto();
            p.setNome(nomeField.getText().trim());
            p.setPreco(Double.parseDouble(precoField.getText().trim()));
            p.setDescricao(descricaoField.getText().trim());

            // CORREÇÃO: Garante que o ID do Restaurante seja lido corretamente
            String idRestauranteStr = idRestauranteField.getText().trim();
            if (idRestauranteStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Erro: O produto deve estar vinculado a um restaurante!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            p.setIdRestaurante(Integer.parseInt(idRestauranteStr));

            // Se for uma edição, preenche o ID do produto
            if (!idField.getText().isEmpty()) {
                p.setIdProduto(Integer.parseInt(idField.getText()));
            }

            return p;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Preço e ID Restaurante devem ser números válidos.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void cadastrar() {
        Produto p = lerProdutoDosCampos();
        if (p == null) return;

        try {
            produtoService.cadastrarProduto(p);
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarProdutos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        Produto p = lerProdutoDosCampos();
        if (p == null || p.getIdProduto() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            produtoService.atualizarProduto(p);
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarProdutos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletar() {
        int r = tbl.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProduto = (int) model.getValueAt(r, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o produto " + idProduto + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                produtoService.deletarProduto(idProduto);
                JOptionPane.showMessageDialog(this, "Produto deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarProdutos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        precoField.setText("");
        descricaoField.setText("");
        idRestauranteField.setText("");
        tbl.clearSelection();
    }
}
