package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.transacao.*;
import pt.tecnico.uilib.forms.*;
import ggc.core.produto.*;
import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    _ent = receiver;
  }

  @Override
  public void execute() throws CommandException {
    Form form = new Form("id");

    String idParceiro = form.requestString(Message.requestPartnerKey());

    if (!_ent.verificaExistenciaParceiro(idParceiro))
      throw new UnknownPartnerKeyException(idParceiro);

    Parceiro parceiro = _ent.getParceiro(idParceiro);

    for (Venda v : _ent.getVendasParceiro(parceiro))
      if (_ent.verificaVendaSimples(v))
        _display.popup(v);
  }
}