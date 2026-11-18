
package api_estoque;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {

    private final DataSource dataSource;
    private final ProdutoDAO produtoDAO;
    private final MovimentacaoDAO movimentacaoDAO;

    public MovimentacaoService(
            DataSource dataSource,
            ProdutoDAO produtoDAO,
            MovimentacaoDAO movimentacaoDAO) {

        this.dataSource = dataSource;
        this.produtoDAO = produtoDAO;
        this.movimentacaoDAO = movimentacaoDAO;
    }

    // Atualiza o estoque e registra o histórico na mesma transação.
    public void registrarMovimentacao(
            Integer idProduto, String tipo, Integer quantidade) {

        if (idProduto == null || quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException(
                "Informe um produto e uma quantidade válida."
            );
        }

        if (!"entrada".equalsIgnoreCase(tipo)
                && !"saída".equalsIgnoreCase(tipo)
                && !"saida".equalsIgnoreCase(tipo)) {

            throw new IllegalArgumentException(
                "Tipo de movimentação inválido."
            );
        }

        try (Connection conexao = dataSource.getConnection()) {

            conexao.setAutoCommit(false);

            try {
                Produto produto = produtoDAO.buscarPorId(conexao, idProduto);

                if (produto == null) {
                    throw new IllegalArgumentException(
                        "Produto não encontrado."
                    );
                }

                int estoqueAtual = produto.getQuantidade();
                int novoEstoque;

                if ("entrada".equalsIgnoreCase(tipo)) {
                    novoEstoque = Math.addExact(estoqueAtual, quantidade);
                } else {
                    if (quantidade > estoqueAtual) {
                        throw new IllegalArgumentException(
                            "Estoque insuficiente para essa saída."
                        );
                    }

                    novoEstoque = estoqueAtual - quantidade;
                }

                produtoDAO.atualizarQuantidade(
                    conexao, idProduto, novoEstoque
                );

                Movimentacao movimentacao = new Movimentacao(
                    null,
                    produto.getNome(),
                    tipo,
                    quantidade,
                    null
                );

                movimentacaoDAO.registrar(conexao, movimentacao);

                conexao.commit();

            } catch (Exception e) {

                try {
                    conexao.rollback();
                } catch (Exception erroRollback) {
                    e.addSuppressed(erroRollback);
                }

                if (e instanceof IllegalArgumentException) {
                    throw (IllegalArgumentException) e;
                }

                throw new RuntimeException(
                    "Não foi possível registrar a movimentação.", e
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Erro ao conectar com o banco de dados.", e
            );
        }
    }
}
