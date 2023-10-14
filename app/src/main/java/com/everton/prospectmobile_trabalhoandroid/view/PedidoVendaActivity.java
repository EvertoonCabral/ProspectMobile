package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;
import com.everton.prospectmobile_trabalhoandroid.controller.EnderecoController;
import com.everton.prospectmobile_trabalhoandroid.controller.ItemController;
import com.everton.prospectmobile_trabalhoandroid.dao.PedidoDeVendaDao;
import com.everton.prospectmobile_trabalhoandroid.databinding.ActivityPedidoVendaBinding;
import com.everton.prospectmobile_trabalhoandroid.model.Cliente;
import com.everton.prospectmobile_trabalhoandroid.model.Item;
import com.everton.prospectmobile_trabalhoandroid.model.Endereco;

import com.everton.prospectmobile_trabalhoandroid.model.ItemPedido;
import com.everton.prospectmobile_trabalhoandroid.model.PedidoVenda;

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

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        binding.spinnerClientes.setAdapter(adapter);

        binding.spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                clienteSelecionado = listaClientes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        ItemController itemController = new ItemController(this);
        ArrayList<Item> listaItens = itemController.retornarTodosItens();

        ArrayAdapter<Item> adapterItens = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaItens);
        binding.spinnerItens.setAdapter(adapterItens);

        binding.spinnerItens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Item itemSelecionado = listaItens.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        binding.btnAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etQuantidade.getText().toString().isEmpty()) {
                    Toast.makeText(PedidoVendaActivity.this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantidade = Integer.parseInt(binding.etQuantidade.getText().toString());
                if (quantidade <= 0) {
                    Toast.makeText(PedidoVendaActivity.this, "Quantidade deve ser maior que zero", Toast.LENGTH_SHORT).show();
                    return;
                }

                Item itemSelecionado = (Item) binding.spinnerItens.getSelectedItem();
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
                if (checkedId == R.id.rbAPrazo) {
                    binding.etQuantidadeParcelas.setVisibility(View.VISIBLE);
                    binding.lvParcelas.setVisibility(View.VISIBLE);
                } else {
                    binding.etQuantidadeParcelas.setVisibility(View.GONE);
                    binding.lvParcelas.setVisibility(View.GONE);
                }
                atualizarValores();
            }
        });

        binding.etQuantidadeParcelas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                atualizarParcelas();
            }
        });

        binding.btnConcluirPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedidoVenda pedido = new PedidoVenda();
                pedido.setCodigo(clienteSelecionado.getCodigo());
                pedido.setValorTotal(valorTotal + calcularFrete((Endereco) binding.spinnerEnderecos.getSelectedItem()));
                pedido.setCondicaoPagamento(binding.rbAVista.isChecked() ? "À vista" : "A prazo");
                pedido.setEnderecoEntrega((Endereco) binding.spinnerEnderecos.getSelectedItem());

                PedidoDeVendaDao dao = new PedidoDeVendaDao(PedidoVendaActivity.this);
                long result = dao.insert(pedido);

                if (result != -1) {
                    Toast.makeText(PedidoVendaActivity.this, "Pedido cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    // Limpar campos
                    binding.etQuantidade.setText("");
                } else {
                    Toast.makeText(PedidoVendaActivity.this, "Erro ao salvar o pedido.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        EnderecoController enderecoController = new EnderecoController(this);
        ArrayList<Endereco> listaEnderecos = enderecoController.retornarTodosEnderecos();

        if (listaEnderecos != null && !listaEnderecos.isEmpty()) {
            ArrayAdapter<Endereco> adapterEnderecos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEnderecos);
            binding.spinnerEnderecos.setAdapter(adapterEnderecos);
        }

        binding.spinnerEnderecos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Endereco enderecoSelecionado = (Endereco) binding.spinnerEnderecos.getSelectedItem();
                double valorFrete = calcularFrete(enderecoSelecionado);
                // Atualize o valor total do pedido com o valor do frete
                atualizarValorTotalComFrete(valorFrete);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });





    }


    private double calcularFrete(Endereco endereco) {
        if (!endereco.getCidade().equalsIgnoreCase("Toledo-PR")) {
            if (endereco.getUf().equalsIgnoreCase("PR")) {
                return 20.00;
            } else {
                return 50.00;
            }
        }
        return 0;
    }

    private void atualizarValorTotalComFrete(double valorFrete) {
        double valorFinal = valorTotal + valorFrete;
        // Se você tiver descontos ou acréscimos, aplique-os aqui
        binding.tvValorTotal.setText("Valor Total (com frete): " + valorFinal);
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

    private void atualizarParcelas() {
        if (!binding.etQuantidadeParcelas.getText().toString().isEmpty()) {
            int numParcelas = Integer.parseInt(binding.etQuantidadeParcelas.getText().toString());
            double valorParcela = valorTotal / numParcelas;

            List<String> parcelas = new ArrayList<>();
            for (int i = 1; i <= numParcelas; i++) {
                parcelas.add("Parcela " + i + ": " + valorParcela);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parcelas);
            binding.lvParcelas.setAdapter(adapter);
        }
    }
}
