import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {

        try {

            Conexao conexao = Conexao.getInstancia();
            Connection conn = conexao.conectar();

            System.out.println("Conectado com sucesso ao PostgreSQL!");

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}