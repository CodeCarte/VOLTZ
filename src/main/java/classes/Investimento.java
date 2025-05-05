package classes;

public class Investimento implements Ativo {
    private int id;
    private String sigla;
    private String nome;
    private TipoInvestimento tipo;

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
}
