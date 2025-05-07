package classes;

public class Investimento extends AtivoBase {

    private TipoInvestimento tipo;
    private double preco_unitario;


    public TipoInvestimento getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoInvestimento tipo) {
        this.tipo = tipo;
    }

    @Override
    public void mostrarResumo() {
        System.out.printf("📈 Investimento: %s (%s) - R$ %.2f | Tipo: %s%n", nome, sigla, precoUnitario, tipo);
    }

}
