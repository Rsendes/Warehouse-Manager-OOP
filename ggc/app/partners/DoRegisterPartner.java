package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import java.util.*;
import pt.tecnico.uilib.forms.*;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    _ent = receiver;
  }

  @Override
  public void execute() throws CommandException {
    Form form = new Form("id");
    String idParceiro = form.requestString(Message.requestPartnerKey());
    String idProduto = form.requestString(Message.requestPartnerName());
    String morada = form.requestString(Message.requestPartnerAddress());
    
    if (_ent.verificaExistenciaParceiro(idParceiro))
      throw new DuplicatePartnerKeyException(idParceiro);

    _ent.adicionaParceiro(idProduto,morada, idParceiro);
  }
}
