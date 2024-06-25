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
import ggc.core.notificacao.*;

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {
  private WarehouseManager _ent;

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    _ent = receiver;
  }

  @Override
  public final void execute() throws CommandException {
    int val = 0;
    double valorCompra = 0, valorDiferenca = 0;
    Produto produto = null;
    Form form = new Form("id");

    String idParceiro = form.requestString(Message.requestPartnerKey());

    Parceiro parceiro = _ent.getParceiro(idParceiro);

    String idProduto = form.requestString(Message.requestProductKey());

    int quantidade = form.requestInteger(Message.requestAmount());

    if (!_ent.verificaExistenciaParceiro(idParceiro))
      throw new UnknownPartnerKeyException(idParceiro);

    if (!_ent.verificaExistenciaProduto(idProduto))
      throw new UnknownProductKeyException(idProduto);

    for (Produto p : _ent.getProdutos())
      if (p.obterId().equals(idProduto)){
        val = p.verificaQuantidade(quantidade);
        produto = p;
      }

    if (val < 0)
      throw new UnavailableProductException(idProduto, quantidade, quantidade + val);

    else {
      if (_ent.getProduto(idProduto) instanceof ProdutoDerivado){

        double valorVenda = _ent.getProduto(idProduto).vendaProduto(quantidade);

        for (Componente c : ((ProdutoDerivado)produto).obterReceita().obterComponentes()){
          if (c.obterProduto().obterStockTotal() == 0)
            for (Parceiro p : _ent.getParceiros())
              if (p.verificaNotificacao(c.obterProduto().obterId()))
                p.adicionarNotificacao(new NotificacaoDefault("NEW", c.obterProduto()));
          valorCompra += c.obterProduto().compraProduto(c.obterQuantidade() * quantidade, parceiro);
        }

        valorCompra *= (1 + ((ProdutoDerivado)produto).obterReceita().obterAgravamento());

        if (valorCompra > valorVenda)
          valorDiferenca = 0;
        else
          valorDiferenca = valorVenda - valorCompra;
          _display.popup(valorDiferenca);

        _ent.obterWarehouse().adicionarTransacao(new Desagregacao(_ent.getNrTransacao(), _ent.mostrarDataAtual(), valorDiferenca,
            quantidade, produto, parceiro));

        parceiro.adicionarVenda(new Desagregacao(_ent.getNrTransacao(), _ent.mostrarDataAtual(), valorDiferenca,
            quantidade, produto, parceiro));

        _ent.incrementaTransacao();

        if (valorDiferenca > 0)
          parceiro.adicionaPontos(valorDiferenca * 10);
      }
    }
  }
}