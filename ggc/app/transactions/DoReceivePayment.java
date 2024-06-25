package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.transacao.*;
import ggc.app.exception.UnknownTransactionKeyException;
import pt.tecnico.uilib.forms.*;
import ggc.core.data.Data;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    Form form = new Form("id");
    int idTransacao = form.requestInteger(Message.requestTransactionKey());

    if (idTransacao < 0 || _ent.getNrTransacao() <= idTransacao)
      throw new UnknownTransactionKeyException(idTransacao);

    VendaSimples vendaSimples = (VendaSimples)_ent.getTransacao(idTransacao);
    VendaSimples vendaSimplesParceiro = null;
    Parceiro parceiro = null;

    for (Parceiro p : _ent.getParceiros()){
      if (p.verificaVenda(idTransacao)){
        vendaSimplesParceiro = (VendaSimples)p.obterVenda(idTransacao);
        parceiro = p;
      }
    }

    if (vendaSimples.obterQuantiaPaga() == 0){
      vendaSimples.pagar(_ent.obterDataAtual());
      vendaSimplesParceiro.pagar(_ent.obterDataAtual());
    }

    if (vendaSimples.obterDataLimite() >= _ent.mostrarDataAtual())
      parceiro.adicionaPontos(vendaSimples.obterQuantiaPaga() * 10);

    else {
      parceiro.retiraPontos(_ent.mostrarDataAtual() - vendaSimples.obterDataLimite());
    }
  }
}