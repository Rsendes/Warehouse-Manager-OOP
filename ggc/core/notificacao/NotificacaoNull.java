package ggc.core.notificacao;

import ggc.core.produto.*;

public class NotificacaoNull implements Notificacao{

	private Produto _produto;

	public NotificacaoNull(Produto produto){
		_produto = produto;
	}

	public Produto obterProduto(){
		return _produto;
	}

	public String obterTipo(){
		return null;
	}

	public void alterarTipo(String tipo){}

	public String toString() {
		return "";
	}
}