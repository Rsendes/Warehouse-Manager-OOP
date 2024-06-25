package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.WarehouseManager;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.lote.Lote;
import java.util.*;
import pt.tecnico.uilib.forms.*;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    Form form = new Form("id");
    String idParceiro = form.requestString(Message.requestPartnerKey());

    if (!_ent.verificaExistenciaParceiro(idParceiro))
      throw new UnknownPartnerKeyException(idParceiro);

    for (Parceiro p : _ent.getParceiros())
      if (_ent.verificaParceiroIgual(p, idParceiro))
        for (Lote l : _ent.getLotesParceiro(p))
          _display.popup(l);
  }
}
