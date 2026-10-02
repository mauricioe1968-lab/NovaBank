import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BancoFacade {

    private final Conexao conexao;

    public BancoFacade() {
        this.conexao = Conexao.getInstancia();
    }

    // ==========================================================
    // DEPÓSITO
    // ==========================================================

    public boolean depositar(Conta conta, Double valor) {

        if (conta == null || valor == null || valor <= 0) {
            return false;
        }

        String sqlSaldo = """
                UPDATE conta
                SET saldo = saldo + ?
                WHERE id_conta = ?
                """;

        String sqlTransacao = """
                INSERT INTO transacao
                (conta_origem, conta_destino, tipo_operacao, valor)
                VALUES (NULL, ?, 'DEPOSITO', ?)
                """;

        try (Connection conn = conexao.conectar()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement stmtSaldo =
                        conn.prepareStatement(sqlSaldo);

                PreparedStatement stmtTransacao =
                        conn.prepareStatement(sqlTransacao)
            ) {

                stmtSaldo.setDouble(1, valor);
                stmtSaldo.setInt(2, conta.getIdConta());

                int linhas = stmtSaldo.executeUpdate();

                if (linhas == 0) {
                    conn.rollback();
                    return false;
                }

                stmtTransacao.setInt(1, conta.getIdConta());
                stmtTransacao.setDouble(2, valor);
                stmtTransacao.executeUpdate();

                conn.commit();

                return true;

            } catch (SQLException e) {

                conn.rollback();
                System.out.println("Erro no depósito: " + e.getMessage());

                return false;
            }

        } catch (SQLException e) {

            System.out.println("Erro de conexão: " + e.getMessage());
            return false;
        }
    }

    // ==========================================================
    // SAQUE
    // ==========================================================

    public boolean sacar(Conta conta, Double valor) {

        if (conta == null || valor == null || valor <= 0) {
            return false;
        }

        String sqlSaldo = """
                UPDATE conta
                SET saldo = saldo - ?
                WHERE id_conta = ?
                AND saldo >= ?
                """;

        String sqlTransacao = """
                INSERT INTO transacao
                (conta_origem, conta_destino, tipo_operacao, valor)
                VALUES (?, NULL, 'SAQUE', ?)
                """;

        try (Connection conn = conexao.conectar()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement stmtSaldo =
                        conn.prepareStatement(sqlSaldo);

                PreparedStatement stmtTransacao =
                        conn.prepareStatement(sqlTransacao)
            ) {

                stmtSaldo.setDouble(1, valor);
                stmtSaldo.setInt(2, conta.getIdConta());
                stmtSaldo.setDouble(3, valor);

                int linhas = stmtSaldo.executeUpdate();

                if (linhas == 0) {
                    conn.rollback();
                    return false;
                }

                stmtTransacao.setInt(1, conta.getIdConta());
                stmtTransacao.setDouble(2, valor);
                stmtTransacao.executeUpdate();

                conn.commit();

                return true;

            } catch (SQLException e) {

                conn.rollback();
                System.out.println("Erro no saque: " + e.getMessage());

                return false;
            }

        } catch (SQLException e) {

            System.out.println("Erro de conexão: " + e.getMessage());
            return false;
        }
    }

    // ==========================================================
    // TRANSFERÊNCIA
    // ==========================================================

    public boolean transferir(
            Conta origem,
            Conta destino,
            Double valor) {

        if (origem == null || destino == null) {
            return false;
        }

        if (origem.getIdConta() == destino.getIdConta()) {
            return false;
        }

        if (valor == null || valor <= 0) {
            return false;
        }

        if (valor > origem.limiteTransferencia()) {
            return false;
        }

        String sqlOrigem = """
                UPDATE conta
                SET saldo = saldo - ?
                WHERE id_conta = ?
                AND saldo >= ?
                """;

        String sqlDestino = """
                UPDATE conta
                SET saldo = saldo + ?
                WHERE id_conta = ?
                """;

        String sqlTransacao = """
                INSERT INTO transacao
                (conta_origem, conta_destino, tipo_operacao, valor)
                VALUES (?, ?, 'TRANSFERENCIA', ?)
                """;

        try (Connection conn = conexao.conectar()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement stmtOrigem =
                        conn.prepareStatement(sqlOrigem);

                PreparedStatement stmtDestino =
                        conn.prepareStatement(sqlDestino);

                PreparedStatement stmtTransacao =
                        conn.prepareStatement(sqlTransacao)
            ) {

                // Retira o valor da conta de origem
                stmtOrigem.setDouble(1, valor);
                stmtOrigem.setInt(2, origem.getIdConta());
                stmtOrigem.setDouble(3, valor);

                int linhasOrigem = stmtOrigem.executeUpdate();

                if (linhasOrigem == 0) {
                    conn.rollback();
                    return false;
                }

                // Adiciona o valor na conta de destino
                stmtDestino.setDouble(1, valor);
                stmtDestino.setInt(2, destino.getIdConta());

                int linhasDestino = stmtDestino.executeUpdate();

                if (linhasDestino == 0) {
                    conn.rollback();
                    return false;
                }

                // Registra a transferência
                stmtTransacao.setInt(1, origem.getIdConta());
                stmtTransacao.setInt(2, destino.getIdConta());
                stmtTransacao.setDouble(3, valor);

                stmtTransacao.executeUpdate();

                conn.commit();

                return true;

            } catch (SQLException e) {

                conn.rollback();
                System.out.println(
                        "Erro na transferência: "
                        + e.getMessage()
                );

                return false;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erro de conexão: "
                    + e.getMessage()
            );

            return false;
        }
    
    }
}

