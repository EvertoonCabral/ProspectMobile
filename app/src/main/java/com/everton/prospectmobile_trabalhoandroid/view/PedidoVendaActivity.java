package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;
import com.everton.prospectmobile_trabalhoandroid.controller.ItemController;
import com.everton.prospectmobile_trabalhoandroid.databinding.ActivityPedidoVendaBinding;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;
import com.everton.prospectmobile_trabalhoandroid.model.Item;
import com.everton.prospectmobile_trabalhoandroid.model.ItemPedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoVendaActivity extends AppCompatActivity {

    private ActivityPedidoVendaBinding binding;
    private ArrayList<Cliente> listaClientes;
    private Cliente clienteSelecionado;
    private ClienteController clienteController;
    private double valorTotal = 0;

    private List<ItemPedido> itensPedido = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPedidoVendaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clienteController = new ClienteController(this);
        listaClientes = clienteController.retornarTodosClientes();

        if (listaClientes != null && !listaClientes.isEmpty()) {
            ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
            binding.spinnerClientes.setAdapter(adapter);

            binding.spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    clienteSelecionado = listaClientes.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        }

        ItemController itemController = new ItemController(this);
        ArrayList<Item> listaItens = itemController.retornarTodosItens();

        if (listaItens != null && !listaItens.isEmpty()) {
            ArrayAdapter<Item> adapterItens = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaItens);
            binding.spinnerItens.setAdapter(adapterItens);

            binding.spinnerItens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Item itemSelecionado = listaItens.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });
        }

        binding.btnAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item itemSelecionado = (Item) binding.spinnerItens.getSelectedItem();
                int quantidade = Integer.parseInt(binding.etQuantidade.getText().toString());

                ItemPedido itemPedido = new ItemPedido(itemSelecionado, quantidade);
                itensPedido.add(itemPedido);

                valorTotal += itemSelecionado.getValorUnitario() * quantidade;
                atualizarValores();

                atualizarListViewItensPedido();
            }
        });

        binding.rgCondicaoPagamento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                atualizarValores();
            }
        });
    }

    private void atualizarListViewItensPedido() {
        ArrayAdapter<ItemPedido> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itensPedido);
        binding.lvItensPedido.setAdapter(adapter);
    }

    private void atualizarValores() {
        double valorFinal = valorTotal;

        if (binding.rbAVista.isChecked()) {
            valorFinal = valorTotal * 0.95; // Desconto de 5%
        } else if (binding.rbAPrazo.isChecked()) {
            valorFinal = valorTotal * 1.05; // Acréscimo de 5%
        }

        binding.tvValorTotal.setText("Valor Total: " + valorFinal);
        binding.tvTotalItens.setText("Total de Itens: " + itensPedido.size());
    }
}
