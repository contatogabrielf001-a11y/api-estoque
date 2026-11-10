package api_estoque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDAO {

    private final DataSource dataSource;

    public ProdutoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

 // Método responsável por consultar e listar todos os produtos cadastrados no banco de dados.

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
            throw new RuntimeException("Erro ao consultar produtos no banco de dados.", e);
        }

        return produtos;
    }

 // Método responsável por cadastrar um novo produto no banco de dados.

    public void cadastrarProduto(Produto produto) {

        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";

        try (
            Connection conexao = dataSource.getConnection();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setString(1, produto.getNome());
            comando.setDouble(2, produto.getPreco());
            comando.setInt(3, produto.getQuantidade());

            comando.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar produto no banco de dados.", e);
        }
    }

 // Método responsável por atualizar os dados de um produto pelo ID.
public void atualizarProduto(Produto produto) {

    String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";

    try (
        Connection conexao = dataSource.getConnection();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ) {

        comando.setString(1, produto.getNome());
        comando.setDouble(2, produto.getPreco());
        comando.setInt(3, produto.getQuantidade());
        comando.setInt(4, produto.getId());

        comando.executeUpdate();

    } catch (Exception e) {
        throw new RuntimeException("Erro ao atualizar produto no banco de dados.", e);
    }
}

//Método responsavel por excluir um produto pelo ID.
public void excluirProduto(int id) {

    String sql = "DELETE FROM produtos WHERE id = ?";

    try (
        Connection conexao = dataSource.getConnection();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ) {

        comando.setInt(1, id);

        comando.executeUpdate();

    } catch (Exception e) {
        throw new RuntimeException("Erro ao excluir produto do banco de dados.", e);
    }
}

}