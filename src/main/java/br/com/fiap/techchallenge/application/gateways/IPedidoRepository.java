package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.infra.persistence.entities.Pedido;
import com.github.fge.jsonpatch.JsonPatch;

public interface IPedidoRepository {
    Pedido atualizarStatusDoPedido(String id, JsonPatch patch);

    Pedido atualizarPagamentoDoPedido(String id, JsonPatch patch);

}