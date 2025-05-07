package classes;

public class Criptomoeda extends AtivoBase {


    public Criptomoeda(String criptoNome, String criptoSigla) {
        this.nome = criptoNome;
        this.sigla = criptoSigla;
    }

    public Criptomoeda() {

    }

    @Override
    public void mostrarResumo() {
        System.out.printf("🪙 Cripto: %s (%s) - R$ %.2f%n", nome, sigla, precoUnitario);
    }


}
