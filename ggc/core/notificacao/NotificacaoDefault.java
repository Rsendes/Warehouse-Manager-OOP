package ggc.core.notificacao;

import ggc.core.produto.*;

public class NotificacaoDefault implements Notificacao{
	
	private String _tipo;
	private Produto _produto;

	public NotificacaoDefault(String tipo, Produto produto){
		_tipo = tipo;
		_produto = produto;
	}

	public String obterTipo(){
		return _tipo;
	}

	public Produto obterProduto(){
		return _produto;
	}

	public void alterarTipo(String tipo){
		_tipo = tipo;
	}

	public String toString() {
		return String.format(this.obterTipo() +"|" +this.obterProduto().obterId() +"|"
                +(int)Math.round(this.obterProduto().obterPrecoMin()));
	}
}