
package api_estoque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDAO {

    private final DataSource dataSource;

    public ProdutoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private String validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do produto não pode ficar vazio."
            );
        }

        nome = nome.trim();

        if (nome.length() > 100) {
            throw new IllegalArgumentException(
                "O nome do produto deve ter no máximo 100 caracteres."
            );
        }

        return nome;
    }

    private void validarPreco(double preco) {

        if (preco <= 0) {
            throw new IllegalArgumentException(
                "O preço do produto deve ser maior que zero."
            );
        }
    }

    public List<Produto> listarTodos() {

        List<Produto> produtos = new ArrayList<>();

        String sql = "SELECT id, nome, preco, quantidade FROM produtos";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {

            while (resultado.next()) {

                Produto produto = new Produto(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getDouble("preco"),
                    resultado.getInt("quantidade")
                );

                produtos.add(produto);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "Erro ao consultar produtos no banco de dados.", e
            );
        }

        return produtos;
    }

    public void cadastrarProduto(Produto produto) {

        String nomeValidado = validarNome(produto.getNome());
        validarPreco(produto.getPreco());

        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setString(1, nomeValidado);
            comando.setDouble(2, produto.getPreco());
            comando.setInt(3, produto.getQuantidade());

            comando.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(
                "Erro ao cadastrar produto no banco de dados.", e
            );
        }
    }

    public void atualizarProduto(Produto produto) {

        String nomeValidado = validarNome(produto.getNome());
        validarPreco(produto.getPreco());

        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setString(1, nomeValidado);
            comando.setDouble(2, produto.getPreco());
            comando.setInt(3, produto.getQuantidade());
            comando.setInt(4, produto.getId());

            int linhasAfetadas = comando.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NoSuchElementException(
                    "Produto não encontrado."
                );
            }

        } catch (NoSuchElementException e) {
            throw e;

        } catch (Exception e) {
            throw new RuntimeException(
                "Erro ao atualizar produto no banco de dados.", e
            );
        }
    }

    public void atualizarQuantidade(
            Connection conexao, int id, int quantidade)
            throws SQLException {

        String sql = "UPDATE produtos SET quantidade = ? WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, quantidade);
            comando.setInt(2, id);

            int linhasAfetadas = comando.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException(
                    "Produto não encontrado para atualização."
                );
            }
        }
    }

    public Produto buscarPorId(Connection conexao, Integer id)
            throws SQLException {

        String sql = "SELECT id, nome, preco, quantidade FROM produtos WHERE id = ? FOR UPDATE";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {
                    return new Produto(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getDouble("preco"),
                        resultado.getInt("quantidade")
                    );
                }

                return null;
            }
        }
    }

    public void excluirProduto(int id) {

        String sql = "DELETE FROM produtos WHERE id = ?";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setInt(1, id);

            int linhasAfetadas = comando.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NoSuchElementException(
                    "Produto não encontrado."
                );
            }

        } catch (NoSuchElementException e) {
            throw e;

        } catch (Exception e) {
            throw new RuntimeException(
                "Erro ao excluir produto do banco de dados.", e
            );
        }
    }
}
