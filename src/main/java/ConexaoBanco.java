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
            ResultSet rsUsuario = conexao.createStatement().executeQuery("SELECT * FROM USUARIO");
            while (rsUsuario.next()) {
                System.out.println("Nome: " + rsUsuario.getString("nomeUsuario"));
            }
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
}
