package ggc.core.entidadecomnotificacoes.parceiro;

import java.util.Comparator;

public class ComparaParceiros implements Comparator<Parceiro>{

	public int compare(Parceiro a, Parceiro b){
		Comparator<String> stringComparator = String.CASE_INSENSITIVE_ORDER;
		return stringComparator.compare(a.obterId(), b.obterId());
	}
}