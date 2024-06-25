package ggc.core.produto;

/**
 * E um produto do tipo simples.
 */
public class ProdutoSimples extends Produto{

	/**
	 * E o construtor do produto simples.
	 * @param id e o identificador do produto.
	 */
	public ProdutoSimples(String id){
		super(id);
	}

	public String toString() {
		return String.format(this.obterId() +"|" +(int)Math.round(this.obterPrecoMax()) +"|"
          +this.obterStockTotal());
	}
}