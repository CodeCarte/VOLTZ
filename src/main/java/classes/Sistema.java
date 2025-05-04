package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Sistema {
    public static void main (String[] args) {
        System.out.println("Olá, bem vindo ao sistema do VOLTZ");


        Scanner myScanner = new Scanner(System.in);
        System.out.println("""
                Escolha uma das Opções:
                1 - Criar Conta
                2 - Logar Na Conta
                """);
        int opcoes = myScanner.nextInt();
        myScanner.nextLine();

        if (opcoes != 1 && opcoes != 2) {
            System.out.println("Por favor, digite uma opção válida!");
        }

        switch (opcoes) {

            case 1:
                System.out.println("Digite o nome de Usuário: ");
                String nomeUsuario = myScanner.nextLine().trim();

                if (nomeUsuario.isEmpty()) {
                    System.out.println("❌ Nome de usuário não pode ser vazio.");
                    break;
                }
                if (ConexaoBanco.buscarUsuarioPorNome(nomeUsuario) != null) {
                    System.out.println("❌ Nome de usuário já existe. Escolha outro.");
                    break;
                }

                System.out.println("Digite o email: \n" +
                        "Ex: 'victorameno@hotmail.com'");
                String emailUsuario = myScanner.nextLine().trim();

                if (emailUsuario.isEmpty()) {
                    System.out.println("❌ Email não pode estar vazio.");
                    break;
                }
                if (ConexaoBanco.emailJaExiste(emailUsuario)) {
                    System.out.print("❌ Email já está em uso. Tente outro");
                    break;
                }

                System.out.println("Digite a senha: \n" +
                        "Ex: 'R4ADasdw24fas' ");
                String senhaUsuario = myScanner.nextLine().trim();

                if (senhaUsuario.isEmpty()) {
                    System.out.println("❌ Senha não pode ser vazia.");
                    break;
                }

                System.out.println("Digite o seu sexo (Masculino) ou (Feminino): ");
                String sexoUsuario = myScanner.nextLine().trim();

                if (!sexoUsuario.equalsIgnoreCase("Masculino") && !sexoUsuario.equalsIgnoreCase("Feminino")) {
                    System.out.println("❌ Sexo inválido. Digite 'Masculino' ou 'Feminino'.");
                    break;
                }

                System.out.println("Digite a sua data de nascimento: (formato: DD/MM/AAAA): ");
                String dataStr = myScanner.nextLine().trim();
                LocalDate dataNascimento;

                try {
                    DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataNascimento = LocalDate.parse(dataStr, formatoBR);
                } catch (Exception e) {
                    System.out.println("❌ Formato inválido! Use DD/MM/AAAA.");
                    break;
                }

                Usuario usuario = new Usuario(nomeUsuario, emailUsuario, senhaUsuario, sexoUsuario, dataNascimento);
                ConexaoBanco.salvarUsuario(usuario);
                System.out.println("A sua conta foi criada com Sucesso!");
                break;
        }
    }
}