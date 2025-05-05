package classes;

public class AlocacaoInvestimento {
    private int id;
    private Carteira carteira;
    private Investimento investimento;
    double quantidade;


    public Investimento getInvestimento() {
        return this.investimento;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public Carteira getCarteira() {
        return this.carteira;
    }

    public int getId() {
        return this.id;
    }

}
