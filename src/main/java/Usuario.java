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

public Usuario () {

}

//---GETTERS---
public String getNomeUsuario() {
    return usuarioNome;
}

public String getEmail() {
    return email;
}

public String getSenha() {
    return senha;
}

public String getSexo() {
    return sexo;
}

public LocalDate getDataNascimento() {
    return dataNascimento;
}

public double getSaldo() {
    return saldo;
}

//---SETTERS---
public void setId (int novoId) {
    id = novoId;
}

public void setNomeUsuario (String novoNomeUsuario) {
    usuarioNome = novoNomeUsuario;
}

public void setEmail (String novoEmail) {
    email = novoEmail;
}

public void setSenha (String novaSenha) {
    senha = novaSenha;
}

public void setDataNascimento (LocalDate novaDataNascimento) {
    dataNascimento = novaDataNascimento;
}

public void setSaldo (double novoSaldo) {
    saldo = novoSaldo;
}

public void setSexo (String novoSexo) {
    sexo = novoSexo;
}

public boolean autenticarUsuario(String senhaInformada) {
    return this.senha.equals(senhaInformada);
}














}


