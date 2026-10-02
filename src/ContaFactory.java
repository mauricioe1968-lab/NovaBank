public class ContaFactory {

    public Conta criarConta(String tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de conta não informado.");
        }

        if (tipo.equalsIgnoreCase("PF")) {
            return new pessoaFisica();
        }

        if (tipo.equalsIgnoreCase("PJ")) {
            return new pessoaJuridica();
        }

        throw new IllegalArgumentException(
                "Tipo de conta inválido: " + tipo
        );
    }
}