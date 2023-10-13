package com.everton.prospectmobile_trabalhoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.everton.prospectmobile_trabalhoandroid.model.Endereco;

import com.everton.prospectmobile_trabalhoandroid.helper.SQLiteDataHelper;

import java.util.ArrayList;

public class EnderecoDao implements GenericDao<Endereco>{

    private SQLiteDataHelper  sqLiteDataHelper;
    private SQLiteDatabase bd;
    private String[] colunas = {"Codigo", "Logradouro", "Numero", "Bairro", "Cidade", "UF"};
    private String tableName = "ENDERECO";
    private Context context;
    private static EnderecoDao instancia;

    public static EnderecoDao getInstancia(Context context) {
        if (instancia == null)
            return instancia = new EnderecoDao(context);
        else
            return instancia;
    }

    private EnderecoDao(Context context) {
        this.context = context;
        sqLiteDataHelper = new SQLiteDataHelper(this.context,"UNIPAR",null,1);
        bd = sqLiteDataHelper.getWritableDatabase();
    }

    public long insert(Endereco endereco) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("Logradouro", endereco.getLogradouro());
            valores.put("Numero", endereco.getNumero());
            valores.put("Bairro", endereco.getBairro());
            valores.put("Cidade", endereco.getCidade());
            valores.put("UF", endereco.getUf());

            return bd.insert(tableName, null, valores);

        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.insert(): " + ex.getMessage());
            return -1;
        }
    }

    public long update(Endereco endereco) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("Logradouro", endereco.getLogradouro());
            valores.put("Numero", endereco.getNumero());
            valores.put("Bairro", endereco.getBairro());
            valores.put("Cidade", endereco.getCidade());
            valores.put("UF", endereco.getUf());

            String[] identificador = {String.valueOf(endereco.getCodigo())};
            return bd.update(tableName, valores, "Codigo = ?", identificador);

        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.update(): " + ex.getMessage());
            return -1;
        }
    }

    public long delete(Endereco endereco) {
        try {
            String[] identificador = {String.valueOf(endereco.getCodigo())};
            return bd.delete(tableName, "Codigo=?", identificador);

        } catch (SQLException e) {
            Log.e("ERRO", "EnderecoDao.delete(): " + e.getMessage());
            return -1;
        }
    }

    public ArrayList<Endereco> getAll() {
        ArrayList<Endereco> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query(tableName, colunas, null, null, null, null, "Codigo");
            if (cursor.moveToFirst()) {
                do {
                    Endereco endereco = new Endereco();
                    endereco.setCodigo(cursor.getInt(0));
                    endereco.setLogradouro(cursor.getString(1));
                    endereco.setNumero(cursor.getString(2));
                    endereco.setBairro(cursor.getString(3));
                    endereco.setCidade(cursor.getString(4));
                    endereco.setUf(cursor.getString(5));
                    lista.add(endereco);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ERRO", "EnderecoDao.getAll(): " + e.getMessage());
        }
        return lista;
    }

    public Endereco getById(int id) {
        try {
            String[] identificador = {String.valueOf(id)};
            Cursor cursor = bd.query(tableName, colunas, "Codigo = ?", identificador, null, null, null);

            if (cursor.moveToFirst()) {
                Endereco endereco = new Endereco();
                endereco.setCodigo(cursor.getInt(0));
                endereco.setLogradouro(cursor.getString(1));
                endereco.setNumero(cursor.getString(2));
                endereco.setBairro(cursor.getString(3));
                endereco.setCidade(cursor.getString(4));
                endereco.setUf(cursor.getString(5));
                return endereco;
            }
        } catch (SQLException e) {
            Log.e("ERRO", "EnderecoDao.getById(): " + e.getMessage());
        }
        return null;
    }

}
