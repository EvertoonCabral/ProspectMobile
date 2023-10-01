package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;

public class MainActivity extends AppCompatActivity {

    private Button btnTelaCadastroCliente;
    private ClienteController clienteController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clienteController = new ClienteController(this);

        btnTelaCadastroCliente = findViewById(R.id.btnTelaCadastroCliente);

        btnTelaCadastroCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ClienteCadastroActivity.class);
                startActivity(intent);


            }
        });




    }
}