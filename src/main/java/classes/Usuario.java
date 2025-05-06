package classes;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String usuarioNome;
    private String email;
    private String senha;
    private String sexo;
    private LocalDate dataNascimento;
    private double saldo;
    private Carteira carteira;

public Usuario (String nomeUsuario, String emailUsuario, String senhaUsuario, String sexoUsuario, LocalDate dataNascimentoUsuario) {
    this.usuarioNome = nomeUsuario;
    this.email = emailUsuario;
    this.senha = senhaUsuario;
    this.sexo = sexoUsuario;
    this.dataNascimento = dataNascimentoUsuario;
}

public Usuario (String nomeUsuario, String senhaUsuario) {
    this.usuarioNome = nomeUsuario;
    this.senha = senhaUsuario;
}

public Usuario () {

}

//---GETTERS---
public String getNomeUsuario() {
    return this.usuarioNome;
}

public int getId() {
    return this.id;
}

public String getEmail() {
    return this.email;
}

public String getSenha() {
    return this.senha;
}

public String getSexo() {
    return this.sexo;
}

public LocalDate getDataNascimento() {
    return this.dataNascimento;
}

public double getSaldo() {
    return this.saldo;
}

public Carteira getCarteira() {
    return this.carteira;
}


//---SETTERS---
public void setId (int novoId) {
    this.id = novoId;
}

public void setNomeUsuario (String novoNomeUsuario) {
    this.usuarioNome = novoNomeUsuario;
}

public void setEmail (String novoEmail) {
    this.email = novoEmail;
}

public void setSenha (String novaSenha) {
    this.senha = novaSenha;
}

public void setDataNascimento (LocalDate novaDataNascimento) {
    this.dataNascimento = novaDataNascimento;
}

public void setSaldo (double novoSaldo) {
    this.saldo = novoSaldo;
}

public void setSexo (String novoSexo) {
    this.sexo = novoSexo;
}

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public boolean autenticarUsuario(String senhaInformada) {
    return this.senha.equals(senhaInformada);
}

public void adicionarSaldo(double valorBRL) {
    if (valorBRL > 0) {
        this.saldo += valorBRL;
    } else {
        System.out.println("❌ Valor inválido. Só é possível adicionar valores positivos.");
    }
}

public void removerSaldo(double valorBRL) {
    if (valorBRL > 0 && this.saldo >= valorBRL) {
        this.saldo -= valorBRL;
    } else {
        System.out.println("Valor inválido para remoção");
    }

}


}


