package ggc.core.entidadecomnotificacoes.parceiro;

import java.io.Serializable;

public interface PoliticaRecompensa extends Serializable{

	/** Serial number for serialization. */
  long serialVersionUID = 202109192006L;

	public double incrementarPontos(double incremento, double pontos);

	public double decrementarPontos(int difDias, String estatuto);
}