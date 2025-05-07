package classes;



import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BancoDeDados {

    private static Connection abrirConexao() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("src/config.properties"));

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static void main (String[] args) {
        try (Connection conexao = abrirConexao()) {
            if (conexao != null && !conexao.isClosed()) {
                System.out.println("Conexão com o banco de dados foi estabelecida com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    public static void salvarUsuario(Usuario usuario) {
        String sql = """
                INSERT INTO USUARIO (nome_usuario, email, senha, data_nascimento, saldo, sexo)
                VALUES
                (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getDataNascimento()));
            stmt.setDouble(5, usuario.getSaldo());
            stmt.setString(6, usuario.getSexo());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGerado = rs.getInt(1);
                        usuario.setId(idGerado);
                    }
                }
                System.out.println("✅ Usuário cadastrado com sucesso!");
            } else {
                System.out.println("❌ Falha ao cadastrar usuário");
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public static int salvarOrdem(Ordem ordem) {
        String sql = """
                    INSERT INTO ORDEM (id_usuario, tipo, quantidade, preco_unitario, status, data_hora, ativo_tipo, id_criptomoeda, id_investimento)\s
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                   \s""";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            } else if (ordem.getAtivo() instanceof Investimento) {
                stmt.setString(7, "INVEST");
                stmt.setNull(8, Types.INTEGER);
                stmt.setInt(9, ((Investimento) ordem.getAtivo()).getId());
            } else {
                throw new IllegalArgumentException("Tipo de ativo desconhecido");
            }

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1);
                        ordem.setId(idGerado);
                        System.out.println("✅ Ordem registrada com sucesso!");
                        return idGerado;
                    }
                }
            } else {
                System.out.println("❌ Falha ao salvar ordem");
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar ordem: " + e.getMessage());
        }
        return -1;
    }

    public static void salvarTransacao(Transacao transacao) {
        String sql = """
                    INSERT INTO TRANSACAO (id_ordem, id_usuario, quantidade_executada, preco_unitario, data_hora)\s
                    VALUES (?, ?, ?, ?, ?)
                   \s""";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, transacao.getOrdem().getId());
            stmt.setInt(2, transacao.getUsuario().getId());
            stmt.setDouble(3, transacao.getQuantidade());
            stmt.setDouble(4, transacao.getPrecoUnitario());
            stmt.setTimestamp(5, Timestamp.valueOf(transacao.getDataHora()));

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("✅ Transação registrada com sucesso!");
            } else {
                System.out.println("❌ Falha ao salvar transação");
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar transação: " + e.getMessage());
        }
    }

    public static void salvarAlocacaoCripto(int idCarteira, int idCripto, double quantidade) {
        String sql = "INSERT INTO alocacao_cripto (id_carteira, id_criptomoeda, quantidade) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            stmt.setInt(2, idCripto);
            stmt.setDouble(3, quantidade);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar alocação de criptomoeda: " + e.getMessage());
        }
    }

    public static void salvarAlocacaoInvestimento(int idCarteira, int idInvestimento, double quantidade) {
        String sql = "INSERT INTO alocacao_investimento (id_carteira, id_investimento, quantidade) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCarteira);
            stmt.setInt(2, idInvestimento);
            stmt.setDouble(3, quantidade);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar alocação de investimento: " + e.getMessage());
        }
    }

    public static int salvarCarteira(int idUsuario) {
        String sql = "INSERT INTO carteira (id_usuario) VALUES (?)";


        try (Connection conexao = abrirConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Erro ao salvar Carteira no banco: " + e.getMessage());
        }
        return -1;
    }

    public static Usuario buscarUsuarioPorNome (String usuarioNome) {
        Usuario usuario = null;
        String sql = "SELECT * FROM USUARIO WHERE nome_usuario = ?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

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

            rs.close();
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return usuario;
    }

    public static Carteira buscarCarteiraPorUsuarioId(int idUsuario) {
        Carteira carteira = null;
        String sql = "SELECT * FROM carteira WHERE id_usuario = ?";

        try (Connection conexao = abrirConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                carteira = new Carteira();
                int idCarteira = rs.getInt("id");
                carteira.setId(idCarteira);

                List<AlocacaoCripto> criptoAlocacoes = listarAlocacoesCriptoPorCarteiraId(idCarteira);
                carteira.getCriptoAlocacoes().addAll(criptoAlocacoes);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro ao buscar carteira: " + e.getMessage());
        }
        return carteira;
    }

    public static Carteira carregarCarteiraCompletaDoUsuario(int idUsuario) {
        Carteira carteira = null;

        String sqlCarteira = "SELECT * FROM carteira WHERE id_usuario = ?";
        String sqlCriptos = """
                SELECT ac.quantidade, c.id, c.nome, c.sigla, c.preco_unitario
                FROM alocacao_cripto ac
                JOIN criptomoeda c ON ac.id_criptomoeda = c.id
                WHERE ac.id_carteira = ?
                """;
        String sqlInvestimentos = """
                SELECT ai.quantidade, i.id, i.nome, i.sigla, i.tipo, i.preco_unitario
                FROM alocacao_investimento ai
                JOIN investimento i ON ai.id_investimento = i.id
                WHERE ai.id_carteira = ?
                """;

        try (Connection conexao = abrirConexao()) {

            try (PreparedStatement stmt1 = conexao.prepareStatement(sqlCarteira)) {
                stmt1.setInt(1, idUsuario);
                ResultSet rs1 = stmt1.executeQuery();

                if (rs1.next()) {
                    carteira = new Carteira();
                    int idCarteira = rs1.getInt("id");
                    carteira.setId(idCarteira);

                    //Carregar Criptomoedas
                    try (PreparedStatement stmt2 = conexao.prepareStatement(sqlCriptos)) {
                        stmt2.setInt(1, idCarteira);
                        ResultSet rs2 = stmt2.executeQuery();

                        while (rs2.next()) {
                            Criptomoeda cripto = new Criptomoeda();
                            cripto.setId(rs2.getInt("id"));
                            cripto.setNome(rs2.getString("nome"));
                            cripto.setSigla(rs2.getString("sigla"));
                            cripto.setPrecoUnitario(rs2.getDouble("preco_unitario"));

                            AlocacaoCripto alocacao = new AlocacaoCripto();
                            alocacao.setCriptoAtivo(cripto);
                            alocacao.setQuantidade(rs2.getDouble("quantidade"));

                            carteira.getCriptoAlocacoes().add(alocacao);
                        }
                        rs2.close();
                    }
                    //Carregar Investimentos
                    try (PreparedStatement stmt3 = conexao.prepareStatement(sqlInvestimentos)) {
                        stmt3.setInt(1, idCarteira);
                        ResultSet rs3 = stmt3.executeQuery();

                        while (rs3.next()) {
                            Investimento inv = new Investimento();
                            inv.setId(rs3.getInt("id"));
                            inv.setNome(rs3.getString("nome"));
                            inv.setSigla(rs3.getString("sigla"));
                            inv.setTipo(TipoInvestimento.valueOf(rs3.getString("tipo")));
                            inv.setPrecoUnitario(rs3.getDouble("preco_unitario"));

                            AlocacaoInvestimento alocInv = new AlocacaoInvestimento();
                            alocInv.setInvestimento(inv);
                            alocInv.setQuantidade(rs3.getDouble("quantidade"));

                            carteira.getInvestimentoAlocacoes().add(alocInv);
                        }
                        rs3.close();
                    }
                }
                rs1.close();
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar criptos do Usuário: " + e.getMessage());
        }
        return carteira;
    }

    public static List<Investimento> buscarInvestimentosPorTipo(String tipo) {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT * FROM investimento WHERE tipo = ?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, tipo.toUpperCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Investimento inv = new Investimento();
                inv.setId(rs.getInt("id"));
                inv.setNome(rs.getString("nome"));
                inv.setSigla(rs.getString("sigla"));
                inv.setTipo(TipoInvestimento.valueOf(rs.getString("tipo")));
                inv.setPrecoUnitario(rs.getDouble("preco_unitario"));
                investimentos.add(inv);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro ao buscar investimentos por tipo: " + e.getMessage());
        }
        return investimentos;
    }

    public static List<AlocacaoCripto> listarAlocacoesCriptoPorCarteiraId(int idCarteira) {
        List<AlocacaoCripto> alocacoes = new ArrayList<>();
        String sql = """
                SELECT ac.quantidade, c.id, c.nome, c.sigla, c.preco_unitario
                FROM alocacao_cripto ac
                JOIN criptomoeda c ON ac.id_criptomoeda = c.id
                WHERE ac.id_carteira = ?""";

        try (Connection conexao = abrirConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCarteira);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Criptomoeda cripto = new Criptomoeda();
                cripto.setId(rs.getInt("id"));
                cripto.setNome(rs.getString("nome"));
                cripto.setSigla(rs.getString("sigla"));
                cripto.setPrecoUnitario(rs.getDouble("preco_unitario"));

                AlocacaoCripto alocacao = new AlocacaoCripto();
                alocacao.setCriptoAtivo(cripto);
                alocacao.setQuantidade(rs.getDouble("quantidade"));

                alocacoes.add(alocacao);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar alocações de cripto: " + e.getMessage());
        }
        return alocacoes;
    }

    public static boolean emailJaExiste(String email) {
        String sql = "SELECT * FROM USUARIO WHERE email = ?";
        boolean existe = false;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                existe = rs.next();
            }
        } catch (Exception e) {
            System.out.println("Erro ao verificar email existente: " + e.getMessage());
        }
        return existe;
    }

    public static Criptomoeda buscarCriptomoedaPorSigla(String sigla) {
        Criptomoeda cripto = null;
        String sql = "SELECT * FROM CRIPTOMOEDA WHERE sigla = ?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, sigla.toUpperCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cripto = new Criptomoeda();
                    cripto.setId(rs.getInt("id"));
                    cripto.setNome(rs.getString("nome"));
                    cripto.setSigla(rs.getString("sigla"));
                    cripto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar criptomoeda: " + e.getMessage());
        }
        return cripto;
    }

    public static List<Criptomoeda> listarCriptomoedas() {
        List<Criptomoeda> lista = new ArrayList<>();
        String sql = "SELECT * FROM CRIPTOMOEDA";

        try (Connection conexao = abrirConexao()) {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Criptomoeda cripto = new Criptomoeda();
                cripto.setId(rs.getInt("id"));
                cripto.setNome(rs.getString("nome"));
                cripto.setSigla(rs.getString("sigla"));
                cripto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                lista.add(cripto);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar criptomoedas: " + e.getMessage());
        }
        return lista;
    }

    public static void atualizarSaldoUsuario(Usuario usuario) {
        String sql = "UPDATE USUARIO SET saldo = ? WHERE id = ?";

        try (Connection conexao = abrirConexao()) {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, usuario.getSaldo());
            stmt.setInt(2, usuario.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Saldo atualizado no banco com sucesso.");
            } else {
                System.out.println("Falha ao atualizar saldo.");
            }
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar saldo: " + e.getMessage());
        }
    }
}
