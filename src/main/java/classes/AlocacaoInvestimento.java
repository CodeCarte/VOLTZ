package classes;

public class AlocacaoInvestimento {
    private int id;
    private Carteira carteira;
    private Investimento investimento;
    double quantidade;


    public Investimento getInvestimento() {
        return this.investimento;
    }

    //--GETTERS--
    public double getQuantidade() {
        return this.quantidade;
    }

    public Carteira getCarteira() {
        return this.carteira;
    }

    public int getId() {
        return this.id;
    }

    //--SETTERS--
    public void setId(int id) {
        this.id = id;
    }

    public void setInvestimento(Investimento investimento) {
        this.investimento = investimento;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
