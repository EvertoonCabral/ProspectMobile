package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.EnderecoController;

public class EnderecoCadastroActivity extends AppCompatActivity {

    private EditText edCodigoEndereco, edLogradouro, edNumero, edBairro, edCidade, edUF;
    private Button btnSalvarEndereco;
    private EnderecoController enderecoController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco_cadastro);
        

                // Inicializando os componentes
                edCodigoEndereco = findViewById(R.id.edCodigoEndereco);
                edLogradouro = findViewById(R.id.edLogradouro);
                edNumero = findViewById(R.id.edNumero);
                edBairro = findViewById(R.id.edBairro);
                edCidade = findViewById(R.id.edCidade);
                edUF = findViewById(R.id.edUF);
                btnSalvarEndereco = findViewById(R.id.btnSalvarEndereco);

                enderecoController = new EnderecoController(this);

                btnSalvarEndereco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        salvarEndereco();
                    }
                });
            }

            private void salvarEndereco() {
                long resultado = enderecoController.salvarEndereco(
                        edCodigoEndereco.getText().toString(),
                        edLogradouro.getText().toString(),
                        edNumero.getText().toString(),
                        edBairro.getText().toString(),
                        edCidade.getText().toString(),
                        edUF.getText().toString());

                if (resultado < 0) {
                    Toast.makeText(this, "Erro na validação dos campos. Verifique os dados inseridos.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Endereço salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        }

