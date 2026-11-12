
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastrarProdutoBD {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CADASTRO DE PRODUTO ===");

        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o preco do produto: ");
        double preco = scanner.nextDouble();

        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();

        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";

        try (
            Connection conexao = ConexaoMySQL.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setString(1, nome);
            comando.setDouble(2, preco);
            comando.setInt(3, quantidade);

            comando.executeUpdate();

            System.out.println("\nProduto cadastrado com sucesso!");

        } catch (SQLException e) {

            System.out.println("Erro ao cadastrar produto.");
            System.out.println("Detalhes: " + e.getMessage());

        }

        scanner.close();
    }
}