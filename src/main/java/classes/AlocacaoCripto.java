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

    public void adicionarQuantidade(double valor) {
        this.quantidade += valor;
    }

}
