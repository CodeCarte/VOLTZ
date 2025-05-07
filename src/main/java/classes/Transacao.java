package classes;

import java.time.LocalDateTime;

public class Transacao {
    private int id;
    private Ordem ordem;
    private Usuario usuario;
    private double quantidade;
    private double precoUnitario;
    private LocalDateTime dataHora;


    //--GETTERS--
    public int getId() {
        return this.id;
    }

    public Ordem getOrdem() {
        return this.ordem;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    //--SETTERS--
    public void setOrdem(Ordem novaOrdem) {
        this.ordem = novaOrdem;
    }

    public void setUsuario(Usuario novoUsuario) {
        this.usuario = novoUsuario;
    }

    public void setQuantidade(double novaQuantidade) {
        this.quantidade = novaQuantidade;
    }

    public void setPrecoUnitario(double novoPrecoUnitario) {
        this.precoUnitario = novoPrecoUnitario;
    }

    public void setDataHora(LocalDateTime novaDataHora) {
        this.dataHora = novaDataHora;
    }
}



