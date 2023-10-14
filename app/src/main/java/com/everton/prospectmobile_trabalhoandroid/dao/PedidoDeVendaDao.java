package com.everton.prospectmobile_trabalhoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.everton.prospectmobile_trabalhoandroid.helper.SQLiteDataHelper;
import com.everton.prospectmobile_trabalhoandroid.model.PedidoVenda;

import java.util.ArrayList;
import java.util.List;

public class PedidoDeVendaDao {
    private SQLiteDatabase database;
    private SQLiteDataHelper dbHelper;
    private Context context;

    public PedidoDeVendaDao(Context context) {
        this.context = context; // Modifique esta linha
        dbHelper = new SQLiteDataHelper(this.context, "UNIPAR", null, 1);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(PedidoVenda pedidoVenda) {
        ContentValues values = new ContentValues();
        values.put("Data", pedidoVenda.getData());
        values.put("ValorTotal", pedidoVenda.getValorTotal());
        values.put("CondicaoPagamento", pedidoVenda.getCondicaoPagamento());
        values.put("NumParcelas", pedidoVenda.getQuantidadeParcelas());
        values.put("ValorFrete", pedidoVenda.getValorFrete());
        values.put("CodigoCliente", pedidoVenda.getCliente().getCodigo());

        return database.insert("PEDIDOVENDA", null, values);
    }

    public int update(PedidoVenda pedidoVenda) {
        ContentValues values = new ContentValues();
        values.put("Data", pedidoVenda.getData());
        values.put("ValorTotal", pedidoVenda.getValorTotal());
        values.put("CondicaoPagamento", pedidoVenda.getCondicaoPagamento());
        values.put("NumParcelas", pedidoVenda.getQuantidadeParcelas());
        values.put("ValorFrete", pedidoVenda.getValorFrete());
        values.put("CodigoCliente", pedidoVenda.getCliente().getCodigo());

        return database.update("PEDIDOVENDA", values, "Codigo = ?", new String[]{String.valueOf(pedidoVenda.getCodigo())});
    }

    public void delete(PedidoVenda pedidoVenda) {
        long id = pedidoVenda.getCodigo();
        database.delete("PEDIDOVENDA", "Codigo = " + id, null);
    }

    public List<PedidoVenda> getAllPedidos() {
        List<PedidoVenda> pedidos = new ArrayList<>();
        Cursor cursor = database.query("PEDIDOVENDA", null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PedidoVenda pedidoVenda = cursorToPedidoVenda(cursor);
            pedidos.add(pedidoVenda);
            cursor.moveToNext();
        }
        cursor.close();
        return pedidos;
    }

    private PedidoVenda cursorToPedidoVenda(Cursor cursor) {
        PedidoVenda pedidoVenda = new PedidoVenda();
        pedidoVenda.setCodigo(cursor.getInt(0));
        pedidoVenda.setData(cursor.getString(1));
        pedidoVenda.setValorTotal(cursor.getDouble(2));
        pedidoVenda.setCondicaoPagamento(cursor.getString(3));
        pedidoVenda.setQuantidadeParcelas(cursor.getInt(4));
        pedidoVenda.setValorFrete(cursor.getDouble(5));
        // Aqui, você pode adicionar lógica para recuperar o Cliente e o EnderecoEntrega usando seus respectivos DAOs e os códigos obtidos do cursor.
        return pedidoVenda;
    }
}
