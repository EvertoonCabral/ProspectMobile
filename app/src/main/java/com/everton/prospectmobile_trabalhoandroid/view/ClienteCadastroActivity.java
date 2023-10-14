package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ClienteController;
import com.everton.prospectmobile_trabalhoandroid.controller.EnderecoController;
import com.everton.prospectmobile_trabalhoandroid.dao.EnderecoDao;
import com.everton.prospectmobile_trabalhoandroid.databinding.ActivityClienteCadastroBinding;
import com.everton.prospectmobile_trabalhoandroid.model.Endereco;
import java.util.ArrayList;
import java.util.List;

public class ClienteCadastroActivity extends AppCompatActivity {

    private ClienteController clienteController;
    private EnderecoController enderecoController;
    private ActivityClienteCadastroBinding binding;
    private int enderecoSelecionadoId;
    private List<Endereco> enderecos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClienteCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Cadastrar Cliente");

        clienteController = new ClienteController(this);
        enderecoController = new EnderecoController(this);

        EnderecoDao enderecoDao = EnderecoDao.getInstancia(this);
        enderecos = enderecoDao.getAll();

        preencherSpinnerEnderecos();

        binding.btnSalvarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCliente();
            }
        });
    }

    private void preencherSpinnerEnderecos() {
        enderecos = enderecoController.retornarTodosEnderecos();
        List<String> enderecoList = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            enderecoList.add(endereco.getLogradouro() + ", " + endereco.getNumero() + " - " + endereco.getBairro());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, enderecoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerEndereco.setAdapter(adapter);

        binding.spinnerEndereco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enderecoSelecionadoId = enderecos.get(position).getCodigo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enderecoSelecionadoId = -1;
            }
        });
    }

    private void salvarCliente() {
        // 1. Buscar o Endereco usando o enderecoSelecionadoId
        EnderecoDao enderecoDao = EnderecoDao.getInstancia(this);
        Endereco enderecoSelecionado = enderecoDao.getById(enderecoSelecionadoId);

        // 2. Verificar se o endereço foi encontrado
        if (enderecoSelecionado == null) {
            Toast.makeText(this, "Endereço não encontrado. Selecione um endereço válido.", Toast.LENGTH_LONG).show();
            return;
        }

        // 3. Salvar o cliente usando o endereço obtido
        long resultado = clienteController.salvarCliente(
                binding.edNomeCliente.getText().toString(),
                binding.edCPFCliente.getText().toString(),
                binding.edDataNascCliente.getText().toString(),
                enderecoSelecionado);

        if (resultado < 0) {
            Toast.makeText(this, "Erro na validação dos campos. Verifique os dados inseridos.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
}
