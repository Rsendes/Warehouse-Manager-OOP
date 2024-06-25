package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Warehouse;
import ggc.core.data.Data;

/**
 * Show current date.
 */
class DoDisplayDate extends Command<WarehouseManager> implements Message {
  private WarehouseManager _ent;

  DoDisplayDate(WarehouseManager receiver) {
    super(Label.SHOW_DATE, receiver);
    _ent = receiver;
  }


  @Override
  public final void execute() throws CommandException {
    _display.popup(Message.currentDate(_ent.mostrarDataAtual()));
  }
}
