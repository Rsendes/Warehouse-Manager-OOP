package ggc.core;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import ggc.core.data.Data;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.produto.*;
import ggc.core.transacao.*;
import ggc.core.lote.Lote;
import java.io.Serializable;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  /** Data atual. */
  private Data _data;

  /** Numero da ultima transacao a ser efetuada. */
  private int _nrTransacao;

  /** Lista dos produtos existentes. */
  private List <Produto> _produtos;

  /** Lista dos parceiros existentes. */
  private List<Parceiro> _parceiros;

  /** Lista das transacoes existentes. */
  private List<Transacao> _transacoes;

  /**
   * Construtor da classe warehouse.
   */
  public Warehouse(){
    _data = new Data();
    _produtos = new ArrayList<>();
    _parceiros = new ArrayList<>();
    _transacoes = new ArrayList<>();
  }

  /**
   * Obtem a data atual.
   * @return _data e a data a obter.
   */
  Data obterData(){ // devia ser package
    return _data;
  }

  /**
   * Obtem o numero de transacao atual.
   * @return _nrTransacao e o numero de transacao a obter.
   */
  int obterNrTransacao(){
    return _nrTransacao;
  }

  /**
   * Obtem a lista de produtos existentes.
   * @return _produtos e a lista de produtos a obter.
   */
  List<Produto> obterProdutos(){
    return _produtos;
  }

  /**
   * Obtem a lista de parceiros existentes.
   * @return _parceiros e a lista de parceiros a obter.
   */
  List<Parceiro> obterParceiros(){
    return _parceiros;
  }

  /**
   * Obtem a lista de transacoes existentes.
   * @return _transacoes e a lista de transacoes a obter.
   */
  List<Transacao> obterTransacoes(){
    return _transacoes;
  }

  Transacao obterTransacao(int id){
    for (Transacao t : _transacoes)
      if (t.obterId() == id)
        return t;
    return null;
  }

  /**
   * Incrementa o numero de transacao atual em um.
   */
  void incrementarNrTransacao(){
    _nrTransacao++;
  }

  /**
   * Adiciona um parceiro a lista de parceiros existentes.
   * @param parceiro e o parceiro a adicionar a lista.
   */
  void adicionarParceiro(Parceiro parceiro){
    _parceiros.add(parceiro);
  }

  /**
   * Adiciona um produto a lista de produtos existentes.
   * @param produto e o produto a adicionar a lista.
   */
  public void adicionarProduto(Produto produto){
    _produtos.add(produto);
  }

  boolean verificaProduto(String id){
    for (Produto p : _produtos)
      if (p.obterId().equals(id))
        return true;
    return false;
  }

  boolean verificaParceiro(String id){
    for (Parceiro p : _parceiros)
      if (p.obterId().toLowerCase().equals(id.toLowerCase()))
        return true;
    return false;
  }

  Produto obterProduto(String id){
    for (Produto p : _produtos)
      if (p.obterId().equals(id))
        return p;
    return null;
  }

  public void adicionarTransacao(Transacao transacao){
    _transacoes.add(transacao);
  }

  Parceiro obterParceiro(String id){
    for (Parceiro p : _parceiros)
      if(p.obterId().equals(id))
        return p;
    return null;
  }

  public Produto obterProdutoEmFalta(Produto produto, int quantidade){
    for (int i = quantidade; i > 0; i--)
      for (Componente c : ((ProdutoDerivado)produto).obterReceita().obterComponentes())
        if (c.obterProduto().verificaQuantidade(c.obterQuantidade()) < 0)
          return c.obterProduto();
    return null;
  }

  public boolean verificaCompSuf(Produto produto, int quantidade){
    for (int i = quantidade; i > 0; i--)
      for (Componente c : ((ProdutoDerivado)produto).obterReceita().obterComponentes())
        if (c.obterProduto().verificaQuantidade(c.obterQuantidade()) < 0)
          return false;
    return true;
  }

  public void agrega(Produto produto, int quantidade, Parceiro parceiro){
    for (int i = quantidade; i > 0; i--)
      for (Componente c : ((ProdutoDerivado)produto).obterReceita().obterComponentes())
        c.obterProduto().vendaProduto(c.obterQuantidade());
    produto.compraProduto(quantidade, parceiro);
  }

  double obterSaldoDisponivel(){
    double gasto = 0;
    double recebido = 0;

    for (Transacao t : _transacoes)
      if (t instanceof Compra)
        gasto += t.obterValorBase();
      else if (t instanceof Desagregacao)
        recebido += t.obterValorBase();
      else if (t instanceof VendaSimples && ((VendaSimples)t).obterQuantiaPaga() != 0)
        recebido += ((VendaSimples)t).obterQuantiaPaga();

    return recebido - gasto;
  }

  double obterSaldoContabilistico(){
    double gasto = 0;
    double recebido = 0;

    for (Transacao t : _transacoes)
      if (t instanceof Compra)
        gasto += t.obterValorBase();
      else if (t instanceof Desagregacao)
        recebido += t.obterValorBase();
      else if (t instanceof VendaSimples)
        if (((VendaSimples)t).obterQuantiaPaga() != 0)
          recebido += ((VendaSimples)t).obterQuantiaPaga();
        else
          recebido += ((VendaSimples)t).obterQuantiaPaga(_data);

    return recebido - gasto;
  }

  /**
   * Importa um ficheiro no inicio da execucao do programa.
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException{
    Parser parser = new Parser(this);
    parser.parseFile(txtfile);
  }

  boolean verificaTipoVenda(Venda v) {
    if (v instanceof VendaSimples && ((VendaSimples)v).obterQuantiaPaga() != 0)
          return true;
      return false;
  }

}