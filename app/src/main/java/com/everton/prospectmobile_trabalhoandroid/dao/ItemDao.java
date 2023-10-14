package com.everton.prospectmobile_trabalhoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.everton.prospectmobile_trabalhoandroid.helper.SQLiteDataHelper;
import com.everton.prospectmobile_trabalhoandroid.model.Item;

import java.util.ArrayList;

public class ItemDao implements GenericDao<Item> {

    private SQLiteDatabase bd;
    private String[] colunas = {"Codigo", "Descricao", "ValorUnitario", "UnidadeMedida"};
    private String tableName = "ITEM";
    private Context context;
    private SQLiteDataHelper sqLiteDataHelper;
    private static ItemDao instancia;

    public static ItemDao getInstancia(Context context) {
        if (instancia == null)
            return instancia = new ItemDao(context);
        else
            return instancia;
    }

    public ItemDao(Context context) {
        this.context = context;
        sqLiteDataHelper = new SQLiteDataHelper(this.context, "UNIPAR", null, 1);
        bd = sqLiteDataHelper.getWritableDatabase();
    }

    @Override
    public long insert(Item item) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("Descricao", item.getDescricao());
            valores.put("ValorUnitario", item.getValorUnitario());
            valores.put("UnidadeMedida", item.getUnidadeMedida());

            return bd.insert(tableName, null, valores);

        } catch (SQLException ex) {
            Log.e("ERRO", "ItemDao.insert(): " + ex.getMessage());
            return -1;
        }
    }

    @Override
    public long update(Item item) {
        ContentValues valores = new ContentValues();
        valores.put("Descricao", item.getDescricao());
        valores.put("ValorUnitario", item.getValorUnitario());
        valores.put("UnidadeMedida", item.getUnidadeMedida());

        return bd.update(tableName, valores, "Codigo = ?", new String[]{String.valueOf(item.getCodigo())});
    }

    @Override
    public long delete(Item item) {
        return bd.delete(tableName, "Codigo = ?", new String[]{String.valueOf(item.getCodigo())});
    }

    @Override
    public ArrayList<Item> getAll() {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = bd.query(tableName, colunas, null, null, null, null, "Descricao ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Item item = new Item();
                item.setCodigo(cursor.getInt(0));
                item.setDescricao(cursor.getString(1));
                item.setValorUnitario(cursor.getDouble(2));
                item.setUnidadeMedida(cursor.getString(3));
                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return items;
    }

    @Override
    public Item getById(int id) {
        Cursor cursor = bd.query(tableName, colunas, "Codigo = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Item item = new Item();
            item.setCodigo(cursor.getInt(0));
            item.setDescricao(cursor.getString(1));
            item.setValorUnitario(cursor.getDouble(2));
            item.setUnidadeMedida(cursor.getString(3));
            cursor.close();
            return item;
        } else {
            cursor.close();
            return null;
        }
    }
}
