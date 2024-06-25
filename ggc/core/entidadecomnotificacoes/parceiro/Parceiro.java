package ggc.core.entidadecomnotificacoes.parceiro;

import ggc.core.lote.Lote;
import ggc.core.notificacao.Notificacao;
import ggc.core.transacao.*;
import ggc.core.produto.*;
import java.util.*;
import ggc.core.entidadecomnotificacoes.EntidadeComNotificacoes;

public class Parceiro extends EntidadeComNotificacoes{

	private String _nome;
	private String _morada;
	private String _id;
	private String _estatuto;
	private double _pontos;
	private List <Lote> _lotes;
	private List<Compra> _compras;
	private List<Venda> _vendas;
	private PoliticaRecompensa _estrategia;

	public Parceiro(String nome, String morada, String id, PoliticaRecompensa estrategia){
		super();
		_nome = nome;
		_morada = morada;
		_id = id;
		_estatuto = "NORMAL";
		_lotes = new ArrayList<>();
		_compras = new ArrayList<>();
		_vendas = new ArrayList<>();
		_estrategia = estrategia;
	}

	public String obterNome(){
		return _nome;
	}

	public String obterMorada(){
		return _morada;
	}

	public String obterId(){
		return _id;
	}

	public String obterEstatuto(){
		return _estatuto;
	}

	public double obterPontos(){
		return _pontos;
	}

	public List <Lote> obterLotes(){
		return _lotes;
	}

	public List<Compra> obterCompras(){
		return _compras;
	}

	public List<Venda> obterVendas(){
		return _vendas;
	}

	public Venda obterVenda(int id){
		for (Venda v : _vendas)
			if (v.obterId() == id)
				return v;
		return null;
	}

	public boolean verificaVenda(int id){
		for (Venda v : _vendas)
			if (v.obterId() == id)
				return true;
		return false;
	}

	public void alterarEstatuto(String estatuto){
		_estatuto = estatuto;
	}

	public void adicionaPontos(double incremento){
		if (_estrategia instanceof PoliticaRecompensaDefault){
			_pontos = ((PoliticaRecompensaDefault)_estrategia).incrementarPontos(incremento, _pontos);
			_estatuto = ((PoliticaRecompensaDefault)_estrategia).atualizarEstatuto(_pontos, _estatuto);
		}
	}

	public void retiraPontos(int difDias){
		if (_estrategia instanceof PoliticaRecompensaDefault){
			double modificador = ((PoliticaRecompensaDefault)_estrategia).decrementarPontos(difDias, _estatuto);
			_pontos = ((PoliticaRecompensaDefault)_estrategia).modificaPontos(_pontos, modificador);
			_estatuto = ((PoliticaRecompensaDefault)_estrategia).decrementarEstatuto(modificador);
		}
	}

	public void adicionarLote(Lote lote){
		_lotes.add(lote);
	}

	public void removerLote(Lote lote){
		_lotes.remove(lote);
	}

	public void adicionarCompra(Compra compra){
		_compras.add(compra);
	}

	public void adicionarVenda(Venda venda){
		_vendas.add(venda);
	}

	public double obterValorTotalCompras(){
		double total = 0;
		for (Compra c : _compras)
			total += c.obterValorBase();
		return total;
	}

	public double obterValorVendasPagas(){
		double total = 0;
		for(Venda v : _vendas)
			if (v instanceof VendaSimples)
				total += ((VendaSimples)v).obterQuantiaPaga();
		return total;
	}

	public double obterValorVendasEfetuadas(){
		double total = 0;
		for (Venda v : _vendas)
			if (v instanceof VendaSimples)
				total += v.obterValorBase();
		return total;
	}

	public String toString() {
		return String.format(this.obterId() +"|" +this.obterNome() +"|" +this.obterMorada()
            +"|" +this.obterEstatuto() +"|" +(int)Math.round(this.obterPontos()) + "|" +(int)Math.round(this.obterValorTotalCompras())
            +"|" +(int)Math.round(this.obterValorVendasEfetuadas()) +"|" +(int)Math.round(this.obterValorVendasPagas()));
	}
}