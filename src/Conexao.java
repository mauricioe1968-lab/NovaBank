//CONEXAO java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // ==========================================================
    // SINGLETON
    // ==========================================================

    private static Conexao instancia;

    // Construtor privado:
    // impede que outras classes façam new Conexao()
    private Conexao() {
    }

    // Retorna sempre a mesma instância da classe
    public static Conexao getInstancia() {

        if (instancia == null) {
            instancia = new Conexao();
        }

        return instancia;
    }

    // ==========================================================
    // CONFIGURAÇÃO DO BANCO
    // ==========================================================

    private static final String URL =
            "jdbc:postgresql://localhost:5432/javinha";

    private static final String USUARIO = "postgres";

    private static final String SENHA = "000116";

    // ==========================================================
    // CONEXÃO
    // ==========================================================

    public Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                SENHA
        );
    }
}

