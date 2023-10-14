package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.EnderecoController;
import com.everton.prospectmobile_trabalhoandroid.databinding.ActivityEnderecoCadastroBinding;

public class EnderecoCadastroActivity extends AppCompatActivity {

    private ActivityEnderecoCadastroBinding binding;
    private EnderecoController enderecoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnderecoCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        enderecoController = new EnderecoController(this);

        binding.btnSalvarEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEndereco();
            }
        });
    }

    private void salvarEndereco() {
        long resultado = enderecoController.salvarEndereco(
                binding.edLogradouro.getText().toString(),
                binding.edNumero.getText().toString(),
                binding.edBairro.getText().toString(),
                binding.edCidade.getText().toString(),
                binding.edUF.getText().toString());

        if (resultado < 0) {
            Toast.makeText(this, "Erro na validação dos campos. Verifique os dados inseridos.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Endereço salvo com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
}
