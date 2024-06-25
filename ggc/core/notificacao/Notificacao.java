package ggc.core.notificacao;

import java.io.Serializable;
import ggc.core.produto.*;

public interface Notificacao extends Serializable{

			/** Serial number for serialization. */
  	long serialVersionUID = 202109192006L;
	
	String obterTipo();

	Produto obterProduto();

	void alterarTipo(String tipo);

	String toString();
}