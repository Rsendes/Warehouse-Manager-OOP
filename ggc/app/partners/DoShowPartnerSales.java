package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.produto.*;
import ggc.core.transacao.*;
import java.util.*;
import ggc.core.data.Data;
import pt.tecnico.uilib.forms.*;
import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
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
      if(p.obterId().equals(form.stringField("str"))){
        for (Venda v : p.obterVendas()){
          if (v instanceof VendaSimples)
            if(v.obterDataPagamento() != -1)
              _display.popup("VENDA" +"|" +v.obterId() +"|" +((Venda)v).obterParceiro().obterId() +"|" +v.obterProduto().obterId() +"|"
                  +v.obterQuantidade() +"|" +(int)Math.round(v.obterValorBase()) +"|" +(int)Math.round(((VendaSimples)v).obterQuantiaPaga()) +"|"
                  +((VendaSimples)v).obterDataLimite() +"|" +v.obterDataPagamento());
            else
              _display.popup("VENDA" +"|" +v.obterId() + "|" +((Venda)v).obterParceiro().obterId() +"|" +v.obterProduto().obterId() +"|"
                  +v.obterQuantidade() +"|" +(int)Math.round(v.obterValorBase()) +"|" +(int)Math.round(((VendaSimples)v).obterValorBase()) +"|"
                  +((VendaSimples)v).obterDataLimite());
          else if (v instanceof Desagregacao){
            String s = new String("");
            for(Componente c : ((ProdutoDerivado)((Desagregacao)v).obterProduto()).obterReceita().obterComponentes())
              s += c.obterProduto().obterId() + ":" + v.obterQuantidade() *
                  c.obterQuantidade() +":" + (int)Math.round(v.obterProduto().obterPrecoMax()) + "#";
            s = s.substring(0, s.length() - 1);
            _display.popup("DESAGREGAÇAO|" +v.obterId() +"|" +p.obterId() +"|"
                +v.obterProduto().obterId() +"|" +v.obterQuantidade() +"|"
                +(int)Math.round(v.obterValorBase()) +"|" +(int)Math.round(v.obterValorBase()) +"|" 
                +v.obterDataPagamento() +"|" +s);
          }
        }
      }
    }
  }
}
