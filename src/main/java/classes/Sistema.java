package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    public static void main(String[] args) {
        System.out.println("Olá, bem vindo ao sistema da Zypto!");
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

                //Criação de
                case 1:
                    try {
                        System.out.println("Digite o nome de Usuário: ");
                        String nomeUsuario = myScanner.nextLine().trim();

                        if (nomeUsuario.isEmpty()) {
                            System.out.println("❌ Nome de usuário não pode ser vazio.");
                            break;
                        }
                        if (BancoDeDados.buscarUsuarioPorNome(nomeUsuario) != null) {
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
                        if (BancoDeDados.emailJaExiste(emailUsuario)) {
                            System.out.print("❌ Email já está em uso. Tente outro\n");
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
                            BancoDeDados.salvarUsuario(usuario);
                            int idCarteira = BancoDeDados.salvarCarteira(usuario.getId());
                            Carteira carteira = new Carteira();
                            carteira.setId(idCarteira);
                            carteira.setUsuario(usuario);
                            usuario.setCarteira(carteira);
                            System.out.println("A sua conta foi criada com Sucesso!");
                            break;
                        } catch (Exception e) {
                            System.out.println("❌ Ocorreu um erro ao criar o usuário: " + e.getMessage());
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Erro ao criar a conta: " + e.getMessage());
                        break;
                    }

                //Logar na Conta
                case 2:
                    try {
                        System.out.println("Digite o nome de Usuário: ");
                        String nomeLogin = myScanner.nextLine().trim();

                        System.out.println("Digite a senha: ");
                        String senhaLogin = myScanner.nextLine().trim();

                        Sessao sessao = new Sessao();
                        String respostaLogin = sessao.autenticarCredenciais(nomeLogin, senhaLogin);
                        System.out.println(respostaLogin);

                        if (Sessao.usuarioAutenticado != null) {
                            Carteira carteira = BancoDeDados.buscarCarteiraPorUsuarioId(Sessao.usuarioAutenticado.getId());
                            Sessao.usuarioAutenticado.setCarteira(carteira);
                            System.out.println("Bem vindo(a), " + Sessao.usuarioAutenticado.getNomeUsuario() + "!");

                            OUTER_LOGADO:
                            while (true) {
                                try {
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
                                            try {
                                                System.out.println("Insira um Valor decimal: ");
                                                double valorBRL = myScanner.nextDouble();
                                                myScanner.nextLine();

                                                Usuario usuario = Sessao.usuarioAutenticado;
                                                usuario.adicionarSaldo(valorBRL);
                                                BancoDeDados.atualizarSaldoUsuario(usuario);
                                                System.out.println("✅ Saldo adicionado com sucesso!");
                                                System.out.println("Saldo atual: R$ " + usuario.getSaldo());
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("❌ Falha ao adicionar saldo: " + e.getMessage());
                                                break;
                                            }

                                        //Ver Saldo
                                        case 2:
                                            try {
                                                Usuario mesmoUsuarioo = Sessao.usuarioAutenticado;
                                                System.out.println("O saldo atual é: " + mesmoUsuarioo.getSaldo() + "\n");
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("❌ Falha ao ver Saldo: " + e.getMessage());
                                                break;
                                            }

                                        //Comprar Criptomoeda com Saldo
                                        case 3:
                                            try {
                                                System.out.print("Digite a Sigla da Criptomoeda que deseja comprar (ex: BTC): ");
                                                String siglaCripto = myScanner.nextLine().toUpperCase();

                                                Criptomoeda criptoSelecionada = BancoDeDados.buscarCriptomoedaPorSigla(siglaCripto);

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
                                                    System.out.println("❌ Valor inválido. Use ponto ou vírgula corretamente.");
                                                    break;
                                                }

                                                Usuario mesmoUsuario = Sessao.usuarioAutenticado;

                                                if (mesmoUsuario.getSaldo() < valorInvestido) {
                                                    System.out.println("❌ Saldo insuficiente. Seu saldo é de R$ " + mesmoUsuario.getSaldo());
                                                    break;
                                                }

                                                if (precoUnitario <= 0) {
                                                    System.out.println("❌ Erro: O preço unitário da criptomoeda está inválido (R$ " + precoUnitario + ").");
                                                    break;
                                                }

                                                double quantidade = valorInvestido / precoUnitario;
                                                System.out.printf("Você irá comprar aproximadamente %.8f %s. Confirmar? (S/N): ",
                                                        quantidade, siglaCripto);
                                                String confirmar = myScanner.nextLine().trim();

                                                if (!confirmar.equalsIgnoreCase("S")) {
                                                    System.out.println("❌ Compra cancelada.");
                                                    break;
                                                }

                                                BancoDeDados.salvarAlocacaoCripto(mesmoUsuario.getCarteira().getId(), criptoSelecionada.getId(), quantidade);
                                                mesmoUsuario.removerSaldo(valorInvestido);
                                                mesmoUsuario.getCarteira().adicionarCripto(criptoSelecionada, quantidade);
                                                BancoDeDados.atualizarSaldoUsuario(mesmoUsuario);

                                                //Registrar o Pedido de Compra (Ordem):
                                                Ordem ordem = new Ordem();
                                                ordem.setUsuario(mesmoUsuario);
                                                ordem.setAtivo(criptoSelecionada);
                                                ordem.setTipo(TipoOrdem.COMPRA);
                                                ordem.setPrecoUnitario(precoUnitario);
                                                ordem.setQuantidade(quantidade);
                                                ordem.setStatus(StatusOrdem.EXECUTADA);
                                                ordem.setDataHora(LocalDateTime.now());
                                                BancoDeDados.salvarOrdem(ordem);

                                                //Registrar Transacao (Transacao):
                                                Transacao transacao = new Transacao();
                                                transacao.setOrdem(ordem);
                                                transacao.setUsuario(mesmoUsuario);
                                                transacao.setQuantidade(quantidade);
                                                transacao.setPrecoUnitario(precoUnitario);
                                                transacao.setDataHora(LocalDateTime.now());
                                                BancoDeDados.salvarTransacao(transacao);
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("❌ Falha ao comprar Criptomoeda com Saldo: " + e.getMessage());
                                            }
                                        //Listar Criptomoedas
                                        case 4:
                                            try {
                                                List<Criptomoeda> criptos = BancoDeDados.listarCriptomoedas();

                                                if (criptos.isEmpty()) {
                                                    System.out.println("❌ Nenhuma criptomoeda disponível.");
                                                } else {
                                                    System.out.println("Criptomoedas disponíveis: ");
                                                    for (Criptomoeda c : criptos) {
                                                        System.out.println("🔹 " + c.getNome() + " (" + c.getSigla() + ") - Preço Unitário: R$ " + c.getPrecoUnitario());
                                                    }
                                                }
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("❌ Falha ao Listar Criptomoedas: " + e.getMessage());
                                                break;
                                            }

                                        //Ver Carteira
                                        case 5:
                                            try {
                                                Usuario mesmoUsuario3 = Sessao.usuarioAutenticado;
                                                Carteira carteiraa = BancoDeDados.carregarCarteiraCompletaDoUsuario(mesmoUsuario3.getId());
                                                mesmoUsuario3.setCarteira(carteiraa);

                                                if (carteiraa == null) {
                                                    System.out.println("Nenhuma carteira encontrada para este usuário");
                                                    break;
                                                }

                                                System.out.println("Carteira: ");
                                                carteiraa.verCarteira();
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("❌ Falha ao ver Carteira: " + e.getMessage());
                                                break;
                                            }

                                        //Investir com Cripto
                                        case 6:
                                            try {
                                                Usuario mesmoUsuario4 = Sessao.usuarioAutenticado;
                                                Carteira carteira2 = BancoDeDados.carregarCarteiraCompletaDoUsuario(mesmoUsuario4.getId());

                                                List<AlocacaoCripto> criptoAlocacoes = carteira2.getCriptoAlocacoes();

                                                if (criptoAlocacoes.isEmpty()) {
                                                    System.out.println("Você não tem nenhuma Criptomoeda para investir");
                                                    break;
                                                }

                                                System.out.println("Criptomoedas disponíveis: ");
                                                for (int i = 0; i < criptoAlocacoes.size(); i++) {
                                                    AlocacaoCripto aloc = criptoAlocacoes.get(i);
                                                    System.out.printf("%d - %s (%s): %.8f unidades\n", i + 1,
                                                            aloc.getCriptoAtivo().getNome(),
                                                            aloc.getCriptoAtivo().getSigla(),
                                                            aloc.getQuantidade());
                                                }

                                                System.out.print("Digite o número da criptomoeda que deseja usar para investir: ");
                                                int escolha = myScanner.nextInt();

                                                if (escolha < 1 || escolha > criptoAlocacoes.size()) {
                                                    System.out.println("Opção Inválida.");
                                                    break;
                                                }

                                                AlocacaoCripto selecionada = criptoAlocacoes.get(escolha - 1);

                                                System.out.printf("Você escolheu: %s (%s)\n",
                                                        selecionada.getCriptoAtivo().getNome(),
                                                        selecionada.getCriptoAtivo().getSigla());

                                                System.out.println("Escolha a Modalidade do Investimento: (Ex: ACAO, FII, ETF)");
                                                String tipoInvestimento = myScanner.next().toUpperCase();
                                                myScanner.nextLine();

                                                if (!tipoInvestimento.equalsIgnoreCase("ACAO")) {
                                                    System.out.println("Trabalho em progresso para a modalidade: " + tipoInvestimento);
                                                    break;
                                                }

                                                List<Investimento> investimentos = BancoDeDados.buscarInvestimentosPorTipo("ACAO");

                                                if (investimentos.isEmpty()) {
                                                    System.out.println("❌ Nenhuma Ação foi encontrada no banco de dados.");
                                                    break;
                                                }

                                                System.out.println("Escolha a Ação que deseja investir: ");
                                                for (int i = 0; i < investimentos.size(); i++) {
                                                    Investimento inv = investimentos.get(i);
                                                    System.out.printf("%d - %s (%s): Preço: R$ %.2f\n", i + 1,
                                                            inv.getNome(),
                                                            inv.getSigla(),
                                                            inv.getPrecoUnitario());
                                                }
                                                int escolhaAcao = myScanner.nextInt();
                                                myScanner.nextLine();

                                                if (escolhaAcao < 1 || escolhaAcao > investimentos.size()) {
                                                    System.out.println("Opção de Ação inválida!");
                                                    break;
                                                }

                                                Investimento investimentoSelecionado = investimentos.get(escolhaAcao - 1);

                                                System.out.println("Digite quantas unidades da ação deseja comprar: ");
                                                int quantidadeAcao = myScanner.nextInt();
                                                myScanner.nextLine();

                                                double totalInvestimentoBRL = investimentoSelecionado.getPrecoUnitario() * quantidadeAcao;
                                                double precoCriptoBRL = selecionada.getCriptoAtivo().getPrecoUnitario();
                                                double quantidadeCriptoNecessaria = totalInvestimentoBRL / precoCriptoBRL;

                                                System.out.printf("Deseja investir na Ação %s com a Criptomoeda %s?\n",
                                                        investimentoSelecionado.getSigla(), selecionada.getCriptoAtivo().getSigla());
                                                System.out.printf("Serão usados %.8f %s (R$ %.2f) para comprar %d unidades de %s (R$ %.2f)\n",
                                                        quantidadeCriptoNecessaria,
                                                        selecionada.getCriptoAtivo().getSigla(),
                                                        totalInvestimentoBRL,
                                                        quantidadeAcao,
                                                        investimentoSelecionado.getSigla(),
                                                        investimentoSelecionado.getPrecoUnitario());

                                                System.out.print("Confirmar? (S/N): ");
                                                String confirmarConversa = myScanner.nextLine().trim();

                                                if (!confirmarConversa.equalsIgnoreCase("S")) {
                                                    System.out.println("Conversão cancelada.");
                                                    break;
                                                }

                                                if (selecionada.getQuantidade() < quantidadeCriptoNecessaria) {
                                                    System.out.println("❌ Você não possui cripto suficiente para realizar essa conversão.");
                                                    break;
                                                }

                                                //Remove Cripto
                                                selecionada.removerQuantidade(quantidadeCriptoNecessaria);

                                                //Adiciona Investimento na Carteira
                                                AlocacaoInvestimento novaAlocacao = new AlocacaoInvestimento();
                                                novaAlocacao.setInvestimento(investimentoSelecionado);
                                                novaAlocacao.setQuantidade(quantidadeAcao);
                                                carteira2.getInvestimentoAlocacoes().add(novaAlocacao);

                                                //Atualizar Carteira no BancoDeDados
                                                BancoDeDados.salvarAlocacaoInvestimento(carteira2.getId(), investimentoSelecionado.getId(), quantidadeAcao);
                                                BancoDeDados.salvarAlocacaoCripto(carteira2.getId(), selecionada.getCriptoAtivo().getId(), -quantidadeCriptoNecessaria);

                                                //Registrar Ordem
                                                Ordem ordem2 = new Ordem();
                                                ordem2.setUsuario(mesmoUsuario4);
                                                ordem2.setTipo(TipoOrdem.COMPRA);
                                                ordem2.setAtivo(investimentoSelecionado);
                                                ordem2.setPrecoUnitario(investimentoSelecionado.getPrecoUnitario());
                                                ordem2.setStatus(StatusOrdem.EXECUTADA);
                                                ordem2.setDataHora(LocalDateTime.now());
                                                BancoDeDados.salvarOrdem(ordem2);

                                                //Registrar Transação
                                                Transacao transacao2 = new Transacao();
                                                transacao2.setUsuario(mesmoUsuario4);
                                                transacao2.setOrdem(ordem2);
                                                transacao2.setQuantidade(quantidadeAcao);
                                                transacao2.setPrecoUnitario(investimentoSelecionado.getPrecoUnitario());
                                                transacao2.setDataHora(LocalDateTime.now());
                                                BancoDeDados.salvarTransacao(transacao2);

                                                System.out.println("\n✅ Conversão efetuada com Sucesso!");
                                                System.out.println("✅ " + quantidadeAcao + " unidades da Ação " + investimentoSelecionado.getSigla() + " foram adicionadas à sua carteira.");
                                                System.out.println("✅ Quantidade equivalente da criptomoeda " + selecionada.getCriptoAtivo().getSigla() + " foi removida da carteira.");
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("❌ Erro ao Investir com Cripto: " + e.getMessage());
                                                break;
                                            }

                                        case 7:
                                            System.out.println("Saindo da conta...");
                                            Sessao.usuarioAutenticado = null;
                                            break OUTER_LOGADO;
                                    }
                                } catch (Exception e) {
                                    System.out.println("❌ Ocorreu um erro no Menu de Login: " + e.getMessage());
                                    myScanner.nextLine();
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("❌ Erro ao logar na conta: " + e.getMessage());
                        break;
                    }
                    break;

                    //Sair do Sistema
                case 3:
                        System.out.println("Encerrando o sistema VOLTZ. Até mais!");
                        return;

                default:
                    System.out.println("Por favor, digite uma opção válida!");
                    System.out.println();
            }
        }
    }
}
