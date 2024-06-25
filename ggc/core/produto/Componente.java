package ggc.core.produto;

import ggc.core.produto.*;

/**
 * E um produto que faz parte de uma receita.
 */
public class Componente{

	/** E o produto a que o componente corresponde. */
	private Produto _produto;

	/** E a quantidade do produto necessaria para a receita. */
	private int _quantidade;


	/**
	 * Construtor do componente.
	 * @param produto e o produto a que o componente corresponde.
	 * @param quantidade e a quantidade do produto necessaria para a receita.
	 */
	public Componente(Produto produto, int quantidade){
		_produto = produto;
		_quantidade = quantidade;
	}

	/**
	 * Obtem o produto associada ao componente.
	 * @return _produto e o produto a obter.
	 */
	public Produto obterProduto(){
		return _produto;
	}

	/**
	 * Obtem a quantidade de um produto necessaria para a receita.
	 * @return _quantidade e a quantidade a obter.
	 */
	public int obterQuantidade(){
		return _quantidade;
	}
}