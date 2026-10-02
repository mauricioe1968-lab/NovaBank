// Futura integração PostgreSQL:
// conta1 e conta2 poderão ser substituídas por
// idContaOrigem e idContaDestino. (já fizemos isto)

public class Extrato {

    private String conta1;
    private String conta2;
    private String operacao;
    private Double valorOperacao;
    private String dataOperacao;
    private int idTransacao;

    public Extrato(String conta1,
            String conta2,
            String operacao,
            Double valor,
            String data) {

        this.conta1 = conta1;
        this.conta2 = conta2;
        this.operacao = operacao;
        this.valorOperacao = valor;
        this.dataOperacao = data;
    }

    public String getConta1() {
        return conta1;
    }

    public void setConta1(String conta1) {
        this.conta1 = conta1;
    }

    public String getConta2() {
        return conta2;
    }

    public void setConta2(String conta2) {
        this.conta2 = conta2;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Double getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(Double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public String getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(String dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }
}
