package classes;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConexaoBanco {

    private static Connection abrirConexao() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("src/config.properties"));

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static void main (String[] args) throws Exception {
        try (Connection conexao = abrirConexao()) {
            if (conexao != null && !conexao.isClosed()) {
                System.out.println("Conexão com o banco de dados foi estabelecida com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    public static void salvarUsuario(Usuario usuario) {
        Connection conexao = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = """
                    INSERT INTO USUARIO (nome_usuario, email, senha, data_nascimento, saldo, sexo)
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

            String sql = "SELECT * FROM USUARIO WHERE nome_usuario = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuarioNome);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNomeUsuario(rs.getString("nome_usuario"));
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

    public static Criptomoeda buscarCriptomoedaPorSigla(String sigla) {
        Connection conexao = null;
        Criptomoeda cripto = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = "SELECT * FROM CRIPTOMOEDA WHERE sigla = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, sigla.toUpperCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cripto = new Criptomoeda();
                cripto.setId(rs.getInt("id"));
                cripto.setNome(rs.getString("nome"));
                cripto.setSigla(rs.getString("sigla"));
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println("Erro ao buscar criptomoeda: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão.");
            }
        }
        return cripto;
    }

    public static void salvarOrdem(Ordem ordem) {
        Connection conexao = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = """
                    INSERT INTO ORDEM (id_usuario, tipo, quantidade, preco_unitario, status, data_hora, ativo_tipo, id_criptomoeda, id_investimento)\s
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                   \s""";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, ordem.getUsuario().getId());
            stmt.setString(2, ordem.getTipo().toString());
            stmt.setDouble(3, ordem.getQuantidade());
            stmt.setDouble(4, ordem.getPrecoUnitario());
            stmt.setString(5, ordem.getStatus().toString());
            stmt.setTimestamp(6, Timestamp.valueOf(ordem.getDataHora()));

            if (ordem.getAtivo() instanceof Criptomoeda) {
                stmt.setString(7, "CRIPTO");
                stmt.setInt(8, ((Criptomoeda) ordem.getAtivo()).getId());
                stmt.setNull(9, Types.INTEGER);
            }

            else if (ordem.getAtivo() instanceof Investimento) {
                stmt.setString(7, "INVEST");
                stmt.setNull(8, Types.INTEGER);
                stmt.setInt(9, ((Investimento) ordem.getAtivo()).getId());
            }

            else {
                throw new IllegalArgumentException("Tipo de ativo desconhecido");
            }

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("✅ Ordem registrada com sucesso!");
            } else {
                System.out.println("❌ Falha ao salvar ordem");
            }

            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar ordem: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão.");
            }
        }
    }

    public static void salvarTransacao(Transacao transacao) {
        Connection conexao = null;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/voltz", "root", "Rws45tuv32%");

            String sql = """
                    INSERT INTO TRANSACAO (id_ordem, id_usuario, quantidade_executada, preco_unitario, data_hora)\s
                    VALUES (?, ?, ?, ?, ?)
                   \s""";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, transacao.getOrdem().getId());
            stmt.setInt(2, transacao.getUsuario().getId());
            stmt.setDouble(3, transacao.getQuantidade());
            stmt.setDouble(4, transacao.getPrecoUnitario());
            stmt.setTimestamp(5, Timestamp.valueOf(transacao.getDataHora()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("✅ Ordem registrada com sucesso!");
            } else {
                System.out.println("❌ Falha ao salvar ordem");
            }

            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar transação: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão.");
            }
        }
    }



}
