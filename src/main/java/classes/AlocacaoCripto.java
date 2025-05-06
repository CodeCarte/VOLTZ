package classes;

public class AlocacaoCripto {
    private int id;
    private Carteira carteira;
    private Criptomoeda criptoAtivo;
    double quantidade;


    //--GETTERS--
    public Criptomoeda getCriptoAtivo() {
        return this.criptoAtivo;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    //--SETTERS--
    public void setCriptoAtivo(Criptomoeda criptoAtivo) {
        this.criptoAtivo = criptoAtivo;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade(double quant) {
        this.quantidade += quant;
    }

    public void removerQuantidade(double quant) {
        if (quant <= 0) {
            throw new IllegalArgumentException("A quantidade a remover deve ser positiva.");
        }

        if (this.quantidade < quant) {
            throw new IllegalArgumentException("Quantidade insuficiente para remover.");
        }
        this.quantidade -= quant;
    }

}
