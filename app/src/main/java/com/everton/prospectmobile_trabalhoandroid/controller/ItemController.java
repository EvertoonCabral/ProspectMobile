package com.everton.prospectmobile_trabalhoandroid.controller;

import android.content.Context;
import android.text.TextUtils;

import com.everton.prospectmobile_trabalhoandroid.dao.ItemDao;
import com.everton.prospectmobile_trabalhoandroid.model.Item;

import java.util.ArrayList;

public class ItemController {
    private Context context;

    public ItemController(Context context) {
        this.context = context;
    }

    public long salvarItem(String descricao, String valorUnitario, String unidadeMedida) {
        String validacao = validarItem(descricao, valorUnitario, unidadeMedida);
        if (!validacao.isEmpty()) {
            // Se houver erros de validação, retorna um número negativo.
            return -1;
        }

        Item item = new Item(descricao, Double.parseDouble(valorUnitario), unidadeMedida); // 0 é um placeholder para o código do item.
        return ItemDao.getInstancia(context).insert(item);
    }

    public long atualizarItem(String descricao, String valorUnitario, String unidadeMedida) {
        Item item = new Item(descricao, Double.parseDouble(valorUnitario), unidadeMedida);
        return ItemDao.getInstancia(context).update(item);
    }

    public long apagarItem(int codigo) {
        Item item = new Item();
        item.setCodigo(codigo);
        return ItemDao.getInstancia(context).delete(item);
    }

    public ArrayList<Item> retornarTodosItens() {
        return ItemDao.getInstancia(context).getAll();
    }

    public Item retornarItem(int codigo) {
        return ItemDao.getInstancia(context).getById(codigo);
    }

    public String validarItem(String descricao, String valorUnitario, String unidadeMedida) {
        StringBuilder validacao = new StringBuilder();

        if (TextUtils.isEmpty(descricao)) {
            validacao.append("Descrição do item deve ser preenchida!!\n");
        }

        if (TextUtils.isEmpty(valorUnitario) || Double.parseDouble(valorUnitario) <= 0) {
            validacao.append("Valor unitário deve ser preenchido e maior que zero!!\n");
        }

        if (TextUtils.isEmpty(unidadeMedida)) {
            validacao.append("Unidade de medida deve ser preenchida!!\n");
        }

        // Aqui você pode adicionar outras validações específicas para cada campo.

        return validacao.toString();
    }
}
