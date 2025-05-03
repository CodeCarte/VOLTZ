public class Sessao {
    public static Usuario usuarioAutenticado = null;


    public void autenticarCredenciais (String nomeUsuario, String senha) {

        //busca no banco de dados pelo usuário
        Usuario usuarioEncontrado = ConexaoBanco.buscarUsuarioPorNome(nomeUsuario);
    }
}
