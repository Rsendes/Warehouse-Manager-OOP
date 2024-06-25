package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.produto.*;
import ggc.core.lote.Lote;
import java.util.*;

/**
 * Show all products.
 */
class DoShowAllProducts extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowAllProducts(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {

    _ent.sortProdutos();

    for (Produto p : _ent.getProdutos()){

      if (_ent.verificaProdutoSimples(p)){
        _display.popup((ProdutoSimples)p);
      }

      else {
        _display.popup((ProdutoDerivado)p);
      }
    }
  }

}
