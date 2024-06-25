package ggc.core.entidadecomnotificacoes;

import java.io.Serializable;
import ggc.core.notificacao.*;
import java.util.*;

public abstract class EntidadeComNotificacoes implements Serializable{

		/** Serial number for serialization. */
  	private static final long serialVersionUID = 202109192006L;

  	private List <Notificacao> _notificacoes;

  	public EntidadeComNotificacoes(){
  		_notificacoes = new ArrayList<>();
  	}

  	public List <Notificacao> obterNotificacoes(){
		return _notificacoes;
	}

	public void adicionarNotificacao(Notificacao notificacao){
		_notificacoes.add(notificacao);
	}

	public void removerNotificacao(Notificacao notificacao){
		_notificacoes.remove(notificacao);
	}

	public boolean verificaNotificacao(String id){
		for(Notificacao n : _notificacoes)
          if (n.obterProduto().obterId().equals(id))
          	return true;
         return false;
	}

	public Notificacao obterNotificacao(String id){
		for(Notificacao n : _notificacoes)
          if (n.obterProduto().obterId().equals(id))
          	return n;
        return null;
	}

		public void removerNotificacoes(){
		Iterator<Notificacao> iter = _notificacoes.iterator();

		while (iter.hasNext()){
			Notificacao n = iter.next();
			if (!(n instanceof NotificacaoNull))
				iter.remove();
		}
	}
}