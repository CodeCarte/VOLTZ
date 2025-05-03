public class Sessao {
    public static Usuario usuarioAutenticado = null;


    public String autenticarCredenciais (String nomeUsuario, String senha) {

        //busca no banco de dados pelo usuário
        Usuario usuarioEncontrado = ConexaoBanco.buscarUsuarioPorNome(nomeUsuario);
        //Caso não encontre o usuário no bd
        if (usuarioEncontrado == null) {
            return "❌ Usuário não encontrado!";
        }
        //Verificar se a senha está correta
        if (!usuarioEncontrado.autenticarUsuario(senha)) {
            return "❌ Senha incorreta.";
        }
        //Usuario foi encontrado no bd, autentique-o
        usuarioAutenticado = usuarioEncontrado;
        return "✅ Login bem-sucedido";
    }
}
