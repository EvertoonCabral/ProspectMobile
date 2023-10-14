package com.everton.prospectmobile_trabalhoandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.everton.prospectmobile_trabalhoandroid.R;
import com.everton.prospectmobile_trabalhoandroid.controller.ItemController;

public class ItemCadastroActivity extends AppCompatActivity {

    private EditText edDescricaoItem, edValorUnitario, edUnidadeMedida;
    private Button btnSalvarItem;
    private ItemController itemController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_cadastro);

        // Inicializando os componentes
        edDescricaoItem = findViewById(R.id.edDescricaoItem);
        edValorUnitario = findViewById(R.id.edValorUnitario);
        edUnidadeMedida = findViewById(R.id.edUnidadeMedida);
        btnSalvarItem = findViewById(R.id.btnSalvarItem);

        itemController = new ItemController(this);

        btnSalvarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarItem();
            }
        });
    }

    private void salvarItem() {
        String descricao = edDescricaoItem.getText().toString();
        String valorUnitario = edValorUnitario.getText().toString();
        String unidadeMedida = edUnidadeMedida.getText().toString();

        long resultado = itemController.salvarItem(descricao, valorUnitario, unidadeMedida);

        if (resultado < 0) {
            Toast.makeText(this, "Erro na validação dos campos. Verifique os dados inseridos.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Item salvo com sucesso!", Toast.LENGTH_SHORT).show();
            limparCampos();
        }
    }

    private void limparCampos() {
        edDescricaoItem.setText("");
        edValorUnitario.setText("");
        edUnidadeMedida.setText("");
    }
}
