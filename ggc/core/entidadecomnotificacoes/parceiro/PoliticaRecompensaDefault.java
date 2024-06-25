package ggc.core.entidadecomnotificacoes.parceiro;

public class PoliticaRecompensaDefault implements PoliticaRecompensa {

	public static final double PONTOS_SELECTION = 2000;
	public static final double PONTOS_ELITE = 25000;

	public double incrementarPontos(double incremento, double pontos){
		return pontos + incremento;
	}

	public String atualizarEstatuto(double pontos, String estatuto){

		if (estatuto.equals("NORMAL")){
			if (PONTOS_ELITE < pontos)
				return "ELITE";

			else if (PONTOS_SELECTION < pontos)
				return "SELECTION";
		}
		else if (estatuto.equals("SELECTION") && PONTOS_ELITE < pontos)
			return "ELITE";
		return "NORMAL";
	}

	public String decrementarEstatuto(double modificador){

		if (modificador == 0 || modificador == 0.1)
			return "NORMAL";
		else if (modificador == 0.25)
			return "SELECTION";
		return "NORMAL";
	}

	public double modificaPontos(double pontos, double modificador){

		return pontos * modificador;
	}

	public double decrementarPontos(int difDias, String estatuto){
	
		if (estatuto.equals("NORMAL"))
			return 0;

		else if (estatuto.equals("SELECTION") && difDias > 2)
			return 0.1;

		else if (estatuto.equals("ELITE") && difDias > 15)
			return 0.25;
		return 0;
	}
}