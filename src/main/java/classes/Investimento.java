package classes;

public class Investimento implements Ativo {
    private int id;
    private String sigla;
    private String nome;
    private TipoInvestimento tipo;
    private double preco_unitario;

    //--GETTERS--
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public TipoInvestimento getTipo() {
        return this.tipo;
    }

    public double getPreco_unitario() {
        return preco_unitario;
    }

    //--SETTERS--
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setTipo(TipoInvestimento tipo) {
        this.tipo = tipo;
    }

    public void setPreco_unitario(double preco_unitario) {
        this.preco_unitario = preco_unitario;
    }
}
