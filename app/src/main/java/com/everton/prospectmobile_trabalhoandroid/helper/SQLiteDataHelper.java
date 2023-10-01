package com.everton.prospectmobile_trabalhoandroid.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDataHelper extends SQLiteOpenHelper {


    public SQLiteDataHelper(@Nullable Context context,
                            @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory,
                            int version) {

        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createClienteTable = "CREATE TABLE CLIENTE (" +
                "Codigo INTEGER PRIMARY KEY," +
                "Nome TEXT," +
                "CPF TEXT," +
                "DataNasc TEXT," +
                "CodigoEndereco INTEGER)";

        String createEnderecoTable = "CREATE TABLE ENDERECO (" +
                "Codigo INTEGER PRIMARY KEY," +
                "Logradouro TEXT," +
                "Numero INTEGER," +
                "Bairro TEXT," +
                "Cidade TEXT," +
                "UF TEXT)";

        String createItemTable = "CREATE TABLE ITEM (" +
                "Codigo INTEGER PRIMARY KEY," +
                "Descricao TEXT," +
                "ValorUnit REAL," +
                "UnidadeMedia TEXT)";

        String createPedidoVendaTable = "CREATE TABLE PEDIDOVENDA (" +
                "Codigo INTEGER PRIMARY KEY," +
                "Data TEXT," +
                "ValorTotal REAL," +
                "CondicaoPagamento TEXT," +
                "NumParcelas INTEGER," +
                "ValorFrete REAL," +
                "CodigoCliente INTEGER," +
                "FOREIGN KEY (CodigoCliente) REFERENCES CLIENTE (Codigo))";

        sqLiteDatabase.execSQL(createClienteTable);
        sqLiteDatabase.execSQL(createEnderecoTable);
        sqLiteDatabase.execSQL(createItemTable);
        sqLiteDatabase.execSQL(createPedidoVendaTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
