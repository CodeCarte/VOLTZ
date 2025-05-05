package classes;

public class Criptomoeda {
    private int id;
    private  String sigla;
    private  String nome;


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
