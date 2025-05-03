import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoBanco {
    public static void main (String[] args) throws SQLException {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver do banco de dados não localizado!");
        } catch (SQLException e) {
            System.out.print("Ocorreu um erro ao acessar o banco: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close();
            }
        }
    }

    public static Usuario buscarUsuarioPorNome (String usuarioNome) {
        Connection conexao = null;
        Usuario usuario = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = "SELECT * FROM USUARIO WHERE nomeUsuario = ?";
            var stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuarioNome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeUsuario(rs.getString("nomeUsuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                usuario.setSaldo(rs.getDouble("saldo"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão.");
            }
        }
        return usuario;
    }







}
