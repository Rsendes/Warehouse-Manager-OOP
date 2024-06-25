package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.forms.*;
import ggc.core.produto.*;
import ggc.core.lote.*;
import java.util.Collections;

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    _ent = receiver;
  }

  @Override
  public void execute() throws CommandException {
    Form form = new Form("id");

    double precoMinimo = form.requestReal(Message.requestPriceLimit());

    _ent.sortProdutos();

    for (Produto p : _ent.getProdutos()){
      _ent.sortLotes(p);
      for (Lote l : _ent.getLotesProduto(p))
        if (_ent.verificaPrecoMinimoLote(l, precoMinimo))
          _display.popup(l);
    }
  }

}
