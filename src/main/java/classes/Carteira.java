package classes;

import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private int id;
    private Usuario usuario;

    private List<AlocacaoCripto> criptoAlocacoes;
    private List<AlocacaoInvestimento> investimentoAlocacoes;

    public Carteira(Usuario usuario) {
        this.criptoAlocacoes = new ArrayList<>();
        this.investimentoAlocacoes = new ArrayList<>();
    }

    public Carteira() {
        this.criptoAlocacoes = new ArrayList<>();
        this.investimentoAlocacoes = new ArrayList<>();
    }

    //--GETTERS--
    public int getId() {
        return this.id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public List<AlocacaoCripto> getCriptoAlocacoes() {
        return this.criptoAlocacoes;
    }

    public List<AlocacaoInvestimento> getInvestimentoAlocacoes() {
        return this.investimentoAlocacoes;
    }

    //--SETTERS--
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void verCarteira() {
        if (this.criptoAlocacoes.isEmpty()) {
            System.out.println("Nenhuma criptomoeda alocada.");
        } else {
            System.out.println("Criptomoedas na carteira: ");
            for (AlocacaoCripto alocacao : criptoAlocacoes) {
                System.out.printf("🔸 %s (%s): %.8f unidades%n",
                        alocacao.getCriptoAtivo().getNome(),
                        alocacao.getCriptoAtivo().getSigla(),
                        alocacao.getQuantidade()
                );
            }
        }
        System.out.println();

        if (investimentoAlocacoes.isEmpty()) {
            System.out.println("Nenhum investimento alocado.");
        } else {
            System.out.println("📈 Investimentos na carteira: ");
            for (AlocacaoInvestimento alocacao : investimentoAlocacoes) {
                System.out.printf("%s: R$ %.2f aplicados%n",
                        alocacao.getInvestimento().getNome(),
                        alocacao.getQuantidade()
                );
            }
        }
    }

    public void adicionarCripto(Criptomoeda cripto, double quantidade) {
        for (AlocacaoCripto alocacao : this.criptoAlocacoes) {
            if (alocacao.getCriptoAtivo().getId() == cripto.getId()) {
                alocacao.adicionarQuantidade(quantidade);
                return;
            }
        }
        AlocacaoCripto novaAlocacao = new AlocacaoCripto();
        novaAlocacao.setCriptoAtivo(cripto);
        novaAlocacao.setQuantidade(quantidade);
        criptoAlocacoes.add(novaAlocacao);
    }
}
