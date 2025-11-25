package View;

import model.Produto;
import model.ItemPedido;
import model.Pedido;
import service.ProdutoService;
import service.PedidoService;
import Enum.StatusPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DecimalFormat;

public class ProdutoTelaCliente extends JFrame {
    private ProdutoService produtoService = new ProdutoService();
    private PedidoService pedidoService = new PedidoService();

    // Formato para exibir o valor total
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    // Colunas: ID, Nome, Preço, ID Restaurante
    private DefaultTableModel modelProdutos = new DefaultTableModel(new Object[]{"ID","Nome","Preço","Restaurante ID"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private JTable tblProdutos = new JTable(modelProdutos);
    private List<ItemPedido> carrinho = new ArrayList<>();
    private JLabel carrinhoLabel;

    public ProdutoTelaCliente() {
        setTitle("Cliente - Cardápio e Pedido");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titleLabel = new JLabel("Escolha seus Produtos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Tabela de Produtos
        mainPanel.add(new JScrollPane(tblProdutos), BorderLayout.CENTER);

        // Painel Inferior
        JPanel southPanel = new JPanel(new BorderLayout());

        // Label do Carrinho
        carrinhoLabel = new JLabel("Carrinho: 0 itens | Total: R$ 0.00", SwingConstants.LEFT);
        carrinhoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        southPanel.add(carrinhoLabel, BorderLayout.NORTH);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton adicionar = createStyledButton("Adicionar ao Carrinho", new Color(40, 180, 99));
        adicionar.addActionListener(e -> adicionarAoCarrinho());

        JButton verCarrinho = createStyledButton("Finalizar Pedido", new Color(60, 140, 255));
        verCarrinho.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O carrinho está vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new CriarPedidoDialog(this, carrinho, this).setVisible(true);
        });

        JButton voltar = createStyledButton("Voltar", new Color(200, 50, 50));
        voltar.addActionListener(e -> dispose());

        buttonPanel.add(adicionar);
        buttonPanel.add(verCarrinho);
        buttonPanel.add(voltar);

        southPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
        carregarProdutos();
        atualizarCarrinhoLabel();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void carregarProdutos() {
        try {
            modelProdutos.setRowCount(0);
            for (Produto p : produtoService.listarProdutos()) {
                // O método listarProdutos da sua service/dao deve retornar o ID do Restaurante.
                // Assumindo que o Produto tem o método getIdRestaurante()
                modelProdutos.addRow(new Object[]{p.getIdProduto(), p.getNome(), p.getPreco(), p.getIdRestaurante()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar produtos: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarAoCarrinho() {
        int r = tblProdutos.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para adicionar ao carrinho.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProduto = (int) modelProdutos.getValueAt(r, 0);
        String nome = (String) modelProdutos.getValueAt(r, 1);
        Object precoObj = modelProdutos.getValueAt(r, 2);
        double preco = precoObj instanceof Double ? (Double) precoObj : Double.parseDouble(precoObj.toString());
        int idRestaurante = (int) modelProdutos.getValueAt(r, 3);

        // Verifica se o carrinho está vazio ou se o restaurante é o mesmo
        if (!carrinho.isEmpty()) {
            int primeiroRestauranteId = carrinho.get(0).getIdRestaurante();
            if (idRestaurante != primeiroRestauranteId) {
                JOptionPane.showMessageDialog(this, "Você só pode pedir produtos de um único restaurante por vez.\nFinalize o pedido atual ou esvazie o carrinho.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String qtdStr = JOptionPane.showInputDialog(this, "Quantidade de " + nome + ":", "1");
        if (qtdStr == null) return;

        try {
            int qtd = Integer.parseInt(qtdStr);
            if (qtd <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica se o item já está no carrinho para apenas atualizar a quantidade
            boolean itemEncontrado = false;
            for (ItemPedido ip : carrinho) {
                if (ip.getIdProduto() == idProduto) {
                    ip.setQuantidade(ip.getQuantidade() + qtd);
                    itemEncontrado = true;
                    break;
                }
            }

            if (!itemEncontrado) {
                ItemPedido ip = new ItemPedido();
                ip.setIdProduto(idProduto);
                ip.setNomeProduto(nome);
                ip.setQuantidade(qtd);
                ip.setPreco(preco);
                ip.setIdRestaurante(idRestaurante); // Armazena o ID do restaurante no ItemPedido
                carrinho.add(ip);
            }

            JOptionPane.showMessageDialog(this, qtd + "x " + nome + " adicionado(s) ao carrinho.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarCarrinhoLabel();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarCarrinhoLabel() {
        int totalItens = carrinho.stream().mapToInt(ItemPedido::getQuantidade).sum();
        double valorTotal = carrinho.stream().mapToDouble(ip -> ip.getPreco() * ip.getQuantidade()).sum();
        carrinhoLabel.setText(String.format("Carrinho: %d itens | Total: R$ %s", totalItens, df.format(valorTotal)));
    }

    public void limparCarrinho() {
        carrinho.clear();
        atualizarCarrinhoLabel();
    }

    // Dialog interno para criar pedido, agora mais completo
    private class CriarPedidoDialog extends JDialog {
        private final List<ItemPedido> carrinho;
        private final ProdutoTelaCliente owner;
        private JTextArea resumoArea;
        private JTextField idClienteField;
        private JTextArea enderecoArea;
        private int idRestaurante;

        public CriarPedidoDialog(JFrame owner, List<ItemPedido> carrinho, ProdutoTelaCliente parent) {
            super(owner, "Finalizar Pedido", true);
            this.carrinho = carrinho;
            this.owner = parent;
            this.idRestaurante = carrinho.get(0).getIdRestaurante(); // Pega o ID do restaurante do primeiro item

            setSize(550, 500);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout(10, 10));

            // Painel Central: Resumo e Dados do Cliente
            JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Resumo do Pedido
            resumoArea = new JTextArea();
            resumoArea.setEditable(false);
            JScrollPane resumoScroll = new JScrollPane(resumoArea);
            resumoScroll.setBorder(BorderFactory.createTitledBorder("Resumo do Pedido (Restaurante ID: " + idRestaurante + ")"));
            centerPanel.add(resumoScroll);

            // Dados do Cliente
            JPanel dadosPanel = new JPanel(new BorderLayout(5, 5));
            dadosPanel.setBorder(BorderFactory.createTitledBorder("Dados para Finalização"));

            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            inputPanel.add(new JLabel("Seu ID de Cliente:"));
            idClienteField = new JTextField();
            inputPanel.add(idClienteField);

            dadosPanel.add(inputPanel, BorderLayout.NORTH);

            enderecoArea = new JTextArea(3, 20);
            enderecoArea.setLineWrap(true);
            enderecoArea.setWrapStyleWord(true);
            JScrollPane enderecoScroll = new JScrollPane(enderecoArea);





            centerPanel.add(dadosPanel);

            add(centerPanel, BorderLayout.CENTER);

            // Painel de Botões
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            JButton criar = createStyledButton("Confirmar e Criar Pedido", new Color(60, 140, 255));
            criar.addActionListener(e -> criarPedido());
            JButton cancelar = createStyledButton("Cancelar", new Color(200, 50, 50));
            cancelar.addActionListener(e -> dispose());

            buttonPanel.add(cancelar);
            buttonPanel.add(criar);
            add(buttonPanel, BorderLayout.SOUTH);

            // Carregar Resumo
            carregarResumo();
        }

        private void carregarResumo() {
            StringBuilder sb = new StringBuilder();
            double total = 0;
            for (ItemPedido ip : carrinho) {
                double subtotal = ip.getPreco() * ip.getQuantidade();
                sb.append(String.format("%-30s x%d = R$ %s\n", ip.getNomeProduto(), ip.getQuantidade(), df.format(subtotal)));
                total += subtotal;
            }
            sb.append("\n-------------------------------------------------\n");
            sb.append(String.format("TOTAL GERAL: R$ %s", df.format(total)));
            resumoArea.setText(sb.toString());
        }

        private void criarPedido() {
            try {
                int idCliente = Integer.parseInt(idClienteField.getText().trim());
                String endereco = enderecoArea.getText().trim();


                // 1. Criar o objeto Pedido
                Pedido pedido = new Pedido();
                pedido.setIdCliente(idCliente);
                pedido.setIdRestaurante(idRestaurante);
                pedido.setDataHora(new Date());
                pedido.setStatus(StatusPedido.EM_PREPARO);
                pedido.setEnderecoEntrega(endereco);

                // 2. Calcular o total e setar os itens
                double total = 0;
                List<ItemPedido> itensParaPedido = new ArrayList<>();
                for (ItemPedido ip : carrinho) {
                    total += ip.getPreco() * ip.getQuantidade();
                    // Cria uma cópia do ItemPedido para garantir que a lista do pedido seja independente
                    ItemPedido copia = new ItemPedido();
                    copia.setIdProduto(ip.getIdProduto());
                    copia.setQuantidade(ip.getQuantidade());
                    copia.setPreco(ip.getPreco());
                    copia.setNomeProduto(ip.getNomeProduto());
                    copia.setIdRestaurante(ip.getIdRestaurante()); // Mantém o ID do restaurante no item
                    itensParaPedido.add(copia);
                }
                pedido.setValorTotal(total);
                pedido.setItens(itensParaPedido);

                // 3. Registrar o pedido
                pedidoService.registrarPedido(pedido);

                JOptionPane.showMessageDialog(this, "Pedido criado com sucesso! ID: " + pedido.getIdPedido() + "\nTotal: R$ " + df.format(total), "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                // 4. Limpar o carrinho e fechar o dialog
                owner.limparCarrinho();
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de Cliente inválido. Deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao registrar pedido: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private JButton createStyledButton(String text, Color color) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setBackground(color);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            return button;
        }
    }
}
