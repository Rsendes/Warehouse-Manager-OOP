package ggc.core.transacao;

import ggc.core.data.Data;
import ggc.core.produto.*;
import java.io.Serializable;

public abstract class Transacao implements Serializable{

		/** Serial number for serialization. */
  	private static final long serialVersionUID = 202109192006L;

	private int _id;
	private int _dataPagamento;
	private double _valorBase;
	private int _quantidade;
	private Produto _produto;

	public Transacao(int id, int dataPagamento, double valorBase, int quantidade, Produto produto){
		_id = id;
		_dataPagamento = dataPagamento;
		_valorBase = valorBase;
		_quantidade = quantidade;
		_produto = produto;
	}

	public int obterId(){
		return _id;
	}

	public int obterDataPagamento(){
		return _dataPagamento;
	}

	public double obterValorBase(){
		return _valorBase;
	}

	public int obterQuantidade(){
		return _quantidade;
	}

	public Produto obterProduto(){
		return _produto;
	}

	public void alterarData(int novaData){
		_dataPagamento = novaData;
	}
}