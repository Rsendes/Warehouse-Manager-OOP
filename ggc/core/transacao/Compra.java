package ggc.core.transacao;

import ggc.core.data.Data;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.produto.*;

public class Compra extends Transacao{

	private Parceiro _parceiro;

	public Compra(int id, int dataPagamento, double valorBase, int quantidade, Produto produto, Parceiro parceiro){
		super(id, dataPagamento, valorBase, quantidade, produto);
		_parceiro = parceiro;
	}

	public Parceiro obterParceiro(){
		return _parceiro;
	}
}