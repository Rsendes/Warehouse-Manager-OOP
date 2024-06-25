package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import java.util.*;
import ggc.core.exception.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import pt.tecnico.uilib.forms.*;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    if(_ent.obterNomeFicheiro() == null){
      Form form = new Form("filename");
      form.addStringField("str", Message.newSaveAs());
      form.parse();
      try{
        _ent.saveAs(form.stringField("str"));
      } catch (MissingFileAssociationException e){
      } catch (FileNotFoundException e){
      } catch (IOException e){
      }
    }
    else{
      try{
        _ent.save();
      } catch (IOException e){
      } catch (MissingFileAssociationException e){
      }
    }
  }

}
