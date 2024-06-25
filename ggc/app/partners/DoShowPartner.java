package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import java.util.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.notificacao.*;
import ggc.app.exception.UnknownPartnerKeyException;
import pt.tecnico.uilib.forms.*;


/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    _ent = receiver;
  }

  @Override
  public void execute() throws CommandException {
    Form form = new Form("id");
    form.addStringField("str", Message.requestPartnerKey());
    form.parse();

    if (!_ent.verificaExistenciaParceiro(form.stringField("str")))
      throw new UnknownPartnerKeyException(form.stringField("str"));

    for (Parceiro p : _ent.getParceiros())
      if (_ent.verificaParceiroIgual(p, form.stringField("str"))){
        _display.popup(p);
        for (Notificacao n : _ent.obterNotificacaoEntidade(p))
          if (!_ent.verificaNotificacaoNula(n))
            _display.popup(n);
        _ent.removerNotificacoesEntidade(p);
      }
  }
}