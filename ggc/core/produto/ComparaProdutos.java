package ggc.core.produto;

import ggc.core.produto.*;
import java.util.Comparator;

public class ComparaProdutos implements Comparator<Produto>{

	public int compare(Produto a, Produto b){
		Comparator<String> stringComparator = String.CASE_INSENSITIVE_ORDER;
		return stringComparator.compare(a.obterId(), b.obterId());
	}
}