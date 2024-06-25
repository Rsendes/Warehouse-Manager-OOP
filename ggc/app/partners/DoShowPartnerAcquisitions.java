package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.transacao.*;
import ggc.core.produto.*;
import java.util.*;
import pt.tecnico.uilib.forms.*;
import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    _ent = receiver;
  }

  @Override
  public void execute() throws CommandException {
    Form form = new Form("id");
    form.addStringField("str", Message.requestPartnerKey());
    form.parse();

    if (!_ent.verificaExistenciaParceiro(form.stringField("str")))
      throw new UnknownPartnerKeyException(form.stringField("str"));

    for (Parceiro p : _ent.getParceiros()){
      if(p.obterId().equals(form.stringField("str")))
        for(Compra c : p.obterCompras())
        _display.popup("COMPRA|" +c.obterId() +"|" +p.obterId() +"|" 
            +c.obterProduto().obterId() +"|" +c.obterQuantidade() +"|"
            +Math.round(c.obterValorBase()) +"|" +c.obterDataPagamento());
    }
  }

}
