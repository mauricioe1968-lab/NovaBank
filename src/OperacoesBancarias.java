
public interface OperacoesBancarias {

    void depositar(Double valorDeposito);

    boolean sacar(
            Double valorSaque,
            String tipoTransferencia
    );

    void transferencia(
            Double valorTransferencia
    );

}
