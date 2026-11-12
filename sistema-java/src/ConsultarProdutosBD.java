

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultarProdutosBD {

    public static void main(String[] args) {

        String sql = "SELECT * FROM produtos";

        try (
            Connection conexao =
              ConexaoMySQL.conectar();  
            PreparedStatement comando =
                conexao.prepareStatement(sql);

            ResultSet resultado = comando.executeQuery()
        ) {

            System.out.println("===== PRODUTOS EM ESTOQUE =====");

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                double preco = resultado.getDouble("preco");
                int quantidade = resultado.getInt("quantidade");

                System.out.println("------------------------------");
                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Preco: R$ " + preco);
                System.out.println("Quantidade: " + quantidade);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar produtos:");
            System.out.println(e.getMessage());
        }
    }
}
