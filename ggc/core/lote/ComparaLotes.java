package ggc.core.lote;

import ggc.core.produto.*;
import java.util.Comparator;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;


public class ComparaLotes implements Comparator<Lote>{

	public int compare(Lote a, Lote b){
		if (a.obterProduto().obterId().equals(b.obterProduto().obterId())){
			if (a.obterParceiro().obterId().equals(b.obterParceiro().obterId())){
				if ((a.obterPreco() == b.obterPreco()))
					return (int)(a.obterQuantidade() - b.obterQuantidade());
				return (int)(a.obterPreco() - b.obterPreco());
			}
			Comparator<String> stringComparator = String.CASE_INSENSITIVE_ORDER;
			return stringComparator.compare(a.obterParceiro().obterId(), b.obterParceiro().obterId());
		}
		Comparator<String> stringComparator = String.CASE_INSENSITIVE_ORDER;
		return stringComparator.compare(a.obterProduto().obterId(), b.obterProduto().obterId());
	}
}