package service;

import dao.ProdutoDAO;
import model.Produto;
import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void cadastrarProduto(Produto produto) throws SQLException {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        // CORREÇÃO: Garante que o ID do Restaurante seja validado
        if (produto.getIdRestaurante() <= 0) {
            throw new IllegalArgumentException("O produto deve estar vinculado a um restaurante!");
        }
        produtoDAO.inserir(produto);
    }

    public void atualizarProduto(Produto produto) throws SQLException {
        if (produto.getIdProduto() <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        // CORREÇÃO: Garante que o ID do Restaurante seja validado
        if (produto.getIdRestaurante() <= 0) {
            throw new IllegalArgumentException("O produto deve estar vinculado a um restaurante!");
        }
        produtoDAO.atualizar(produto);
    }

    public void deletarProduto(int idProduto) throws SQLException {
        if (idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        produtoDAO.deletar(idProduto);
    }

    public Produto buscarProdutoPorId(int idProduto) throws SQLException {
        if (idProduto <= 0) {
            throw new IllegalArgumentException("ID do produto inválido.");
        }
        return produtoDAO.buscarPorId(idProduto);
    }

    public List<Produto> listarProdutos() throws SQLException {
        return produtoDAO.listar();
    }
}
