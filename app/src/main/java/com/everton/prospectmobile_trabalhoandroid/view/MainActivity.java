package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;
import com.everton.prospectmobile_trabalhoandroid.databinding.ActivityMainBinding; // Import do binding da sua activity

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // Referência para o binding
    private ClienteController clienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializando o View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clienteController = new ClienteController(this);

        // Usando o binding para acessar o botão e ligar à tela de cadastro de cliente
        binding.btnTelaCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClienteCadastroActivity.class);
                startActivity(intent);
            }
        });

        binding.btnTelaCadastroEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnderecoCadastroActivity.class);
                startActivity(intent);
            }
        });




        binding.btnTelaPedidoVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PedidoVendaActivity.class);
                startActivity(intent);

            }

        });

        binding.btnTelaCadastroItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemCadastroActivity.class);
                startActivity(intent);

            }

        });


    }
}
