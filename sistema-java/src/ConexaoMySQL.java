
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    public static Connection conectar() throws SQLException {

        String url = System.getenv("DB_URL");
        String usuario = System.getenv("DB_USER");
        String senha = System.getenv("DB_PASSWORD");

        if (url == null || usuario == null || senha == null) {
            throw new SQLException(
                "Variáveis de ambiente do MySQL não configuradas."
            );
        }

        return DriverManager.getConnection(url, usuario, senha);
    }
}
