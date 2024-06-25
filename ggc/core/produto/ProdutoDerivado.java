package ggc.core.produto;

import java.util.List;
import java.util.ArrayList;
import ggc.core.produto.Receita;
import ggc.core.produto.Componente;

/**
 * E um produto do tipo derivado.
 */
public class ProdutoDerivado extends Produto{

	/** E a receita de um produto derivado. */
	private Receita _receita;

	/**
	 * Construtor do produto derivado.
	 * @param id e o identificador do produto.
	 * @param componentes sao os componentes necessarios para o produto derivado.
	 */
	public ProdutoDerivado(String id, List<Componente> componentes, double agravamento){
		super(id);
		_receita = new Receita(componentes, this, agravamento);
	}

	/**
	 * Obtem a receita do produto derivado.
	 * @return _receita e a receita a obter.
	 */
	public Receita obterReceita(){
		return _receita;
	}

	public String toString() {
		String s = new String("");
        for(Componente c : ((ProdutoDerivado)this).obterReceita().obterComponentes())
          s += c.obterProduto().obterId() + ":" +c.obterQuantidade() +"#";
        s = s.substring(0, s.length() - 1);
        return String.format(this.obterId() +"|" +(int)Math.round(this.obterPrecoMax()) +"|"
          +this.obterStockTotal() +"|" +s);
	}
}