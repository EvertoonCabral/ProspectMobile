package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;

public class ClienteCadastroActivity extends AppCompatActivity {

    private EditText edCodigoCliente;
    private EditText edNomeCliente;
    private EditText edCPFCliente;
    private EditText edDataNascCliente;
    private EditText edCodigoEndereco;
    private ClienteController clienteController;
    private Button btnSalvarCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente_cadastro);
        setTitle("Cadastrar Cliente");

        clienteController = new ClienteController(this);


        edCodigoCliente = findViewById(R.id.edCodigoCliente);
        edNomeCliente = findViewById(R.id.edNomeCliente);
        edCPFCliente = findViewById(R.id.edCPFCliente);
        edDataNascCliente = findViewById(R.id.edDataNascCliente);
        edCodigoEndereco = findViewById(R.id.edCodigoEndereco);
        btnSalvarCliente = findViewById(R.id.btnSalvarCliente);


        btnSalvarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCliente();
            }
        });
    }

    private void salvarCliente() {
        long resultado = clienteController.SalvarCliente(
                edCodigoCliente.getText().toString(),
                edNomeCliente.getText().toString(),
                edCPFCliente.getText().toString(),
                edDataNascCliente.getText().toString(),
                edCodigoEndereco.getText().toString());

        if (resultado < 0) {
            Toast.makeText(this, "Erro na validação dos campos. Verifique os dados inseridos.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

}