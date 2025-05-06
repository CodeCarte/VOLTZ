package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    public static void main (String[] args) {
        System.out.println("Olá, bem vindo ao sistema do VOLTZ");


        Scanner myScanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                Escolha uma das Opções:
                1 - Criar Conta
                2 - Logar Na Conta
                3 - Sair
                """);
            int opcoes = myScanner.nextInt();
            myScanner.nextLine();

            switch (opcoes) {

                //Criação de Conta
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

                    try {
                        Usuario usuario = new Usuario(nomeUsuario, emailUsuario, senhaUsuario, sexoUsuario, dataNascimento);
                        ConexaoBanco.salvarUsuario(usuario);
                        int idCarteira = ConexaoBanco.salvarCarteira(usuario.getId());
                        Carteira carteira = new Carteira();
                        carteira.setId(idCarteira);
                        carteira.setUsuario(usuario);
                        usuario.setCarteira(carteira);

                        System.out.println("A sua conta foi criada com Sucesso!");
                        break;
                    } catch (Exception e) {
                        System.out.println("❌ Ocorreu um erro ao criar o usuário: " + e.getMessage());
                    }
                    break;

                //Logar na Conta
                case 2:
                    System.out.println("Digite o nome de Usuário: ");
                    String nomeLogin = myScanner.nextLine().trim();

                    System.out.println("Digite a senha: ");
                    String senhaLogin = myScanner.nextLine().trim();

                    Sessao sessao = new Sessao();
                    String respostaLogin = sessao.autenticarCredenciais(nomeLogin, senhaLogin);
                    System.out.println(respostaLogin);

                    if (Sessao.usuarioAutenticado != null) {
                        Carteira carteira = ConexaoBanco.buscarCarteiraPorUsuarioId(Sessao.usuarioAutenticado.getId());
                        Sessao.usuarioAutenticado.setCarteira(carteira);
                        System.out.println("Bem vindo(a), " + Sessao.usuarioAutenticado.getNomeUsuario() + "!");

                        while (true) {
                            System.out.println();
                            System.out.println("O que você deseja fazer?");
                            System.out.println("""
                                    1 - Adicionar Saldo
                                    2 - Ver Saldo
                                    3 - Comprar Criptomoeda
                                    4 - Listar Criptomoedas Disponíveis
                                    5 - Ver minha Carteira
                                    6 - Investir com Cripto
                                    7 - Sair da Conta""");

                            int opcoesSistema = myScanner.nextInt();
                            myScanner.nextLine();
                            switch (opcoesSistema) {

                                //Adicionar Saldo
                                case 1:
                                    System.out.println("Insira um Valor decimal: ");
                                    double valorBRL = myScanner.nextDouble();

                                    Usuario usuario = Sessao.usuarioAutenticado;
                                    usuario.adicionarSaldo(valorBRL);
                                    ConexaoBanco.atualizarSaldoUsuario(usuario);
                                    System.out.println("✅ Saldo adicionado com sucesso!");
                                    System.out.println("Saldo atual: R$ " + usuario.getSaldo());
                                    break;

                                    //Ver Saldo
                                case 2:
                                    Usuario mesmoUsuarioo = Sessao.usuarioAutenticado;
                                    System.out.println("O saldo atual é: " + mesmoUsuarioo.getSaldo() + "\n");
                                    break;

                                    //Comprar Criptomoeda com Saldo
                                case 3:
                                    System.out.print("Digite a Sigla da Criptomoeda que deseja comprar (ex: BTC): ");
                                    String siglaCripto = myScanner.nextLine().toUpperCase();

                                    Criptomoeda criptoSelecionada = ConexaoBanco.buscarCriptomoedaPorSigla(siglaCripto);

                                    if (criptoSelecionada == null) {
                                        System.out.println("❌ Criptomoeda não encontrada.");
                                        break;
                                    }
                                    double precoUnitario = criptoSelecionada.getPrecoUnitario();

                                    System.out.println("Digite quanto você deseja investir em reais (R$): ");
                                    String valorStr = myScanner.nextLine().replace(",", ".");
                                    double valorInvestido;

                                    try {
                                        valorInvestido = Double.parseDouble(valorStr);
                                        if (valorInvestido <= 0) {
                                            System.out.println("❌ Valor inválido. Insira um valor maior que zero");
                                            break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Valor inválido. Use ponto ou vírgula corretamente.");
                                        break;
                                    }

                                    Usuario mesmoUsuario = Sessao.usuarioAutenticado;

                                    if (mesmoUsuario.getSaldo() < valorInvestido) {
                                        System.out.println("❌ Saldo insuficiente. Seu saldo é de R$ " + mesmoUsuario.getSaldo());
                                        break;
                                    }

                                    if (precoUnitario <= 0) {
                                        System.out.println("Erro: O preço unitário da criptomoeda está inválido (R$ " + precoUnitario + ").");
                                        break;
                                    }

                                    double quantidade = valorInvestido / precoUnitario;
                                    System.out.printf("Você irá comprar aproximadamente %.8f %s. Confirmar? (S/N): ",
                                            quantidade, siglaCripto);
                                    String confirmar = myScanner.nextLine().trim();

                                    if (!confirmar.equalsIgnoreCase("S")) {
                                        System.out.println("Compra cancelada.");
                                        break;
                                    }

                                    ConexaoBanco.salvarAlocacaoCripto(mesmoUsuario.getCarteira().getId(), criptoSelecionada.getId(), quantidade);
                                    mesmoUsuario.removerSaldo(valorInvestido);
                                    mesmoUsuario.getCarteira().adicionarCripto(criptoSelecionada, quantidade);
                                    ConexaoBanco.atualizarSaldoUsuario(mesmoUsuario);

                                    //Registrar o Pedido de Compra (Ordem):
                                    Ordem ordem = new Ordem();
                                    ordem.setUsuario(mesmoUsuario);
                                    ordem.setAtivo(criptoSelecionada);
                                    ordem.setTipo(TipoOrdem.COMPRA);
                                    ordem.setPrecoUnitario(precoUnitario);
                                    ordem.setQuantidade(quantidade);
                                    ordem.setStatus(StatusOrdem.EXECUTADA);
                                    ordem.setDataHora(LocalDateTime.now());
                                    ConexaoBanco.salvarOrdem(ordem);

                                    //Registrar Transacao (Transacao):
                                    Transacao transacao = new Transacao();
                                    transacao.setOrdem(ordem);
                                    transacao.setUsuario(mesmoUsuario);
                                    transacao.setQuantidade(quantidade);
                                    transacao.setPrecoUnitario(precoUnitario);
                                    transacao.setDataHora(LocalDateTime.now());
                                    ConexaoBanco.salvarTransacao(transacao);
                                    break;

                                    //Listar Criptomoedas
                                case 4:
                                    List<Criptomoeda> criptos = ConexaoBanco.listarCriptomoedas();

                                    if (criptos.isEmpty()) {
                                        System.out.println("❌ Nenhuma criptomoeda disponível.");
                                    } else {
                                        System.out.println("Criptomoedas disponíveis: ");
                                        for (Criptomoeda c : criptos) {
                                            System.out.println("🔹 " + c.getNome() + " (" + c.getSigla() + ") - Preço Unitário: R$ " + c.getPrecoUnitario());
                                        }
                                    }
                                    break;

                                    //Ver Carteira
                                case 5:
                                    Usuario mesmoUsuario3 = Sessao.usuarioAutenticado;
                                    Carteira carteiraa = mesmoUsuario3.getCarteira();

                                    if (carteiraa == null) {
                                        System.out.println("Nenhuma carteira encontrada para este usuário");
                                        break;
                                    }

                                    System.out.println("Carteira: ");
                                    carteiraa.verCarteira();
                                    break;

                                    //Investir com Cripto
                                case 6:




                            }
                        }
                    }
                    break;

                    //Sair do Sistema
                case 3:
                    System.out.println("Encerrando o sistema VOLTZ. Até mais!");
                    return;

                default:
                    System.out.println("Por favor, digite uma opção válida!");
            }
            System.out.println();
        }
    }
}

