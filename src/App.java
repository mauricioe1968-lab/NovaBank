// Este faz a execução de todo o programa.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class App {

    // CORES ANSI
    public static final String RESET = "\u001B[0m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARELO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String CIANO = "\u001B[36m";
    public static final String NEGRITO = "\u001B[1m";
    

    public static void pausa(Scanner scanner) {
        System.out.println("\nPressione UMA TECLA QUALQUER para Continuar");
        scanner.nextLine();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Drivers carregados:");
        System.out.println("Tentando conectar...");
        java.util.Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement().getClass().getName());
        }

        Scanner scanner = new Scanner(System.in);
        
        //Colocando o Factory - Criamos um Objeto da nossaFactory
        ContaFactory factory = new ContaFactory();
        BancoFacade banco = new BancoFacade();

        while (true) {
            System.out.println();
            System.out.println(NEGRITO + AMARELO + "           NOVA BANK S/A. " + RESET);
            System.out.println("------------------------------------------");
            System.out.println(CIANO + "          Seja Bem Vindo" + RESET);
            System.out.println(VERDE + "\nESCOLHA UMA OPCAO DO MENU" + RESET);
            System.out.println("------------------------------------------");
            
            System.out.println(AZUL
                    + "1 - Cadastro Conta\n"
                    + "2 - Consultar Saldo\n"
                    + "3 - Extrato\n"
                    + "4 - Realizar Transferencia\n"
                    + "5 - Realizar Saque\n"
                    + "6 - Fazer deposito\n"
                    + "0 - Encerrar"
                    + RESET);
            System.out.println(AMARELO + "Digite abaixo sua opcao:" + RESET);
            
            String opcao = scanner.nextLine().trim();


            if (opcao.equals("0")) {
                System.out.println(AMARELO + "Encerrando programa, OTIMO DIA!");
                break;
            }
        
            // ******************** Cadastro Conta ***************************************************************
        
            if (opcao.equals("1")) {

                System.out.println(
                        "Deseja Cadastar uma Pessoa Fisica ou Juridica\n1 - Pessoa Fisica\n2 - Pessoa Juridica");
                String tipoConta = scanner.nextLine();

                if (tipoConta.equals("1")) {

                    pessoaFisica nova = (pessoaFisica) factory.criarConta("PF");

                    nova.setTipoConta("PF");

                    System.out.println("Qual Seu nome?\n");
                    String nome = scanner.nextLine();
                    nova.setTitular(nome);

                    System.out.println("Qual Seu Cpf?   ( Digite os 11 numeros )\n");
                    String cpf = scanner.nextLine();
                    nova.setCpf(cpf);

                    System.out.println("Data de Nascimento");
                    System.out.println(AMARELO + "Formato obrigatório: DD-MM-AAAA (Ex.: 01-08-2000)" + RESET);
                    String nascimento = scanner.nextLine();
                    nova.setDataNascimento(nascimento);

                    // ADICIONANDO AO POSTGRESQL +++++++++++++++

                
                    System.out.println("Entrou no cadastro PostgreSQL...");
                    try {

                        // Aqui vamos verificar se gravou no Banco de Dados.*******8
                        Conexao conexao = Conexao.getInstancia();
                        Connection conn = conexao.conectar();

                        System.out.println("Conectou ao banco!");
                        String sqlCliente = "INSERT INTO cliente (nome, tipo_cliente, cpf, data_nascimento) VALUES (?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(
                                sqlCliente,
                                java.sql.Statement.RETURN_GENERATED_KEYS);

                        stmt.setString(1, nova.getTitular());
                        stmt.setString(2, "PF");
                        stmt.setString(3, nova.getCpf());
                        String[] partesData = nova.getDataNascimento().split("-");

                        java.sql.Date dataNascimento = java.sql.Date.valueOf(
                                partesData[2] + "-"
                                + partesData[1] + "-"
                                + partesData[0]);
                        
                        stmt.setDate(4, dataNascimento);

                        stmt.executeUpdate();
                        System.out.println("Executou o INSERT!");
                        java.sql.ResultSet rs = stmt.getGeneratedKeys();

                        int idCliente = 0;

                        if (rs.next()) {
                            idCliente = rs.getInt(1);
                        }

                        System.out.println("ID do cliente: " + idCliente);


                        // ================= CADASTRO DA CONTA =================

                        
                        String sqlConta = """
                                INSERT INTO conta
                                (agencia, saldo, id_cliente)
                                VALUES (?, ?, ?)
                                RETURNING numero_conta
                                """;

                        PreparedStatement stmtConta = conn.prepareStatement(sqlConta);

                        stmtConta.setString(1, nova.getAgencia());
                        stmtConta.setDouble(2, nova.getSaldo());
                        stmtConta.setInt(3, idCliente);

                        java.sql.ResultSet rsConta = stmtConta.executeQuery();

                        int numeroConta = 0;

                        if (rsConta.next()) {
                            numeroConta = rsConta.getInt("numero_conta");
                        }

                        nova.setNumeroConta(numeroConta);

                        System.out.println("Conta gravada no PostgreSQL!");
                        System.out.println("Número da conta criada: " + numeroConta);

                        stmtConta.close();

                        stmt.close();
                        conn.close();

                        System.out.println("Cliente gravado no PostgreSQL!");

                    } catch (Exception e) {

                        System.out.println("Erro no PostgreSQL:");
                        e.printStackTrace();

                    }

                    System.out.println(VERDE
                            + "\n========================================\n"
                            + "     CONTA CADASTRADA COM SUCESSO!\n"
                            + "     Número da conta: " + nova.getNumeroConta() + "\n"
                            + "========================================"
                            + RESET);

                    pausa(scanner);

                }

                // *********** CADASTRO DA CONT PJ *************************************************

                if (tipoConta.equals("2")) {

                    pessoaJuridica nova = (pessoaJuridica) factory.criarConta("PJ");
                
                    nova.setTipoConta("PJ");
                
                    System.out.println("Qual nome da sua empresa");
                    String nome = scanner.nextLine();
                    nova.setTitular(nome);
                
                    System.out.println("Informe seu CNPJ:");
                    String cnpj = scanner.nextLine();
                    nova.setCnpj(cnpj);
                
                    System.out.println("Data de constituição");
                    System.out.println(
                            AMARELO
                            + "Formato obrigatório: DD-MM-AAAA (Ex.: 01-10-2000)"
                            + RESET);
                
                    String constituicao = scanner.nextLine();
                    nova.setDataConstituicao(constituicao);
                
                    System.out.println("Entrou no cadastro PostgreSQL...");
                
                    try {
                
                        // ================= CONEXÃO =================
                
                        Conexao conexao = Conexao.getInstancia();
                        Connection conn = conexao.conectar();
                
                        System.out.println("Conectou ao banco!");
                
                        // ================= CADASTRO DO CLIENTE PJ =================
                
                        String sqlCliente = """
                                INSERT INTO cliente
                                (nome, tipo_cliente, cnpj, data_constituicao)
                                VALUES (?, ?, ?, ?)
                                """;
                
                        PreparedStatement stmt = conn.prepareStatement(
                                sqlCliente,
                                java.sql.Statement.RETURN_GENERATED_KEYS);
                
                        stmt.setString(1, nova.getTitular());
                        stmt.setString(2, "PJ");
                        stmt.setString(3, nova.getCnpj());
                        String[] partesData = nova.getDataConstituicao().split("-");

                        java.sql.Date dataConstituicao = java.sql.Date.valueOf(
                                partesData[2] + "-"
                                + partesData[1] + "-"
                                + partesData[0]);
                        
                        stmt.setDate(4, dataConstituicao);
                
                        stmt.executeUpdate();
                
                        System.out.println("Cliente PJ gravado!");
                
                        // ================= PEGAR ID DO CLIENTE =================
                
                        java.sql.ResultSet rs = stmt.getGeneratedKeys();
                
                        int idCliente = 0;
                
                        if (rs.next()) {
                            idCliente = rs.getInt(1);
                        }
                
                        System.out.println("ID do cliente: " + idCliente);
                
                        // ================= CADASTRO DA CONTA =================
                
                        String sqlConta = """
                                INSERT INTO conta
                                (agencia, saldo, id_cliente)
                                VALUES (?, ?, ?)
                                RETURNING numero_conta
                                """;
                
                        PreparedStatement stmtConta =
                                conn.prepareStatement(sqlConta);
                
                        stmtConta.setString(1, nova.getAgencia());
                        stmtConta.setDouble(2, nova.getSaldo());
                        stmtConta.setInt(3, idCliente);
                
                        java.sql.ResultSet rsConta =
                                stmtConta.executeQuery();
                
                        int numeroConta = 0;
                
                        if (rsConta.next()) {
                            numeroConta =
                                    rsConta.getInt("numero_conta");
                        }
                
                        nova.setNumeroConta(numeroConta);
                
                        System.out.println("Conta gravada no PostgreSQL!");
                        System.out.println(
                                "Número da conta criada: "
                                + numeroConta);
                
                        // ================= FECHANDO =================
                
                        rs.close();
                        rsConta.close();
                        stmt.close();
                        stmtConta.close();
                        conn.close();
                
                        // ================= MENSAGEM =================
                
                        System.out.println(
                                VERDE
                                + "\n========================================\n"
                                + "     CONTA PJ CADASTRADA COM SUCESSO!\n"
                                + "     Número da conta: "
                                + nova.getNumeroConta()
                                + "\n"
                                + "========================================"
                                + RESET);
                
                        pausa(scanner);
                
                    } catch (Exception e) {
                
                        System.out.println(
                                VERMELHO
                                + "Erro no PostgreSQL:"
                                + RESET);
                
                        e.printStackTrace();
                    }
                } 
        }
    //   ********************* CONSULTA SALDO ************************
            if (opcao.equals("2")) {

    System.out.println("Digite o numero da conta:");

    int numeroConta = scanner.nextInt();
    scanner.nextLine();

    String sqlSaldo = """
            SELECT c.numero_conta, c.saldo, cl.nome
            FROM conta c
            INNER JOIN cliente cl
                ON cl.id_cliente = c.id_cliente
            WHERE c.numero_conta = ?
            """;

    try {
        Conexao conexao = Conexao.getInstancia();
        Connection conn = conexao.conectar();

        PreparedStatement stmt = conn.prepareStatement(sqlSaldo);

        stmt.setInt(1, numeroConta);

        java.sql.ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String titular = rs.getString("nome");
            double saldo = rs.getDouble("saldo");

            System.out.println("------------------------------------------");
            System.out.println("Conta: " + rs.getInt("numero_conta"));
            System.out.println("Titular: " + titular);

            if (saldo < 0) {
                System.out.println(
                        VERMELHO + "Saldo: " + saldo + RESET);
            } else {
                System.out.println(
                        AZUL + "Saldo: " + saldo + RESET);
            }

            System.out.println("------------------------------------------");

        } else {

            System.out.println(
                    VERMELHO
                    + "Conta não encontrada no PostgreSQL!"
                    + RESET);
        }

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception e) {

        System.out.println(
                VERMELHO
                + "Erro ao consultar o PostgreSQL:"
                + RESET);

        e.printStackTrace();
    }

    pausa(scanner);
}
           
// *********************** E X T R A T O ************************

if (opcao.equals("3")) {

    System.out.println();
    System.out.println(AZUL + "========================================" + RESET);
    System.out.println(NEGRITO + "              EXTRATO" + RESET);
    System.out.println(AZUL + "========================================" + RESET);

    System.out.println("Digite o numero da conta:");
    int numeroConta = scanner.nextInt();
    scanner.nextLine();

    System.out.println();
    System.out.println("Escolha o período:");
    System.out.println("1 - Últimos 15 dias");
    System.out.println("2 - Últimos 30 dias");
    System.out.println("3 - Últimos 60 dias");
    System.out.println("0 - Voltar");
    System.out.print("Digite sua opção: ");

    String opcaoPeriodo = scanner.nextLine().trim();

    int periodoDias;

    switch (opcaoPeriodo) {

        case "1":
            periodoDias = 15;
            break;

        case "2":
            periodoDias = 30;
            break;

        case "3":
            periodoDias = 60;
            break;

        case "0":
            System.out.println("Voltando ao menu principal...");
            continue;

        default:
            System.out.println(
                    VERMELHO
                            + "Opção de período inválida!"
                            + RESET);

            pausa(scanner);
            continue;
    }

    String sqlExtrato = """
            SELECT
                t.id_transacao,
                t.tipo_operacao,
                t.valor,
                t.data_operacao,
                t.conta_origem,
                t.conta_destino,
                CASE
                    WHEN t.conta_destino = c.id_conta THEN 'ENTRADA'
                    WHEN t.conta_origem = c.id_conta THEN 'SAIDA'
                END AS movimentacao
            FROM transacao t
            JOIN conta c
                ON c.id_conta = t.conta_origem
                OR c.id_conta = t.conta_destino
            WHERE c.numero_conta = ?
              AND t.data_operacao >=
                  CURRENT_TIMESTAMP - (? * INTERVAL '1 day')
            ORDER BY t.data_operacao DESC
            """;

    try {

        Conexao conexao = Conexao.getInstancia();
        Connection conn = conexao.conectar();

        PreparedStatement stmt =
                conn.prepareStatement(sqlExtrato);

        stmt.setInt(1, numeroConta);
        stmt.setInt(2, periodoDias);

        java.sql.ResultSet rs =
                stmt.executeQuery();

        System.out.println();
        System.out.println(
                AZUL
                        + "======================================================"
                        + RESET);

        System.out.println(
                NEGRITO
                        + "                 EXTRATO NOVABANK"
                        + RESET);

        System.out.println(
                AZUL
                        + "======================================================"
                        + RESET);

        System.out.println("Conta: " + numeroConta);
        System.out.println("Período: últimos " + periodoDias + " dias");

        System.out.println(
                "------------------------------------------------------");

        boolean encontrou = false;

        while (rs.next()) {

            encontrou = true;

            String tipo =
                    rs.getString("tipo_operacao");

            double valor =
                    rs.getDouble("valor");

            String movimentacao =
                    rs.getString("movimentacao");

            System.out.println(
                    "Data: "
                            + rs.getTimestamp("data_operacao"));

            System.out.println(
                    "Operação: "
                            + tipo);

            System.out.println(
                    "Movimentação: "
                            + movimentacao);

            if ("ENTRADA".equals(movimentacao)) {

                System.out.println(
                        VERDE
                                + "Valor: + R$ "
                                + String.format("%.2f", valor)
                                + RESET);

            } else {

                System.out.println(
                        VERMELHO
                                + "Valor: - R$ "
                                + String.format("%.2f", valor)
                                + RESET);
            }

            System.out.println(
                    "Conta origem: "
                            + rs.getObject("conta_origem"));

            System.out.println(
                    "Conta destino: "
                            + rs.getObject("conta_destino"));

            System.out.println(
                    "------------------------------------------------------");
        }

        if (!encontrou) {

            System.out.println(
                    AMARELO
                            + "Nenhuma movimentação encontrada "
                            + "nos últimos "
                            + periodoDias
                            + " dias."
                            + RESET);
        }

        System.out.println(
                AZUL
                        + "======================================================"
                        + RESET);

        rs.close();
        stmt.close();
        conn.close();

    } catch (Exception e) {

        System.out.println(
                VERMELHO
                        + "Erro ao consultar o extrato no PostgreSQL:"
                        + RESET);

        e.printStackTrace();
    }

    pausa(scanner);

} // Fecha a opção 3 - Extrato
    

// ************************ REALIZAR TRANSFERÊNCIA *******************
// *******************************************************************

if (opcao.equals("4")) {

    System.out.println("Qual o numero da sua conta:");
    int contaOrigemNumero = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Para qual conta deseja transferir:");
    int contaDestinoNumero = scanner.nextInt();
    scanner.nextLine();

    if (contaOrigemNumero == contaDestinoNumero) {

        System.out.println(
                VERMELHO
                        + "Não é possível transferir para a mesma conta!"
                        + RESET);

        pausa(scanner);
        continue;
    }

    System.out.println("Qual o valor da transferencia:");
    double valor = scanner.nextDouble();
    scanner.nextLine();

    if (valor <= 0) {

        System.out.println(
                VERMELHO
                        + "O valor da transferência deve ser maior que zero!"
                        + RESET);

        pausa(scanner);
        continue;
    }

    try {

        // ==========================================================
        // BUSCAR CONTA DE ORIGEM
        // ==========================================================

        String sqlOrigem = """
                SELECT
                    c.id_conta,
                    c.numero_conta,
                    c.agencia,
                    c.saldo,
                    cl.nome,
                    cl.tipo_cliente
                FROM conta c
                INNER JOIN cliente cl
                    ON cl.id_cliente = c.id_cliente
                WHERE c.numero_conta = ?
                """;

        Conexao conexao = Conexao.getInstancia();
        Connection conn = conexao.conectar();

        PreparedStatement stmtOrigem =
                conn.prepareStatement(sqlOrigem);

        stmtOrigem.setInt(1, contaOrigemNumero);

        java.sql.ResultSet rsOrigem =
                stmtOrigem.executeQuery();

        if (!rsOrigem.next()) {

            System.out.println(
                    VERMELHO
                            + "Conta de origem inexistente!"
                            + RESET);

            rsOrigem.close();
            stmtOrigem.close();
            conn.close();

            pausa(scanner);
            continue;
        }

        // ==========================================================
        // CRIAR OBJETO DA CONTA DE ORIGEM COM O FACTORY
        // ==========================================================

        String tipoOrigem = rsOrigem.getString("tipo_cliente");

        Conta origem;

        if ("PF".equalsIgnoreCase(tipoOrigem)) {
            origem = factory.criarConta("PF");
        } else {
            origem = factory.criarConta("PJ");
        }

        origem.setIdConta(rsOrigem.getInt("id_conta"));
        origem.setNumeroConta(rsOrigem.getInt("numero_conta"));
        origem.setAgencia(rsOrigem.getString("agencia"));
        origem.setSaldo(rsOrigem.getDouble("saldo"));
        origem.setTitular(rsOrigem.getString("nome"));
        origem.setTipoConta(tipoOrigem);

        rsOrigem.close();
        stmtOrigem.close();

        // ==========================================================
        // BUSCAR CONTA DE DESTINO
        // ==========================================================

        String sqlDestino = """
                SELECT
                    c.id_conta,
                    c.numero_conta,
                    c.agencia,
                    c.saldo,
                    cl.nome,
                    cl.tipo_cliente
                FROM conta c
                INNER JOIN cliente cl
                    ON cl.id_cliente = c.id_cliente
                WHERE c.numero_conta = ?
                """;

        PreparedStatement stmtDestino =
                conn.prepareStatement(sqlDestino);

        stmtDestino.setInt(1, contaDestinoNumero);

        java.sql.ResultSet rsDestino =
                stmtDestino.executeQuery();

        if (!rsDestino.next()) {

            System.out.println(
                    VERMELHO
                            + "Conta destino inexistente!"
                            + RESET);

            rsDestino.close();
            stmtDestino.close();
            conn.close();

            pausa(scanner);
            continue;
        }

        // ==========================================================
        // CRIAR OBJETO DA CONTA DE DESTINO COM O FACTORY
        // ==========================================================

        String tipoDestino = rsDestino.getString("tipo_cliente");

        Conta destino;

        if ("PF".equalsIgnoreCase(tipoDestino)) {
            destino = factory.criarConta("PF");
        } else {
            destino = factory.criarConta("PJ");
        }

        destino.setIdConta(rsDestino.getInt("id_conta"));
        destino.setNumeroConta(rsDestino.getInt("numero_conta"));
        destino.setAgencia(rsDestino.getString("agencia"));
        destino.setSaldo(rsDestino.getDouble("saldo"));
        destino.setTitular(rsDestino.getString("nome"));
        destino.setTipoConta(tipoDestino);

        rsDestino.close();
        stmtDestino.close();
        conn.close();

        // ==========================================================
        // BANCO FACADE
        // ==========================================================

        boolean realizada =
                banco.transferir(origem, destino, valor);

        if (realizada) {

            System.out.println(
                    VERDE
                            + "\nTransferencia realizada com sucesso!"
                            + "\nConta origem: "
                            + contaOrigemNumero
                            + "\nConta destino: "
                            + contaDestinoNumero
                            + "\nValor: R$ "
                            + String.format("%.2f", valor)
                            + RESET);

        } else {

            System.out.println(
                    VERMELHO
                            + "\nNão foi possível realizar a transferência."
                            + RESET);
        }

    } catch (Exception e) {

        System.out.println(
                VERMELHO
                        + "Erro ao realizar transferencia:"
                        + RESET);

        e.printStackTrace();
    }

    pausa(scanner);
}
// =====================Finalizacao de Transferencias=====================
 

// ======================== REALIZAR SAQUE ===============================
// =======================================================================

if (opcao.equals("5")) {

    System.out.println("Qual o numero da sua conta:");
    int numeroConta = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Qual o valor do saque:");
    double valorSaque = scanner.nextDouble();
    scanner.nextLine();

    if (valorSaque <= 0) {

        System.out.println(
                VERMELHO
                        + "O valor do saque deve ser maior que zero!"
                        + RESET);

        pausa(scanner);
        continue;
    }

    try {

        // ==========================================================
        // BUSCAR A CONTA NO POSTGRESQL
        // ==========================================================

        String sqlConta = """
                SELECT
                    c.id_conta,
                    c.numero_conta,
                    c.agencia,
                    c.saldo,
                    cl.nome,
                    cl.tipo_cliente
                FROM conta c
                INNER JOIN cliente cl
                    ON cl.id_cliente = c.id_cliente
                WHERE c.numero_conta = ?
                """;

        Conexao conexao = Conexao.getInstancia();
        Connection conn = conexao.conectar();

        PreparedStatement stmt =
                conn.prepareStatement(sqlConta);

        stmt.setInt(1, numeroConta);

        java.sql.ResultSet rs =
                stmt.executeQuery();

        // ==========================================================
        // VERIFICAR SE A CONTA EXISTE
        // ==========================================================

        if (!rs.next()) {

            System.out.println(
                    VERMELHO
                            + "Conta inexistente!"
                            + RESET);

            rs.close();
            stmt.close();
            conn.close();

            pausa(scanner);
            continue;
        }

        // ==========================================================
        // CRIAR OBJETO CONTA
        // ==========================================================

        String tipoConta = rs.getString("tipo_cliente");

        Conta conta;

        if ("PF".equalsIgnoreCase(tipoConta)) {
            conta = factory.criarConta("PF");
        } else {
            conta = factory.criarConta("PJ");
        }

        conta.setIdConta(rs.getInt("id_conta"));
        conta.setNumeroConta(rs.getInt("numero_conta"));
        conta.setAgencia(rs.getString("agencia"));
        conta.setSaldo(rs.getDouble("saldo"));
        conta.setTitular(rs.getString("nome"));
        conta.setTipoConta(tipoConta);

        double saldoAnterior = conta.getSaldo();

        rs.close();
        stmt.close();
        conn.close();

        // ==========================================================
        // BANCO FACADE
        // ==========================================================

        boolean realizado =
                banco.sacar(conta, valorSaque);

        if (realizado) {

            double novoSaldo =
                    saldoAnterior - valorSaque;

            System.out.println(
                    VERDE
                            + "\nSaque realizado com sucesso!"
                            + "\nValor sacado: R$ "
                            + String.format("%.2f", valorSaque)
                            + "\nSaldo atual: R$ "
                            + String.format("%.2f", novoSaldo)
                            + RESET);

        } else {

            System.out.println(
                    VERMELHO
                            + "\nNão foi possível realizar o saque."
                            + "\nVerifique o saldo disponível."
                            + RESET);
        }

    } catch (Exception e) {

        System.out.println(
                VERMELHO
                        + "Erro ao realizar saque:"
                        + RESET);

        e.printStackTrace();
    }

    pausa(scanner);
}
// ======================== FAZER DEPOSITO ===============================
// =======================================================================

if (opcao.equals("6")) {

    System.out.println("Qual o numero da sua conta:");
    int numeroConta = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Qual o valor do deposito:");
    double valorDeposito = scanner.nextDouble();
    scanner.nextLine();

    if (valorDeposito <= 0) {
        System.out.println(VERMELHO
                + "O valor do deposito deve ser maior que zero!"
                + RESET);
        pausa(scanner);
        continue;
    }

    try {

        String sqlConta = """
                SELECT
                    c.id_conta,
                    c.numero_conta,
                    c.agencia,
                    c.saldo,
                    cl.nome,
                    cl.tipo_cliente
                FROM conta c
                INNER JOIN cliente cl
                    ON cl.id_cliente = c.id_cliente
                WHERE c.numero_conta = ?
                """;

        Conexao conexao = Conexao.getInstancia();

        Connection conn = conexao.conectar();

        PreparedStatement stmt = conn.prepareStatement(sqlConta);

        stmt.setInt(1, numeroConta);

        java.sql.ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {

            System.out.println(VERMELHO
                    + "Conta inexistente!"
                    + RESET);

            rs.close();
            stmt.close();
            conn.close();

            pausa(scanner);
            continue;
        }

        // Identifica o tipo da conta
        String tipoConta = rs.getString("tipo_cliente");

        Conta conta;

        // Factory cria o objeto correto
        if ("PF".equalsIgnoreCase(tipoConta)) {

            conta = factory.criarConta("PF");

        } else {

            conta = factory.criarConta("PJ");
        }

        // Preenche os dados da conta
        conta.setIdConta(rs.getInt("id_conta"));
        conta.setNumeroConta(rs.getInt("numero_conta"));
        conta.setAgencia(rs.getString("agencia"));
        conta.setSaldo(rs.getDouble("saldo"));
        conta.setTitular(rs.getString("nome"));
        conta.setTipoConta(tipoConta);

        double saldoAnterior = conta.getSaldo();

        // Fecha a consulta
        rs.close();
        stmt.close();
        conn.close();

        // Chama o Facade
        boolean realizado = banco.depositar(conta, valorDeposito);

        if (realizado) {

            double novoSaldo = saldoAnterior + valorDeposito;

            System.out.println(
                    VERDE
                    + "\nDeposito realizado com sucesso!"
                    + "\nValor depositado: R$ "
                    + String.format("%.2f", valorDeposito)
                    + "\nSaldo atual: R$ "
                    + String.format("%.2f", novoSaldo)
                    + RESET
            );

        } else {

            System.out.println(
                    VERMELHO
                    + "\nNao foi possivel realizar o deposito."
                    + RESET
            );
        }

    } catch (Exception e) {

        System.out.println(
                VERMELHO
                + "Erro ao realizar deposito:"
                + RESET
        );

        e.printStackTrace();
    }

    pausa(scanner);
}

} // fecha while

} // fecha main

} // fecha classe App