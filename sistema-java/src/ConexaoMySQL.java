
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

   public static Connection conectar() throws SQLException {

    String url = System.getenv("DB_URL");
    String usuario = System.getenv("DB_USER");
    String senha = System.getenv("DB_PASSWORD");

    if (url == null || url.isBlank()) {
        url = "jdbc:mysql://localhost:3306/controle_estoque";
    }

    if (usuario == null || usuario.isBlank()) {
        usuario = "estoque_app";
    }

    if (senha == null || senha.isBlank()) {
        throw new SQLException(
            "A variável DB_PASSWORD não está configurada."
        );
    }

    return DriverManager.getConnection(url, usuario, senha);
}
}
