
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {

public void cadastrarProduto(Produto produto) throws SQLException {
    String sql = "INSERT INTO produtos (nome, preco, quantidade, estoque_minimo) VALUES (?, ?, ?, ?)";

    try (
        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ) {
        comando.setString(1, produto.nome);
        comando.setDouble(2, produto.preco);
        comando.setInt(3, produto.quantidade);
        comando.setInt(4, produto.estoqueMinimo);

        comando.executeUpdate();

        System.out.println("Produto cadastrado no MySQL com sucesso!");
    }

}

public boolean existeProduto(String nome) throws SQLException{
    String sql = "SELECT COUNT(*) FROM produtos WHERE LOWER(nome) = LOWER(?)";

    try(
        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ){
        comando.setString(1, nome. trim());

        try(ResultSet resultado = comando.executeQuery()){
            resultado.next();
            return resultado.getInt(1) > 0;
        }
    }
  }

public boolean existeOutroProduto(String nome, int id) throws SQLException {

    String sql = "SELECT COUNT(*) FROM produtos "
            + "WHERE LOWER(nome) = LOWER(?) AND id <> ?";

    try (
        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ) {
        comando.setString(1, nome.trim());
        comando.setInt(2, id);

        try (ResultSet resultado = comando.executeQuery()) {
            resultado.next();
            return resultado.getInt(1) > 0;
        }
    }
}

public ArrayList<Produto> listarProdutos() throws SQLException {
        ArrayList<Produto> produtos = new ArrayList<>();

        String sql = "SELECT id, nome, preco, quantidade, estoque_minimo FROM produtos";

        try (
            Connection conexao = ConexaoMySQL.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                double preco = resultado.getDouble("preco");
                int quantidade = resultado.getInt("quantidade");
                int estoqueMinimo = resultado.getInt("estoque_minimo");

                Produto produto = new Produto(
                  id, nome, preco, quantidade, estoqueMinimo
                 );

                produtos.add(produto);
            }
        }

        return produtos;
    }
public ArrayList<Produto> pesquisarProduto(String nome) throws SQLException{
    ArrayList<Produto> produtos = new ArrayList<>();

    String sql = "SELECT id, nome, preco, quantidade, estoque_minimo " +
             "FROM produtos WHERE LOWER(nome) LIKE LOWER(?)";

    try(
        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ) {
        comando.setString(1, "%" + nome.trim() + "%");

        try(ResultSet resultado = comando.executeQuery()){
            while(resultado.next()){
                 Produto produto = new Produto(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getDouble("preco"),
                    resultado.getInt("quantidade"),
                    resultado.getInt("estoque_minimo")
                );

                produtos.add(produto);
            }
        }
    }

    return produtos;
}
public boolean excluirProduto(int id ) throws SQLException{
    String sql = "DELETE FROM produtos WHERE id = ?";

    try(
        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql)
    ){
        comando.setInt(1,id);

        int linhasAfetadas = comando.executeUpdate();

        return linhasAfetadas > 0;
    }
}
public boolean editarProduto(Produto produto) throws SQLException{
    String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ?, estoque_minimo = ? WHERE id = ?";

    try(
        Connection conexao = ConexaoMySQL.conectar();
        PreparedStatement comando = conexao.prepareStatement(sql)){
        
            comando.setString(1, produto.nome);
            comando.setDouble(2, produto.preco);
            comando.setInt(3, produto.quantidade);
            comando.setInt(4, produto.estoqueMinimo);
            comando.setInt(5, produto.id);

            int linhasAfetadas = comando.executeUpdate();

            return linhasAfetadas > 0;


        }
    
}
public boolean registrarVenda(Connection conexao, int id, int quantidadeVendida)
        throws SQLException {

    String sql = "UPDATE produtos "
            + "SET quantidade = quantidade - ? "
            + "WHERE id = ? AND quantidade >= ?";

    try (PreparedStatement comando = conexao.prepareStatement(sql)) {

        comando.setInt(1, quantidadeVendida);
        comando.setInt(2, id);
        comando.setInt(3, quantidadeVendida);

        int linhasAfetadas = comando.executeUpdate();

        return linhasAfetadas > 0;
    }
}
}


