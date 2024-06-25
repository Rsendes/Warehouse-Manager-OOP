package ggc.core.produto;

import java.util.List;
import java.util.ArrayList;
import ggc.core.produto.Componente;
import ggc.core.produto.ProdutoDerivado;

/**
 * E a receita de um produto derivado.
 */
public class Receita{

	/** Lista de componentes da receita. */
	private List<Componente> _componentes;

	/** Produto a que a receita vai dar origem. */
	private ProdutoDerivado _produto;

	private double _agravamento;
	/**
	 * E o construtor da receita.
	 * @param componentes sao os componentes da receita.
	 * @param produto e o produto a criar.
	 */
	public Receita(List<Componente> componentes, ProdutoDerivado produto, double agravamento){
		_componentes = componentes;
		_produto = produto;
		_agravamento = agravamento;
	}

	/**
	 * Obtem os componentes da receita.
	 * @return _componentes e a lista de componentes a obter.
	 */
	public List<Componente> obterComponentes(){
		return _componentes;
	}

	/**
	 * Obtem o produto que a receita cria.
	 * @return produto e o produto a obter.
	 */
	public ProdutoDerivado obterProduto(){
		return _produto;
	}

	public double obterAgravamento(){
		return _agravamento;
	}

	public void alterarAgravamento(double novoAgravamento){
		_agravamento = novoAgravamento;
	}

	public int obterQntdComponente(String id){
		for (Componente c : _componentes)
			if (c.obterProduto().obterId().equals(id))
				return c.obterQuantidade();
		return 0;
	}
}