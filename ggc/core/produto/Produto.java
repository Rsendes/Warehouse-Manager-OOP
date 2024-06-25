package ggc.core.produto;

import java.io.Serializable;
import ggc.core.lote.Lote;
import java.util.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;

/**
 * Representa o produto.
 */
public abstract class Produto implements Serializable{

	/** Serial number for serialization. */
  	private static final long serialVersionUID = 202109192006L;
  	
  	/** E o preco maximo de um produto num lote. */
	private double _precoMax;

	/** E o identificador do produto. */
	private String _id;

	/** E uma lista com os lotes que sao compostos por esse produto. */
	private List <Lote> _lotes;

	/**
	 * Construtor da classe produto.
	 * @param id e o identificador do produto.
	 */
	public Produto(String id){
		_id = id;
		_lotes = new ArrayList<>();
	}

	/**
	 * Obtem o preco maximo de um produto num lote.
	 * @return _precoMax e o preco a obter.
	 */
	public double obterPrecoMax(){
		return _precoMax;
	}

	/**
	 * Obtem o identificador de um dado produto.
	 * @return _id e o identificador a obter.
	 */
	public String obterId(){
		return _id;
	}

	/**
	 * Obtem a lista de lotes compostos por esse produto.
	 * @return _lotes e a lista de lotes a obter.
	 */
	public List <Lote> obterLotes(){
		return _lotes;
	}

	/**
	 * Obtem o stock total do produto.
	 * @return soma e o stock total a obter.
	 */
	public int obterStockTotal(){
		int soma = 0;
		for (Lote l: _lotes)
			soma += l.obterQuantidade();
		return soma;
	}

	/**
	 * Adiciona um lote ao fim da lista de lotes.
	 * @param lote e o lote a adicionar.
	 */
	public void adicionarLote(Lote lote){
		_lotes.add(lote);
	}

	/**
	 * Adiciona um lote num indice da lista.
	 * @param lote e o lote a adicionar.
	 * @param ind e o indice onde deve ser adicionado.
	 */
	public void adicionarLote(Lote lote, int ind){
		_lotes.add(ind, lote);
	}

	public void removerLote(Lote lote){
		_lotes.remove(lote);
	}

	/**
	 * Atualiza o preco maximo do produto.
	 * @param preco e o novo preco maximo.
	 */
	public void atualizarPreco(double preco){
		_precoMax = preco;
	}

	public int verificaQuantidade(int quantidade){
		int total = 0;

		for (Lote l : _lotes)
			total += l.obterQuantidade();

		return total - quantidade;
	}

	public double vendaProduto(int quantidade){
		double total = 0;
		int contQntd = quantidade;
		Iterator<Lote> iter = _lotes.iterator();

		while (iter.hasNext()){
			Lote l = iter.next();
			int qntdInicial = l.obterQuantidade();

			int qtdRemovida = l.removerQuantidade(contQntd);
			contQntd -= qtdRemovida;
			total += qtdRemovida * l.obterPreco();

			if (l.obterQuantidade() == 0)
				iter.remove();

			if (contQntd <= 0)
				break;
		}
	return total;
	}

	public double obterPrecoMin(){
		double preco = _precoMax;

		for (Lote l : _lotes)
			if (l.obterPreco() < preco)
				preco = l.obterPreco();

		return preco;
	}

	public double compraProduto(int quantidade, Parceiro parceiro){
		double preco = obterPrecoMin();

		adicionarLote(new Lote(preco, quantidade, this, parceiro));

		return preco;
	}

	public void compraProduto(int quantidade, Parceiro parceiro, double preco){
		if (preco > _precoMax)
			_precoMax = preco;
		adicionarLote(new Lote(preco, quantidade, this, parceiro));
	}
}