package com.everton.prospectmobile_trabalhoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.everton.prospectmobile_trabalhoandroid.helper.SQLiteDataHelper;
import com.everton.prospectmobile_trabalhoandroid.model.PedidoVenda;

import java.util.ArrayList;
import java.util.List;

public class PedidoDeVendaDao {
    private SQLiteDatabase database;
    private SQLiteDataHelper dbHelper;
    private Context context;

    public PedidoDeVendaDao(Context context) {
        this.context = context;
        dbHelper = new SQLiteDataHelper(this.context, "UNIPAR", null, 1);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(PedidoVenda pedidoVenda) {
        long result = -1;
        try {
            open();
            ContentValues values = new ContentValues();
            values.put("Data", pedidoVenda.getData());
            values.put("ValorTotal", pedidoVenda.getValorTotal());
            values.put("CondicaoPagamento", pedidoVenda.getCondicaoPagamento());
            values.put("NumParcelas", pedidoVenda.getQuantidadeParcelas());
            values.put("ValorFrete", pedidoVenda.getValorFrete());
            values.put("CodigoCliente", pedidoVenda.getCliente().getCodigo());

            result = database.insert("PEDIDOVENDA", null, values);
        } catch (SQLException e) {
            Log.e("PedidoDeVendaDao", "Error inserting data: " + e.getMessage());
        } finally {
            close();
        }
        return result;
    }

    public int update(PedidoVenda pedidoVenda) {
        int result = -1;
        try {
            open();
            ContentValues values = new ContentValues();
            values.put("Data", pedidoVenda.getData());
            values.put("ValorTotal", pedidoVenda.getValorTotal());
            values.put("CondicaoPagamento", pedidoVenda.getCondicaoPagamento());
            values.put("NumParcelas", pedidoVenda.getQuantidadeParcelas());
            values.put("ValorFrete", pedidoVenda.getValorFrete());
            values.put("CodigoCliente", pedidoVenda.getCliente().getCodigo());

            result = database.update("PEDIDOVENDA", values, "Codigo = ?", new String[]{String.valueOf(pedidoVenda.getCodigo())});
        } catch (SQLException e) {
            Log.e("PedidoDeVendaDao", "Error updating data: " + e.getMessage());
        } finally {
            close();
        }
        return result;
    }

    public void delete(PedidoVenda pedidoVenda) {
        try {
            open();
            database.delete("PEDIDOVENDA", "Codigo = " + pedidoVenda.getCodigo(), null);
        } catch (SQLException e) {
            Log.e("PedidoDeVendaDao", "Error deleting data: " + e.getMessage());
        } finally {
            close();
        }
    }

    public List<PedidoVenda> getAllPedidos() {
        List<PedidoVenda> pedidos = new ArrayList<>();
        Cursor cursor = null;
        try {
            open();
            cursor = database.query("PEDIDOVENDA", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    PedidoVenda pedidoVenda = cursorToPedidoVenda(cursor);
                    pedidos.add(pedidoVenda);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("PedidoDeVendaDao", "Error fetching data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            close();
        }
        return pedidos;
    }

    private PedidoVenda cursorToPedidoVenda(Cursor cursor) {
        PedidoVenda pedidoVenda = new PedidoVenda();

        int codigoIndex = cursor.getColumnIndex("Codigo");
        int dataIndex = cursor.getColumnIndex("Data");
        int valorTotalIndex = cursor.getColumnIndex("ValorTotal");
        int condicaoPagamentoIndex = cursor.getColumnIndex("CondicaoPagamento");
        int numParcelasIndex = cursor.getColumnIndex("NumParcelas");
        int valorFreteIndex = cursor.getColumnIndex("ValorFrete");
        int clienteCodigoIndex = cursor.getColumnIndex("CodigoCliente");

        if (codigoIndex != -1) {
            pedidoVenda.setCodigo(cursor.getInt(codigoIndex));
        }
        if (dataIndex != -1) {
            pedidoVenda.setData(cursor.getString(dataIndex));
        }
        if (valorTotalIndex != -1) {
            pedidoVenda.setValorTotal(cursor.getDouble(valorTotalIndex));
        }
        if (condicaoPagamentoIndex != -1) {
            pedidoVenda.setCondicaoPagamento(cursor.getString(condicaoPagamentoIndex));
        }
        if (numParcelasIndex != -1) {
            pedidoVenda.setQuantidadeParcelas(cursor.getInt(numParcelasIndex));
        }
        if (valorFreteIndex != -1) {
            pedidoVenda.setValorFrete(cursor.getDouble(valorFreteIndex));
        }
        if (clienteCodigoIndex != -1) {
            int clienteCodigo = cursor.getInt(clienteCodigoIndex);
            ClienteDao clienteDao = ClienteDao.getInstancia(context);
            pedidoVenda.setCliente(clienteDao.getById(clienteCodigo));
        }

        return pedidoVenda;
    }

}
