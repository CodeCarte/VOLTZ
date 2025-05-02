import java.time.LocalDate;
import java.util.Scanner;

public class Sistema {
    public static void main (String[] args) {
        System.out.println("Olá, bem vindo ao sistema do VOLTZ");


        Scanner myScanner = new MyScanner(System.in);
        System.out.println("""
                Escolha uma das Opções:
                1 - Criar Conta
                2 - Logar Na Conta
                """);
        int opcoes = myScanner.nextInt();

        if (opcoes != 1 && opcoes != 2) {
            System.out.println("Por favor, digite uma opção válida!");
        }

        switch (opcoes) {

            case 1:
                System.out.println("Digite o nome de Usuário: ");
                String nomeUsuario = myScanner.nextLine();

                System.out.println("Digite o email: \n" +
                        "Ex: 'victorameno@hotmail.com'");
                String emailUsuario = myScanner.nextLine();

                System.out.println("Digite a senha: \n" +
                        "Ex: 'R4ADasdw24fas' ");
                String senhaUsuario = myScanner.nextLine();

                System.out.println("Digite o seu sexo (Masculino) ou (Feminino): ");
                String sexoUsuario = myScanner.nextLine();

                if (!sexoUsuario.equals("Masculino") && !sexoUsuario.equals("Feminino")) {
                    System.out.println("Por favor, digita um sexo válido! \n" +
                            "Ex: (Masculino) ou (Feminino)");
                }

                System.out.println("Digite a sua data de nascimento: (formato: AAAA-MM-DD)");
                String dataStr = myScanner.nextLine();
                LocalDate dataNascimento = null;

                try {
                    dataNascimento = LocalDate.parse(dataStr);
                } catch (Exception e) {
                    System.out.println("Formato inválido! Use AAA-MM-DD.");
                }

                Usuario usuario = new Usuario(nomeUsuario, emailUsuario, senhaUsuario, sexoUsuario, dataNascimento);













        }







    }
}