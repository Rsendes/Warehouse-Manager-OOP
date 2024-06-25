package ggc.core.data;

import java.io.Serializable;

public class Data implements Serializable{

		/** Serial number for serialization. */
  	private static final long serialVersionUID = 202109192006L;

	private int _dias;

	public int obterDias(){
		return _dias;
	}

	public Data incrementaData(int incremento){
		_dias += incremento;
		return this;
	}

	public int calcularDiferencaDatas(Data outraData){
		return Math.abs( _dias - outraData.obterDias());
	}

	public Data obterDataAtual(){
		return this;
	}
}