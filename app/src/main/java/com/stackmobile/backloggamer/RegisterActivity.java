package com.stackmobile.backloggamer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnRegistrar.setOnClickListener(v -> registerUser());

        btnVoltar.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Digite o email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtSenha.setError("Digite a senha");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
