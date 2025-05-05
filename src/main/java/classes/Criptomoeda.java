package classes;

public class Criptomoeda implements Ativo {
    private int id;
    private  String sigla;
    private  String nome;
    private double precoUnitario;


    public Criptomoeda(String criptoNome, String criptoSigla) {
        this.nome = criptoNome;
        this.sigla = criptoSigla;
    }

    public Criptomoeda() {

    }

    //---GETTERS---
    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public int getId() {
        return this.id;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    //---SETTERS---
    public void setId(int novoId) {
        this.id = novoId;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setSigla(String novaSigla) {
        this.sigla = novaSigla;
    }

 }
