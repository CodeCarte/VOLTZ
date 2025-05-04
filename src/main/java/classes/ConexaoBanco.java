package classes;

import java.sql.*;

public class ConexaoBanco {
    public static void main (String[] args) throws SQLException {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado.");
        } catch (SQLException e) {
            System.out.print("Ocorreu um erro ao acessar o banco: " + e.getMessage());
        } finally {
            if (conexao != null) {
                conexao.close();
            }
        }
    }

    public static void salvarUsuario(Usuario usuario) {
        Connection conexao = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = """
                    INSERT INTO USUARIO (nomeUsuario, email, senha, data_nascimento, saldo, sexo)
                    VALUES
                    (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            //Define cada "?" com o valor do 'Objeto classes.Usuario'
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getDataNascimento()));
            stmt.setDouble(5, usuario.getSaldo());
            stmt.setString(6, usuario.getSexo());

            //Executar INSERT no banco
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Usuário cadastrado com sucesso!");
            } else {
                System.out.println("❌ Falha ao cadastrar usuário");
            }
            stmt.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão.");
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
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuarioNome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeUsuario(rs.getString("nomeUsuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                usuario.setSaldo(rs.getDouble("saldo"));
                usuario.setSexo(rs.getString("sexo"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado.");
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

    public static boolean emailJaExiste(String email) {
        Connection conexao = null;
        boolean existe = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = "SELECT * FROM USUARIO WHERE email = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            existe = rs.next();
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao verificar email existente: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão.");
            }
        }
        return existe;


    }







}
