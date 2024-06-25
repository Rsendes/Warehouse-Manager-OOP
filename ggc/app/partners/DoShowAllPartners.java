package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.entidadecomnotificacoes.parceiro.*;
import ggc.core.transacao.*;
import java.util.Collections;

/**
 * Show all partners.
 */
class DoShowAllPartners extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowAllPartners(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PARTNERS, receiver);
    _ent = receiver;
  }

  @Override
  public void execute() throws CommandException {
    _ent.sortParceiros();
    for (Parceiro p : _ent.getParceiros())
      _display.popup(p);
  }

}
