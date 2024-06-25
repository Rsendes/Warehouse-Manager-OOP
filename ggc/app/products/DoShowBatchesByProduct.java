package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.lote.Lote;
import java.util.*;
import ggc.core.produto.*;
import pt.tecnico.uilib.forms.*;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    Form form = new Form("id");
    form.addStringField("str", Message.requestProductKey());
    form.parse();

    if (!_ent.verificaExistenciaProduto(form.stringField("str")))
      throw new UnknownProductKeyException(form.stringField("str"));

    for (Produto p : _ent.getProdutos())
      if (_ent.verificaProdutoIgual(p, form.stringField("str")))
        for (Lote l : _ent.getLotesProduto(p))
          _display.popup(l);
  }
}
