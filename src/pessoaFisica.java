
public class pessoaFisica extends Conta {

    private String cpf;
    private String dataNascimento;

    // ==========================================================
    // GETTERS E SETTERS
    // ==========================================================

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    // ==========================================================
    // POLIMORFISMO
    // ==========================================================

    @Override
    public double limiteTransferencia() {
        return 5000.0;
    }
}


