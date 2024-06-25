package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.forms.*;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.core.produto.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.transacao.*;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    int qntdFalta = 0;
    boolean val = true;
    Produto produto = null;
    Form form = new Form("id");

    String idParceiro = form.requestString(Message.requestPartnerKey());

    int dataLimite = form.requestInteger(Message.requestPaymentDeadline());

    String idProduto = form.requestString(Message.requestProductKey());

    int quantidade = form.requestInteger(Message.requestAmount());
    form.parse();

    if (!_ent.verificaExistenciaParceiro(idParceiro))
      throw new UnknownPartnerKeyException(idParceiro);

    if (!_ent.verificaExistenciaProduto(idProduto))
      throw new UnknownProductKeyException(idProduto);

    for (Produto p : _ent.getProdutos())
      if (p.obterId().equals(idProduto)){
        qntdFalta = p.verificaQuantidade(quantidade);
        produto = p;
      }

    Parceiro parceiro = _ent.getParceiro(idParceiro);

    if (qntdFalta < 0){
      val = false;
      if (produto instanceof ProdutoSimples)
        throw new  UnavailableProductException(idProduto, quantidade, quantidade + qntdFalta);
      else if (produto instanceof ProdutoDerivado){
        if (!_ent.obterWarehouse().verificaCompSuf(produto, -qntdFalta)){
          Produto produtoEmFalta = _ent.obterWarehouse().obterProdutoEmFalta(produto, -qntdFalta);
          int qntdPedida = ((ProdutoDerivado)produto).obterReceita().obterQntdComponente(produtoEmFalta.obterId()) * (-qntdFalta);
          throw new UnavailableProductException(produtoEmFalta.obterId(), qntdPedida, quantidade + qntdFalta);
        }
        else {
          _ent.obterWarehouse().agrega(produto, qntdFalta, parceiro);
          val = true;
        }
      }
    }

    if (val){
      double valorVenda = _ent.getProduto(idProduto)
            .vendaProduto(quantidade);

      _ent.obterWarehouse().adicionarTransacao(new VendaSimples(_ent.getNrTransacao(),
          -1, valorVenda, quantidade, produto, parceiro, dataLimite, 0));

      parceiro.adicionarVenda(new VendaSimples(_ent.getNrTransacao(),
          -1, valorVenda, quantidade, produto, parceiro, dataLimite, 0));

      _ent.incrementaTransacao();
    }
  }
}