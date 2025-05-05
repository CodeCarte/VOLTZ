package classes;

import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private int id;
    private Usuario usuario;

    private List<AlocacaoCripto> criptoAlocacoes;
    private List<AlocacaoInvestimento> investimentoAlocacoes;

    public Carteira() {
        this.criptoAlocacoes = new ArrayList<>();
        this.investimentoAlocacoes = new ArrayList<>();
    }

    public void verCarteira() {

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
