package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.produto.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.lote.*;
import java.util.Collections;

/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    _ent.sortProdutos();
    for (Produto p : _ent.getProdutos()){
      _ent.sortLotes(p);
      for (Lote l : _ent.getLotesProduto(p)){
        _display.popup(l);
      }
    }
  }

}
