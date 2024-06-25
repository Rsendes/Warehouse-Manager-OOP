package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.forms.*;
import ggc.core.transacao.*;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.produto.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;


/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    int idTransacao = 0;
    Form form = new Form("id");

    idTransacao = form.requestInteger(Message.requestTransactionKey());
    form.parse();

    if (idTransacao < 0 || _ent.getNrTransacao() <= idTransacao)
      throw new UnknownTransactionKeyException(idTransacao);

    for (Transacao t : _ent.getTransacoes()){
      if (t.obterId() == idTransacao){
        if (t instanceof Compra)
          _display.popup("COMPRA" +"|" +t.obterId() +"|" +((Compra)t).obterParceiro().obterId() +"|" +t.obterProduto().obterId() +"|"
              +t.obterQuantidade() +"|" +(int)Math.round(((Compra)t).obterValorBase()) + "|" +t.obterDataPagamento());
        else if (t instanceof VendaSimples){
          if(t.obterDataPagamento() != -1)
            _display.popup("VENDA" +"|" +t.obterId() +"|" +((Venda)t).obterParceiro().obterId() +"|" +t.obterProduto().obterId() +"|"
                +t.obterQuantidade() +"|" +(int)Math.round(t.obterValorBase()) +"|" +(int)Math.round(((VendaSimples)t).obterQuantiaPaga()) +"|"
                +((VendaSimples)t).obterDataLimite() +"|" +t.obterDataPagamento());
          else
            _display.popup("VENDA" +"|" +t.obterId() + "|" +((Venda)t).obterParceiro().obterId() +"|" +t.obterProduto().obterId() +"|"
                +t.obterQuantidade() +"|" +(int)Math.round(t.obterValorBase()) +"|" +(int)Math.round(((VendaSimples)t).obterValorBase()) +"|"
                +((VendaSimples)t).obterDataLimite());
        }
        else if (t instanceof Desagregacao){
          String s = new String("");
          for(Componente c : ((ProdutoDerivado)t.obterProduto()).obterReceita().obterComponentes())
            s += c.obterProduto().obterId() + ":" +c.obterQuantidade() +"#";
          s.substring(0, s.length() - 1);
          _display.popup("DESAGREGACAO" +"|" +t.obterId() +"|" +((Desagregacao)t).obterParceiro().obterId() +"|" +t.obterProduto().obterId() +"|"
              +t.obterQuantidade() +"|" +(int)Math.round(t.obterValorBase()) +"|" +(int)Math.round(t.obterValorBase()) +"|"
              +t.obterDataPagamento() +"|" +s);
        }
      }
    }
  }
}