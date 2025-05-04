package classes;

public class Criptomoeda {
    private String sigla;
    private String nome;


    public Criptomoeda(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    //---GETTERS---
    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
 }
