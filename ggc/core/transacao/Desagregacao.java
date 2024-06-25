package ggc.core.transacao;

import ggc.core.data.Data;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.produto.*;

public class Desagregacao extends Venda{

	public Desagregacao(int id, int dataPagamento, double valorBase, int quantidade,
			Produto produto, Parceiro parceiro){
		super(id, dataPagamento, valorBase, quantidade, produto, parceiro);
	}
}