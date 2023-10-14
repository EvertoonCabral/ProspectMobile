package com.everton.prospectmobile_trabalhoandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.everton.prospectmobile_trabalhoandroid.helper.SQLiteDataHelper;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;
import com.everton.prospectmobile_trabalhoandroid.model.Endereco;

import java.util.ArrayList;

public class ClienteDao {

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

    public long insert(Cliente cliente) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", cliente.getNome());
        valores.put("CPF", cliente.getCpf());
        valores.put("DataNasc", cliente.getDataNasc());
        valores.put("CodigoEndereco", cliente.getEndereco().getCodigo()); // Aqui você pega o código do endereço associado ao cliente

        return bd.insert(tableName, null, valores);
    }

    public long update(Cliente cliente) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", cliente.getNome());
        valores.put("CPF", cliente.getCpf());
        valores.put("DataNasc", cliente.getDataNasc());
        valores.put("CodigoEndereco", cliente.getEndereco().getCodigo()); // Aqui você pega o código do endereço associado ao cliente

        String[] identificador = {String.valueOf(cliente.getCodigo())};
        return bd.update(tableName, valores, "Codigo = ?", identificador);
    }

    public long delete(Cliente cliente) {
        String[] identificador = {String.valueOf(cliente.getCodigo())};
        return bd.delete(tableName, "Codigo=?", identificador);
    }

    public ArrayList<Cliente> getAll() {
        ArrayList<Cliente> lista = new ArrayList<>();
        Cursor cursor = bd.query(tableName, colunas, null, null, null, null, "Codigo");
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setCodigo(cursor.getInt(0));
                cliente.setNome(cursor.getString(1));
                cliente.setCpf(cursor.getString(2));
                cliente.setDataNasc(cursor.getString(3));

                int enderecoCodigo = cursor.getInt(4);
                EnderecoDao enderecoDao = new EnderecoDao(context);
                Endereco endereco = enderecoDao.getById(enderecoCodigo);
                cliente.setEndereco(endereco);

                lista.add(cliente);
            } while (cursor.moveToNext());
        }
        return lista;
    }

    public Cliente getById(int id) {
        String[] identificador = {String.valueOf(id)};
        Cursor cursor = bd.query(tableName, colunas, "Codigo = ?", identificador, null, null, null);
        if (cursor.moveToFirst()) {
            Cliente cliente = new Cliente();
            cliente.setCodigo(cursor.getInt(0));
            cliente.setNome(cursor.getString(1));
            cliente.setCpf(cursor.getString(2));
            cliente.setDataNasc(cursor.getString(3));

            // Aqui, você pode buscar o endereço associado ao cliente usando o CodigoEndereco
            int enderecoCodigo = cursor.getInt(4);
            EnderecoDao enderecoDao = new EnderecoDao(context);
            Endereco endereco = enderecoDao.getById(enderecoCodigo);
            cliente.setEndereco(endereco);

            return cliente;
        }
        return null;
    }

    public Cliente getByCPF(String cpf) {
        Cliente cliente = null;
        Cursor cursor = bd.query("CLIENTE", null, "CPF = ?", new String[]{cpf}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cliente = cursorToCliente(cursor);
                break; // Como o CPF é único, podemos sair do loop assim que encontrarmos o cliente
            }
            cursor.close();
        }
        return cliente;
    }

    private Cliente cursorToCliente(Cursor cursor) {
        Cliente cliente = new Cliente();

        int codigoIndex = cursor.getColumnIndex("Codigo");
        int nomeIndex = cursor.getColumnIndex("Nome");
        int cpfIndex = cursor.getColumnIndex("CPF");
        int dataNascIndex = cursor.getColumnIndex("DataNasc");

        if (codigoIndex != -1) {
            cliente.setCodigo(cursor.getInt(codigoIndex));
        }
        if (nomeIndex != -1) {
            cliente.setNome(cursor.getString(nomeIndex));
        }
        if (cpfIndex != -1) {
            cliente.setCpf(cursor.getString(cpfIndex));
        }
        if (dataNascIndex != -1) {
            cliente.setDataNasc(cursor.getString(dataNascIndex));
        }
        return cliente;
    }



}

