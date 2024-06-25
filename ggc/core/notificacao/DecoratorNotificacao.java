package ggc.core.notificacao;

import ggc.core.produto.*;

public abstract class DecoratorNotificacao implements Notificacao{

	private String _tipo;
	private Produto _produto;
	private String _meioEnvio;

	public DecoratorNotificacao(String tipo, Produto produto, String meioEnvio){
		_tipo = tipo;
		_produto = produto;
		_meioEnvio = meioEnvio;
	}

	public String obterTipo(){
		return _tipo;
	}

	public String obterMeioEnvio(){
		return _meioEnvio;
	}

	public Produto obterProduto(){
		return _produto;
	}

	public void alterarTipo(String tipo){
		_tipo = tipo;
	} 

	public String toString() {
		return "";
	}
}