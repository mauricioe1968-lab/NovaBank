// Classe abstrata que representa uma conta bancária.
// Ela serve como base para pessoaFisica e pessoaJuridica.

public abstract class Conta implements OperacoesBancarias {

    private int numeroConta;
    private String agencia = "001";
    private Double saldo = 999.0;
    private String titular;
    private String tipoConta;
    private int idConta;

    // ==========================================================
    // GETTERS E SETTERS
    // ==========================================================

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    // ==========================================================
    // OPERAÇÕES BANCÁRIAS
    // ==========================================================

    @Override
    public void depositar(Double valorDeposito) {

        if (valorDeposito == null || valorDeposito <= 0) {
            System.out.println("Valor de depósito inválido.");
            return;
        }

        this.saldo += valorDeposito;
    }

    @Override
    public boolean sacar(Double valorSaque, String tipoTransferencia) {

        if (valorSaque == null || valorSaque <= 0) {
            System.out.println("Valor de saque inválido.");
            return false;
        }

        if (valorSaque > this.saldo) {
            System.out.println("Saldo insuficiente.");
            return false;
        }

        this.saldo -= valorSaque;

        return true;
    }

    /*
     * A transferência não será realizada diretamente aqui.
     *
     * Uma transferência envolve duas contas:
     *
     * conta origem  -> diminui o saldo
     * conta destino -> aumenta o saldo
     *
     * Essa operação será coordenada pelo BancoFacade.
     */

    @Override
    public void transferencia(Double valorTransferencia) {

        // A operação completa de transferência
        // será responsabilidade do BancoFacade.
    }

    // ==========================================================
    // POLIMORFISMO
    // ==========================================================

    // Cada tipo de conta deverá informar seu próprio limite.
    // pessoaFisica e pessoaJuridica irão implementar este método.

    public abstract double limiteTransferencia();
}

