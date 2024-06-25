package ggc.core;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import ggc.core.entidadecomnotificacoes.parceiro.*;
import ggc.core.lote.Lote;
import ggc.core.produto.*;
import java.util.*;
import ggc.core.exception.BadEntryException;
import ggc.core.notificacao.*;

/**
 * Da parse a um ficheiro de texto importado.
 */
public class Parser {

  /** warehouse onde vao ser guardados os dados */
  private Warehouse _store;

  /**
   * Construtor da classe Parser.
   * @param w e a warehouse a carregar para o parser.
   */
  public Parser(Warehouse w) {
    _store = w;
  }

  /**
   * Le o ficheiro linha a linha.
   * @param filename e o nome do ficheiro a ler.
   * @throws IOException
   * @throws BadEntryException
   */
  void parseFile(String filename) throws IOException, BadEntryException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;

      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }

  /**
   * Le o conteudo de uma linha e determina se e parceiro, lote de produto simples ou lote de produto derivado.
   * @param line e a linha a analisar.
   * @throws BadEntryException
   */
  private void parseLine(String line) throws BadEntryException, BadEntryException {
    String[] components = line.split("\\|");

    switch (components[0]) {
      case "PARTNER":
        parsePartner(components, line);
        break;
      case "BATCH_S":
        parseSimpleProduct(components, line);
        break;

      case "BATCH_M":
        parseAggregateProduct(components, line);
        break;
        
      default:
        throw new BadEntryException("Invalid type element: " + components[0]);
    }
  }

  /**
   * Pega no conteudo da linha e adiciona um parceiro.
   * @param components contem os dados do parceiro.
   * @param line e a linha lida.
   * @throws BadEntryException
   */
  private void parsePartner(String[] components, String line) throws BadEntryException {
    if (components.length != 4)
      throw new BadEntryException("Invalid partner with wrong number of fields (4): " + line);

    String id = components[1];
    String name = components[2];
    String address = components[3];

    _store.adicionarParceiro(new Parceiro(name, address, id, new PoliticaRecompensaDefault()));
  }

  /**
   * Pega no conteudo da linha e adiciona um lote de um produto simples.
   * @param components sao os dados do lote.
   * @param line e a linha lida.
   * @throws BadEntryException
   */
  private void parseSimpleProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 5)
      throw new BadEntryException("Invalid number of fields (4) in simple batch description: " + line);
    
    boolean val = false;
    String idProduct = components[1];
    String idPartner = components[2];
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    
    for (Produto p : _store.obterProdutos()){
      if (p.obterId().equals(idProduct)){
        val = true;
        for (Parceiro i : _store.obterParceiros()){
          if (i.obterId().equals(idPartner)){
            p.adicionarLote(new Lote(price, stock, p, i));
            i.adicionarLote(new Lote(price, stock, p, i));
            if (price > p.obterPrecoMax())
              p.atualizarPreco(price);
            break;
          }
        }
        break;
      }
    }

    if(!val){
      _store.adicionarProduto(new ProdutoSimples(idProduct));
      for (Parceiro i : _store.obterParceiros()){
        if (i.obterId().equals(idPartner)){
          Produto p = _store.obterProdutos().get(_store.obterProdutos().size() -1);
          p.adicionarLote(new Lote(price, stock, p, i));
          i.adicionarLote(new Lote(price, stock, p, i));
          p.atualizarPreco(price);
        }
        i.adicionarNotificacao(new NotificacaoNull(_store.obterProduto(idProduct)));
      }
    }
  }
 
    
  /**
   * Pega no conteudo da linha e adiciona um lote de um produto derivado.
   * @param components sao os dados do lote.
   * @param line e a linha lida.
   * @throws BadEntryException
   */
  private void parseAggregateProduct(String[] components, String line) throws BadEntryException {
    if (components.length != 7)
      throw new BadEntryException("Invalid number of fields (7) in aggregate batch description: " + line);
    
    String idProduct = components[1];
    String idPartner = components[2];
    double alpha = Double.parseDouble(components[5]);
    boolean val = false;

    for (Produto p : _store.obterProdutos()){
      if (p.obterId().equals(idProduct)){
        val = true;
        break;
      }
    }
    if (!val) {
      ArrayList<Produto> products = new ArrayList<>();
      ArrayList<Integer> quantities = new ArrayList<>();
      ArrayList<Componente> componentes = new ArrayList<>();
      
      for (String component : components[6].split("#")) {
        String[] recipeComponent = component.split(":");
        componentes.add(new Componente(new ProdutoSimples(recipeComponent[0]), Integer.parseInt(recipeComponent[1])));
        products.add(new ProdutoSimples(recipeComponent[0]));
        quantities.add(Integer.parseInt(recipeComponent[1]));
      }
      
      _store.adicionarProduto(new ProdutoDerivado(idProduct, componentes, alpha));
    }
    
    double price = Double.parseDouble(components[3]);
    int stock = Integer.parseInt(components[4]);
    for (Produto p : _store.obterProdutos()){
      if (p.obterId().equals(idProduct)){
        for (Parceiro i : _store.obterParceiros()){
          if (i.obterId().equals(idPartner)){
            p.adicionarLote(new Lote(price, stock, p, i));
            p.atualizarPreco(price);
          }
        }
      }
    }
  }
}