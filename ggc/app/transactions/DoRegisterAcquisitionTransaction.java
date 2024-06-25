package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.forms.*;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.produto.*;
import ggc.core.entidadecomnotificacoes.parceiro.Parceiro;
import ggc.core.lote.Lote;
import ggc.core.transacao.*;
import ggc.core.notificacao.*;
import java.util.*;

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    List<Componente> componentes = new ArrayList<>();
    Form form = new Form("id");

    String idParceiro = form.requestString(Message.requestPartnerKey());

    String idProduto = form.requestString(Message.requestProductKey());

    double preco = form.requestReal(Message.requestPrice());

    int quantidade = form.requestInteger(Message.requestAmount());

    Parceiro parceiro = _ent.getParceiro(idParceiro);
    Produto produto = _ent.getProduto(idProduto);

    if (!_ent.verificaExistenciaParceiro(idParceiro))
      throw new UnknownPartnerKeyException(idParceiro);

    if (!_ent.verificaExistenciaProduto(idProduto)){
      boolean ehDerivado = form.confirm(Message.requestAddRecipe());
      if(!ehDerivado){
        _ent.adicionaProduto(idProduto);
        produto = _ent.getProduto(idProduto);
      }
      else {
        int nrComponentes = form.requestInteger(Message.requestNumberOfComponents());
        double agravamento = form.requestReal(Message.requestAlpha());
        for (int i = 0; i < nrComponentes; i++){
          String idComponente = form.requestString(Message.requestProductKey());
          int quantidadeComp = form.requestInteger(Message.requestAmount());
          if (!_ent.verificaExistenciaProduto(idComponente))
            throw new UnknownProductKeyException(idComponente);
          componentes.add(new Componente(_ent.getProduto(idComponente),
              quantidadeComp));
        }
        _ent.obterWarehouse().adicionarProduto(new ProdutoDerivado(idProduto, componentes, agravamento));
      }
      for (Parceiro p : _ent.getParceiros())
          if (p.verificaNotificacao(idProduto))
            p.adicionarNotificacao(new NotificacaoNull(produto));
    }

    else if (produto.obterStockTotal() == 0){
      for (Parceiro p : _ent.getParceiros())
        if (p.verificaNotificacao(idProduto))
          p.adicionarNotificacao(new NotificacaoDefault("NEW", produto));
    }

    else if (preco < produto.obterPrecoMin()){
      for (Parceiro p : _ent.getParceiros())
        if (p.verificaNotificacao(idProduto))
          p.adicionarNotificacao(new NotificacaoDefault("BARGAIN", produto));
    }

    produto.compraProduto(quantidade, parceiro, preco);
    parceiro.adicionarLote(new Lote(preco, quantidade, produto, parceiro));

    _ent.obterWarehouse().adicionarTransacao(new Compra(_ent.getNrTransacao(),
        _ent.mostrarDataAtual(), preco*quantidade, quantidade, produto, parceiro));

    parceiro.adicionarCompra(new Compra(_ent.getNrTransacao(),
        _ent.mostrarDataAtual(), preco*quantidade, quantidade, produto, parceiro));

    _ent.incrementaTransacao();
  }
}