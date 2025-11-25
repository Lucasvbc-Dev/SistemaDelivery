package service;
import dao.EntregadorDAO;
import model.Entregador;
import java.sql.SQLException;
import java.util.List;

public class EntregadorService {
    private EntregadorDAO entregadorDAO;


    public EntregadorService(){
        this.entregadorDAO = new EntregadorDAO();
    }

    public void cadastrarEntregador(Entregador entregador) throws SQLException{
        if(entregador.getNome() == null || entregador.getNome().isEmpty()){
            throw new IllegalArgumentException("Nome do entregador é obrigatório");
        }
        if (entregador.getTelefone() == null || entregador.getTelefone().isEmpty()){
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        if (entregador.getTipoVeiculo() == null || entregador.getTipoVeiculo().isEmpty()){
            throw new IllegalArgumentException("Tipo do veículo do entregador é obrigatório");
        }
        entregadorDAO.inserir(entregador);
    }

    public List<Entregador> listarEntregadores() throws SQLException{
        return entregadorDAO.listar();
    }

    public void atualizarEntregador(Entregador entregador) throws SQLException{
        if(entregador.getIdEntregador() <= 0){
            throw new IllegalArgumentException(".ID do entregador inválido");
        }
        entregadorDAO.atualizar(entregador);
    }

    public void deletarEntregador(int idEntregador) throws SQLException{
        if(idEntregador <= 0){
            throw new IllegalArgumentException("ID do entregador inválido");
        }
        entregadorDAO.deletar(idEntregador);
    }

    // Em service/EntregadorService.java

    public Entregador buscarEntregadorPorId(int idEntregador) throws SQLException {
        if (idEntregador <= 0) {
            throw new IllegalArgumentException("ID do entregador inválido.");
        }
        return entregadorDAO.buscarPorId(idEntregador);
    }

}
