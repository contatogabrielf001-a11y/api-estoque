
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class vendasDAO {

    public void registrarVenda(Connection conexao, String produto,
            int quantidade, double precoUnitario) throws SQLException {

        // Validar os dados da venda
        if (produto == null || produto.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto inválido.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero.");
        }

        if (precoUnitario <= 0) {
            throw new IllegalArgumentException(
                    "O preço deve ser maior que zero.");
        }

        String sql = "INSERT INTO vendas "
                + "(produto, quantidade, preco_unitario, valor_total) "
                + "VALUES (?, ?, ?, ?)";

        double valorTotal = quantidade * precoUnitario;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, produto.trim());
            comando.setInt(2, quantidade);
            comando.setDouble(3, precoUnitario);
            comando.setDouble(4, valorTotal);

            comando.executeUpdate();
        }
    }

    public void listarVendas() throws SQLException {

        String sql = "SELECT id, produto, quantidade, preco_unitario, "
                + "valor_total, data_venda FROM vendas ORDER BY id DESC";

        try (
                Connection conexao = ConexaoMySQL.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {

            boolean encontrouVenda = false;

            System.out.println("======= HISTÓRICO DE VENDAS =======");

            while (resultado.next()) {

                encontrouVenda = true;

                System.out.println("ID da venda: "
                        + resultado.getInt("id"));

                System.out.println("Produto: "
                        + resultado.getString("produto"));

                System.out.println("Quantidade vendida: "
                        + resultado.getInt("quantidade"));

                System.out.printf("Preço unitário: R$ %.2f%n",
                        resultado.getDouble("preco_unitario"));

                System.out.printf("Valor total: R$ %.2f%n",
                        resultado.getDouble("valor_total"));

                System.out.println("Data da venda: "
                        + resultado.getTimestamp("data_venda"));

                System.out.println("----------------------------------");
            }

            if (!encontrouVenda) {
                System.out.println("Nenhuma venda registrada.");
            }
        }
    }
}
