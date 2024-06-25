package ggc.core.lote;

import ggc.core.produto.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import java.io.Serializable;

/**
 * E um lote de um produto. 
 */
public class Lote implements Serializable{

	/** Serial number for serialization. */
  	private static final long serialVersionUID = 202109192006L;

  	/** E o preco do lote (ERRADO E O PRECO DE CADA UNIDADE)*/ 
	private double _preco;

	/** E a quantidade do produto no lote. */
	private int _quantidade;

	/** E o produto que compoe o lote. */
	private Produto _produto;

	/** E o parceiro associado ao lote. */
	private Parceiro _parceiro;

	/**
	 * E o construtor da classe Lote.
	 * @param preco e o preco do lote.
	 * @param quantidade e a quantidade do produto no lote.
	 * @param produto e o produto que compoe o lote.
	 * @param parceiro e o parceiro associado ao lote.
	 */
	public Lote(double preco, int quantidade, Produto produto, Parceiro parceiro){
		_preco = preco;
		_quantidade = quantidade;
		_produto = produto;
		_parceiro = parceiro;
	}

	/**
	 * Obtem o preco do lote.
	 * @return _preco e o preco a obter.
	 */
	public double obterPreco(){
		return _preco;
	}

	/**
	 * Obtem a quantidade do produto no lote.
	 * @return _quantidade e a quantidade a obter.
	 */
	public int obterQuantidade(){
		return _quantidade;
	}

	/**
	 * Obtem o produto que compoe o lote.
	 * @return _produto e o produto a obter.
	 */
	public Produto obterProduto(){
		return _produto;
	}

	/**
	 * Obtem o parceiro associado ao lote.
	 * @return _parceiro e o parceiro a obter.
	 */
	public Parceiro obterParceiro(){
		return _parceiro;
	}

	/**
	 * Copia o lote.
	 * @retun this e a copia do lote.
	 */
	public Lote copiarLote(){
		return this;
	}

	public int removerQuantidade(int quantidade){
		if (quantidade > _quantidade) {
			_quantidade = 0;
			return _quantidade;
		}else {
			_quantidade -= quantidade;
			return quantidade;
		}
	}

	public String toString() {
		return String.format(this.obterProduto().obterId() +"|" +this.obterParceiro().obterId()
           +"|" +(int)Math.round(this.obterPreco()) +"|" +this.obterQuantidade());
	}
}