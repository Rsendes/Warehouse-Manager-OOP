package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowGlobalBalance(WarehouseManager receiver) {
    super(Label.SHOW_BALANCE, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    _display.popup(Message.currentBalance(_ent.mostrarSaldoDisponivel(),
        _ent.mostrarSaldoContabilistico()));
  }
  
}
