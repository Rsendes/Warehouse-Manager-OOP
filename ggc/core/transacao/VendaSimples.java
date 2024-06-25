package ggc.core.transacao;

import ggc.core.data.Data;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.produto.*;

public class VendaSimples extends Venda{
	private int _dataLimite;
	private double _quantiaPaga;
	private int _periodo;

	public VendaSimples(int id, int dataPagamento, double valorBase, int quantidade,
			Produto produto, Parceiro parceiro, int dataLimite, double quantiaPaga){
		super(id, dataPagamento, valorBase, quantidade, produto, parceiro);
		_dataLimite = dataLimite;
		_quantiaPaga = quantiaPaga;
		_periodo = 1;
	}

	public int obterDataLimite(){
		return _dataLimite;
	}

	public double obterQuantiaPaga(){
		return _quantiaPaga;
	}

	public double obterQuantiaPaga(Data dataAtual){
		atualizarPeriodo(dataAtual);
		return super.obterValorBase() * obterDesconto(dataAtual);
	}

	public double obterDesconto(Data dataAtual){
		String estatuto = super.obterParceiro().obterEstatuto();
		int difDias = dataAtual.obterDias() - _dataLimite;

		if (_periodo == 1)
			return 0.90;

		else if (_periodo == 2){
			if (estatuto.equals("NORMAL"))
				return 1;
			else if (estatuto.equals("SELECTION")){
				if (_dataLimite - dataAtual.obterDias() >= 2)
					return 0.95;
				else
					return 1;
			}
			else if (estatuto.equals("ELITE")){
				return 0.90;
			}
		}
		else if (_periodo == 3){
			if (estatuto.equals("ELITE"))
				return 0.95;
			else if (estatuto.equals("NORMAL"))
				return Math.pow(1.05, difDias);  
			else if (estatuto.equals("SELECTION")){
				if (difDias <= 1)
					return 1;
				else
					return Math.pow(1.02, difDias);
			}
		}
		else {
			if (estatuto.equals("NORMAL"))
				return Math.pow(1.10, difDias);
			else if (estatuto.equals("SELECTION"))
				return Math.pow(1.05, difDias);
			else if (estatuto.equals("ELITE"))
				return 1;
		}
		return 0;
	}

	public int obterPeriodo(){
		return _periodo;
	}

	public void pagar(Data dataAtual){
		_quantiaPaga = obterQuantiaPaga(dataAtual);
		alterarData(dataAtual.obterDias());
	}

	public void atualizarPeriodo(Data dataAtual){
		int n = 0;
		int diaPagamento = 0;

		if (super.obterDataPagamento() == -1)
			diaPagamento = dataAtual.obterDias();
		else
			diaPagamento = super.obterDataPagamento();

		if (super.obterProduto() instanceof ProdutoSimples)
			n = 5;
		else if (super.obterProduto() instanceof ProdutoDerivado)
			n = 3;

		if (_dataLimite - diaPagamento >= n)
			_periodo = 1;

		else if (_dataLimite - diaPagamento < n)
			_periodo = 2;

		else if (0 < diaPagamento - _dataLimite && diaPagamento - _dataLimite <= n)
			_periodo = 3;

		else if (diaPagamento - _dataLimite > n)
			_periodo = 4;
	}

	public String toString() {
		return String.format("VENDA|" + this.obterId() + "|" + this.obterParceiro().obterId() + "|" + this.obterProduto().obterId() + "|" + this.obterQuantidade()
				+ "|" + this.obterValorBase() + "|" + this.obterQuantiaPaga() + "|" + this.obterDataLimite() + "|" + this.obterDataPagamento());
	}

}