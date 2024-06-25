package ggc.core;

/**
Grupo 51
  * @author Filipe Resendes 96859
  * @author Rafael Ferreira 90173
*/

import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;
import java.io.*;
import java.util.*;
import ggc.core.data.Data;
import ggc.core.transacao.*;
import ggc.core.entidadecomnotificacoes.EntidadeComNotificacoes;
import ggc.core.notificacao.*;
import ggc.core.entidadecomnotificacoes.parceiro.*;
import ggc.core.produto.*;
import ggc.core.lote.*;

/** Façade for access. */
public class WarehouseManager{

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  /**
   * Construtor do WarehouseManager quando e importado um ficheiro.
   * @param filename e o nome do ficheiro a importar.
   */
  public WarehouseManager(String filename){
    _filename = filename;
  }

  /**
   * Construtor do WarehouseManager quando nao e importado ficheiro.
   */
  public WarehouseManager(){
    _filename = null;
  }

  /**
   * Obtem atributo Warehouse.
   * @return _warehouse e a Warehouse a devolver.
   */
  public Warehouse obterWarehouse(){
    return _warehouse;
  }

  /**
   * Obtem o atributo filename
   * @return _filename e o nome do ficheiro a devolver.
   */
  public String obterNomeFicheiro(){
    return _filename;
  }

  /**
   * Guarda a warehouse serializada num ficheiro. 
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    File f = new File(_filename);
    FileOutputStream file = new FileOutputStream(f);
    ObjectOutputStream out = new ObjectOutputStream(file);
    out.writeObject(_warehouse);
    out.flush();
    out.close();
    file.close();
  }

  /**
   * Guarda warehouse serializada num ficheiro a definir.
   * @param filename e o nome do ficheiro onde queremos guardar a warehouse.
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * Importa a warehouse serializada contida num ficheiro.
   * @param filename e o nome do ficheiro de onde se quer importar.
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, ClassNotFoundException  {
    _filename = filename;
    File f = new File(filename);
    try {
      FileInputStream file = new FileInputStream(f);
      ObjectInputStream in = new ObjectInputStream(file);
      _warehouse = (Warehouse)in.readObject();
      in.close();
      file.close();
    } catch (IOException e){
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * Importa um ficheiro de texto no inicio da execucao do programa.
   * @param textfile e o nome do ficheiro de texto.
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }
  public boolean verificarDataPositiva(int dias) {
    if (dias > 0) {
      return true;
    }
    return false;
  }

  public void avancarData(int dias) {
    this.obterWarehouse().obterData().incrementaData(dias);
  }

  public Data obterDataAtual() {
    return this.obterWarehouse().obterData();
  }

  public int mostrarDataAtual() {
    return this.obterWarehouse().obterData().obterDias();
  }

  public double mostrarSaldoDisponivel() {
    return this.obterWarehouse().obterSaldoDisponivel();
  }

  public double mostrarSaldoContabilistico() {
    return this.obterWarehouse().obterSaldoContabilistico();
  }

  public boolean verificaExistenciaParceiro(String idparceiro) {
    return this.obterWarehouse().verificaParceiro(idparceiro);
  }

  public boolean verificaVendaSimples(Venda venda) {
    return this.obterWarehouse().verificaTipoVenda(venda);
  }


  public List<Venda> getVendasParceiro(Parceiro parceiro) {
    return parceiro.obterVendas();
  }

  public void sortProdutos() {
    Collections.sort(this.obterWarehouse().obterProdutos(), new ComparaProdutos());
  }

  public void sortLotes(Produto produto) {
    Collections.sort(produto.obterLotes(), new ComparaLotes());
  }

  public List<Produto> getProdutos() {
    return this.obterWarehouse().obterProdutos();
  }

  public List<Lote> getLotesProduto(Produto produto) {
    return produto.obterLotes();
  }

  public double getPrecoLote(Lote l) {
    return l.obterPreco();
  }

  public boolean verificaPrecoMinimoLote(Lote l, double precoMinimo) {
    if (this.getPrecoLote(l) <= precoMinimo)
      return true;
    return false;
  }

  public Parceiro getParceiro(String idParceiro) {
    return this.obterWarehouse().obterParceiro(idParceiro);
  }

  public void adicionaParceiro(String idProduto, String morada, String idParceiro) {
    this.obterWarehouse().adicionarParceiro(new Parceiro(idProduto,
        morada, idParceiro, new PoliticaRecompensaDefault()));
  }

  public void sortParceiros() {
    Collections.sort(this.obterWarehouse().obterParceiros(), new ComparaParceiros());
  }

  public List<Parceiro> getParceiros() {
    return this.obterWarehouse().obterParceiros();
  }

  public boolean verificaParceiroIgual(Parceiro parceiro, String idParceiro) {
    if (parceiro.obterId().toLowerCase().equals(idParceiro.toLowerCase()))
      return true;
    return false;
  }

  public List<Notificacao> obterNotificacaoEntidade(EntidadeComNotificacoes entidade) {
    return entidade.obterNotificacoes();
  }

  public boolean verificaNotificacaoNula(Notificacao n) {
    if(n instanceof NotificacaoNull)
      return true;
    return false;
  }

  public void removerNotificacoesEntidade(EntidadeComNotificacoes entidade) {
    entidade.removerNotificacoes();
  }

  public void removerNotificacaoIndicada(EntidadeComNotificacoes entidade, String idProduto) {
    entidade.removerNotificacao(this.obterNotificacaoIndicada(idProduto, entidade));
  }

  public boolean verificaExistenciaProduto(String idProduto) {
    return this.obterWarehouse().verificaProduto(idProduto);
  }

  public Notificacao obterNotificacaoIndicada(String idProduto, EntidadeComNotificacoes entidade) {
    return entidade.obterNotificacao(idProduto);
  }

  public Produto getProduto(String idProduto) {
    return this.obterWarehouse().obterProduto(idProduto);
  }

  public void adicionarNotificacaoNula(EntidadeComNotificacoes entidade, Produto produto) {
    entidade.adicionarNotificacao(new NotificacaoNull(produto));
  }

  public List<Lote> getLotesParceiro(Parceiro parceiro) {
    return parceiro.obterLotes();
  }

  public boolean verificaProdutoSimples(Produto produto) {
    if (produto instanceof ProdutoSimples)
      return true;
    return false;
  }

  public boolean verificaProdutoIgual(Produto produto, String idProduto) {
    return produto.obterId().equals(idProduto);
  }

  public int getNrTransacao() {
    return this.obterWarehouse().obterNrTransacao();
  }

  public Transacao getTransacao(int idTransacao) {
    return this.obterWarehouse().obterTransacao(idTransacao);
  }

  public void incrementaTransacao() {
    this.obterWarehouse().incrementarNrTransacao();
  }

  public void adicionaProduto(String idProduto) {
    this.obterWarehouse().adicionarProduto(new ProdutoSimples(idProduto));
  }

  public List<Transacao> getTransacoes() {
    return this.obterWarehouse().obterTransacoes();
  }
}


