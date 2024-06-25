package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import java.util.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.produto.*;
import ggc.core.notificacao.*;
import pt.tecnico.uilib.forms.*;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    _ent = receiver;

  }

  @Override
  public void execute() throws CommandException {
    Form form = new Form("id");
    String idParceiro = form.requestString(Message.requestPartnerKey());
    String idProduto = form.requestString(Message.requestProductKey());

    if (!_ent.verificaExistenciaParceiro(idParceiro))
      throw new UnknownPartnerKeyException(idParceiro);

    if (!_ent.verificaExistenciaProduto(idProduto))
      throw new UnknownProductKeyException(idProduto);

    Produto produto = _ent.getProduto(idProduto);

    for (Parceiro i : _ent.getParceiros())
      if (_ent.verificaParceiroIgual(i, idParceiro)){
        if (!_ent.verificaNotificacaoNula(_ent.obterNotificacaoIndicada(idProduto, i)))
            _ent.removerNotificacaoIndicada(i, idProduto);
        else 
          _ent.adicionarNotificacaoNula(i, produto);
      }
  }
}