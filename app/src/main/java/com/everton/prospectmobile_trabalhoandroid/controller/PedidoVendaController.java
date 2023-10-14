package com.everton.prospectmobile_trabalhoandroid.controller;

import android.content.Context;

import com.everton.prospectmobile_trabalhoandroid.dao.PedidoDeVendaDao;
import com.everton.prospectmobile_trabalhoandroid.model.Item;
import com.everton.prospectmobile_trabalhoandroid.model.PedidoVenda;

import java.util.List;

public class PedidoVendaController {
    private final Context context;
    private final PedidoDeVendaDao pedidoVendaDao;

    public PedidoVendaController(Context context) {
        this.context = context;
        this.pedidoVendaDao = new PedidoDeVendaDao(context);
    }

    public long salvarPedido(PedidoVenda pedidoVenda) {
        // Aqui você pode adicionar validações e lógicas específicas antes de salvar o pedido.
        calcularValorTotal(pedidoVenda);
        return pedidoVendaDao.insert(pedidoVenda);
    }

    public int atualizarPedido(PedidoVenda pedidoVenda) {
        calcularValorTotal(pedidoVenda);
        return pedidoVendaDao.update(pedidoVenda);
    }

    public void apagarPedido(PedidoVenda pedidoVenda) {
        pedidoVendaDao.delete(pedidoVenda);
    }

    public List<PedidoVenda> listarTodosPedidos() {
        return pedidoVendaDao.getAllPedidos();
    }

    private void calcularValorTotal(PedidoVenda pedidoVenda) {
        double valorTotal = 0;

        // Calcula o valor total com base nos itens
        for (Item item : pedidoVenda.getItens()) {
            valorTotal += item.getValorUnitario();
        }

        // Aplica desconto ou acréscimo conforme condição de pagamento
        if ("á vista".equalsIgnoreCase(pedidoVenda.getCondicaoPagamento())) {
            valorTotal *= 0.95; // Desconto de 5%
        } else if ("á prazo".equalsIgnoreCase(pedidoVenda.getCondicaoPagamento())) {
            valorTotal *= 1.05; // Acréscimo de 5%
        }

        // Adiciona valor de frete conforme localidade
        if (!"Toledo-PR".equalsIgnoreCase(pedidoVenda.getEnderecoEntrega().getCidade())) {
            valorTotal += 20.00; // Frete de R$20,00 para outras cidades
        }
        if (!"PR".equalsIgnoreCase(pedidoVenda.getEnderecoEntrega().getUf())) {
            valorTotal += 50.00; // Adicional de R$50,00 para outros estados
        }

        pedidoVenda.setValorTotal(valorTotal);
    }
}
