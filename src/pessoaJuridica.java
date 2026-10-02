```java
public class pessoaJuridica extends Conta {

    private String cnpj;
    private String dataConstituicao;

    // ==========================================================
    // GETTERS E SETTERS
    // ==========================================================

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDataConstituicao() {
        return dataConstituicao;
    }

    public void setDataConstituicao(String dataConstituicao) {
        this.dataConstituicao = dataConstituicao;
    }

    // ==========================================================
    // POLIMORFISMO
    // ==========================================================

    @Override
    public double limiteTransferencia() {
        return 50000.0;
    }
}
```

