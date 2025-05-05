package classes;

import jdk.jshell.Snippet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ordem {
    private int id;
    private Usuario usuario;
    private Ativo ativo;
    private TipoOrdem tipo;
    private StatusOrdem status;
    private double quantidade;
    private LocalDateTime dataHora;
    private double precoUnitario;


    public int getId() {
        return this.id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public TipoOrdem getTipo() {
        return this.tipo;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    public StatusOrdem getStatus() {
        return this.status;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public Ativo getAtivo() {
        return this.ativo;
    }

    //--SETTERS--
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(Usuario novoUsuario) {
        this.usuario = novoUsuario;
    }

    public void setAtivo(Ativo novoAtivo) {
        this.ativo = novoAtivo;
    }

    public void setTipo(TipoOrdem novoTipo) {
        this.tipo = novoTipo;
    }

    public void setQuantidade(double novaQuantidade) {
        this.quantidade = novaQuantidade;
    }

    public void setStatus(StatusOrdem novoStatus) {
        this.status = novoStatus;
    }

    public void setDataHora(LocalDateTime novaDataHora) {
        this.dataHora = novaDataHora;
    }

    public void setPrecoUnitario(double novoPrecoUnitario) {
        this.precoUnitario = novoPrecoUnitario;
    }





}
