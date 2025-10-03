package com.stackmobile.backloggamer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddGameActivity extends AppCompatActivity {

    private EditText edtGameName, edtGamePlatform;
    private Spinner spnGameStatus;
    private Button btnSaveGame, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        edtGameName = findViewById(R.id.edtGameName);
        edtGamePlatform = findViewById(R.id.edtGamePlatform);
        spnGameStatus = findViewById(R.id.spnGameStatus);
        btnSaveGame = findViewById(R.id.btnSaveGame);
        btnBack = findViewById(R.id.btnBack);

        String[] status = {"Zerado", "Jogando", "Quero Jogar", "Pendente"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, status);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnGameStatus.setAdapter(adapter);

        btnSaveGame.setOnClickListener(v -> {
            String nome = edtGameName.getText().toString().trim();
            String plataforma = edtGamePlatform.getText().toString().trim();
            String statusSelecionado = spnGameStatus.getSelectedItem().toString();

            if (nome.isEmpty() || plataforma.isEmpty()) {
                Toast.makeText(AddGameActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("nome", nome);
            resultIntent.putExtra("plataforma", plataforma);
            resultIntent.putExtra("status", statusSelecionado);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnBack.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
