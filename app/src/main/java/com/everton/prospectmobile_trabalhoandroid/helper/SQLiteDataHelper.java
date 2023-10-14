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
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," + // Adicionado AUTOINCREMENT
                "Nome TEXT," +
                "CPF TEXT," +
                "DataNasc TEXT," +
                "CodigoEndereco INTEGER," +
                "FOREIGN KEY (CodigoEndereco) REFERENCES ENDERECO (Codigo))"; // Adicionada chave estrangeira para Endereco

        String createEnderecoTable = "CREATE TABLE ENDERECO (" +
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," + // Adicionado AUTOINCREMENT
                "Logradouro TEXT," +
                "Numero TEXT," + // Alterado para TEXT para permitir números de endereço como "12A", "3B", etc.
                "Bairro TEXT," +
                "Cidade TEXT," +
                "UF TEXT)";

        String createItemTable = "CREATE TABLE ITEM (" +
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," + // Adicionado AUTOINCREMENT
                "Descricao TEXT," +
                "ValorUnit REAL," +
                "UnidadeMedia TEXT)";

        String createPedidoVendaTable = "CREATE TABLE PEDIDOVENDA (" +
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," + // Adicionado AUTOINCREMENT
                "Data TEXT," +
                "ValorTotal REAL," +
                "CondicaoPagamento TEXT," +
                "NumParcelas INTEGER," +
                "ValorFrete REAL," +
                "CodigoCliente INTEGER," +
                "FOREIGN KEY (CodigoCliente) REFERENCES CLIENTE (Codigo))"; // Adicionada chave estrangeira para Cliente

        sqLiteDatabase.execSQL(createClienteTable);
        sqLiteDatabase.execSQL(createEnderecoTable);
        sqLiteDatabase.execSQL(createItemTable);
        sqLiteDatabase.execSQL(createPedidoVendaTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
