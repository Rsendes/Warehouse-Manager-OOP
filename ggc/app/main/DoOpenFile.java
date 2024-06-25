package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import java.util.*;
import ggc.core.exception.*;
import ggc.app.exception.FileOpenFailedException;
import pt.tecnico.uilib.forms.*;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    Form form = new Form("filename");
    String aux = form.requestString(Message.openFile());
    form.parse();
    try {
      _ent.load(aux);
    } catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}