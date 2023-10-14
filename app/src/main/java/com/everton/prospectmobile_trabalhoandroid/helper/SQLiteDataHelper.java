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
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nome TEXT," +
                "CPF TEXT," +
                "DataNasc TEXT," +
                "CodigoEndereco INTEGER," +
                "FOREIGN KEY (CodigoEndereco) REFERENCES ENDERECO (Codigo))";

        String createEnderecoTable = "CREATE TABLE ENDERECO (" +
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Logradouro TEXT," +
                "Numero TEXT," +
                "Bairro TEXT," +
                "Cidade TEXT," +
                "UF TEXT)";

        String createItemTable = "CREATE TABLE ITEM (" +
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Descricao TEXT NOT NULL," +
                "ValorUnitario REAL NOT NULL," +
                "UnidadeMedida TEXT NOT NULL)";

        String createPedidoVendaTable = "CREATE TABLE PEDIDOVENDA (" +
                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Data TEXT," +
                "ValorTotal REAL," +
                "CondicaoPagamento TEXT," +
                "NumParcelas INTEGER," +
                "ValorFrete REAL," +
                "CodigoCliente INTEGER," +
                "CodigoEnderecoEntrega INTEGER," +
                "FOREIGN KEY (CodigoCliente) REFERENCES CLIENTE (Codigo)," +
                "FOREIGN KEY (CodigoEnderecoEntrega) REFERENCES ENDERECO (Codigo))";

        String createPedidoItemTable = "CREATE TABLE PEDIDO_ITEM (" +
                "CodigoPedido INTEGER," +
                "CodigoItem INTEGER," +
                "Quantidade INTEGER," +
                "FOREIGN KEY (CodigoPedido) REFERENCES PEDIDOVENDA (Codigo)," +
                "FOREIGN KEY (CodigoItem) REFERENCES ITEM (Codigo))";

        sqLiteDatabase.execSQL(createClienteTable);
        sqLiteDatabase.execSQL(createEnderecoTable);
        sqLiteDatabase.execSQL(createItemTable);
        sqLiteDatabase.execSQL(createPedidoVendaTable);
        sqLiteDatabase.execSQL(createPedidoItemTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Aqui você pode gerenciar atualizações de esquema de banco de dados, como adicionar ou remover colunas, etc.
    }
}
