package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Warehouse;
import ggc.core.data.Data;
import java.util.*;
import ggc.app.exception.LessThanZeroException;
import ggc.app.exception.InvalidDateException;
import pt.tecnico.uilib.forms.*;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> implements Message {
  private WarehouseManager _ent;

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException{
    Form form = new Form("incremento");
    int aux = form.requestInteger(Message.requestDaysToAdvance());
    form.parse();
    if (!_ent.verificarDataPositiva(aux))
      throw new InvalidDateException(aux);
    _ent.avancarData(aux);
  }
}