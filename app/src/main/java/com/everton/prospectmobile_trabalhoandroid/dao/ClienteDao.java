package com.everton.prospectmobile_trabalhoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.everton.prospectmobile_trabalhoandroid.helper.SQLiteDataHelper;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;

import java.util.ArrayList;

public class ClienteDao implements GenericDao<Cliente>{


    private SQLiteDataHelper sqLiteDataHelper;
    private SQLiteDatabase bd;
    private String[] colunas = {"Codigo", "Nome", "CPF", "DataNasc", "CodigoEndereco"};
    private String tableName = "CLIENTE";
    private Context context;
    private static ClienteDao instancia;

    public static ClienteDao getInstancia(Context context) {
        if (instancia == null)
            return instancia = new ClienteDao(context);
        else
            return instancia;
    }


    private ClienteDao(Context context) {
        this.context = context;
        sqLiteDataHelper = new SQLiteDataHelper(this.context, "UNIPAR",null,1);
        bd = sqLiteDataHelper.getWritableDatabase();
    }



    @Override
    public long insert(Cliente obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("Nome", obj.getNome());
            valores.put("CPF", obj.getCpf());
            valores.put("DataNasc", obj.getDataNasc());
            valores.put("CodigoEndereco", obj.getCodEndereco());


            //método para inserir na tabela (<nome da tabela>, <coluna especifica que queira inserir>, <dados>);
            //retorna a linha que foi inserida na tabela
            return bd.insert(tableName, null, valores);

        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.insert(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long update(Cliente obj) {

        try{
            ContentValues valores = new ContentValues();
            valores.put("Nome", obj.getNome());
            valores.put("CPF", obj.getCpf());
            valores.put("DataNasc", obj.getDataNasc());
            valores.put("CodigoEndereco", obj.getCodEndereco());
            String[] identificador = {String.valueOf(obj.getCodigo())};

            return bd.update(tableName, valores, "Codigo = ?",identificador);

        }catch (SQLException ex){

            Log.e("ERRO", "ClienteDao.update(): " + ex.getMessage());
        }
        return -1;

    }

    @Override
    public long delete(Cliente obj) {

        try{
            String[] identificador = {String.valueOf(obj.getCodigo())};
            return bd.delete(tableName, "Codigo=?", identificador);

        }catch (SQLException e){
            Log.e("ERRO", "ClienteDao.delete(): " + e.getMessage());
        }

        return -1;
    }
    @Override
    public ArrayList<Cliente> getAll() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query(tableName, colunas, null, null,

                    null, null, "Codigo");
            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(cursor.getInt(0));
                    cliente.setNome(cursor.getString(1));
                    cliente.setCpf(cursor.getString(2));
                    cliente.setDataNasc(cursor.getString(3));
                    cliente.setCodEndereco(cursor.getInt(4));
                    lista.add(cliente);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ERRO", "ClienteDao.getAll(): " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Cliente getById(int id) {
        try {
            String[] identificador = {String.valueOf(id)};
            Cursor cursor = bd.query(tableName, colunas, "Codigo = ?", identificador,
                    null, null, null);


            if (cursor.moveToFirst()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(cursor.getInt(0));
                cliente.setNome(cursor.getString(1));
                cliente.setCpf(cursor.getString(2));
                cliente.setDataNasc(cursor.getString(3));
                cliente.setCodEndereco(cursor.getInt(4));
                return cliente;
            }
        } catch (SQLException e) {
            Log.e("ERRO", "ClienteDao.getById(): " + e.getMessage());
        }
        return null;
    }
}
