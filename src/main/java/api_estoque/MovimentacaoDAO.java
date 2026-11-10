package api_estoque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class MovimentacaoDAO {

    private final DataSource dataSource;

    public MovimentacaoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método responsável por consultar o histórico de movimentações.
    public List<Movimentacao> listarTodas() {

        List<Movimentacao> movimentacoes = new ArrayList<>();

        String sql = "SELECT id, nome_produto, tipo, quantidade, " +
                     "DATE_FORMAT(data_movimentacao, '%d/%m/%Y %H:%i:%s') AS data " +
                     "FROM movimentacoes ORDER BY data_movimentacao DESC";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {

            while (resultado.next()) {

                Movimentacao movimentacao = new Movimentacao(
                    resultado.getInt("id"),
                    resultado.getString("nome_produto"),
                    resultado.getString("tipo"),
                    resultado.getInt("quantidade"),
                    resultado.getString("data")
                );

                movimentacoes.add(movimentacao);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar movimentações.", e);
        }

        return movimentacoes;
    }

// Método responsável por registrar uma movimentação no banco de dados.
    public void registrar(Movimentacao movimentacao) {

        String sql = "INSERT INTO movimentacoes " +
                     "(nome_produto, tipo, quantidade) VALUES (?, ?, ?)";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setString(1, movimentacao.getNomeProduto());
            comando.setString(2, movimentacao.getTipo());
            comando.setInt(3, movimentacao.getQuantidade());

            comando.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar movimentação.", e);
        }
    }

// Método responsável por excluir todo o histórico de movimentações.
    public void excluirTodas() {

        String sql = "DELETE FROM movimentacoes";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir movimentações.", e);
        }
    }
}