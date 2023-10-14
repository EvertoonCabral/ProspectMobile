package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;
import com.everton.prospectmobile_trabalhoandroid.databinding.ActivityPedidoVendaBinding;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;

import java.util.ArrayList;

public class PedidoVendaActivity extends AppCompatActivity {

    private ActivityPedidoVendaBinding binding;
    private ArrayList<Cliente> listaClientes;
    private Cliente clienteSelecionado;
    private ClienteController clienteController; // Supondo que você tenha um ClienteController para buscar os clientes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPedidoVendaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clienteController = new ClienteController(this);
        listaClientes = clienteController.retornarTodosClientes(); // Supondo que você tenha um método para pegar todos os clientes

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        binding.spinnerClientes.setAdapter(adapter);


        binding.spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                clienteSelecionado = listaClientes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nada selecionado
            }
        });
    }
}
