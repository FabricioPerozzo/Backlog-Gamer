package com.stackmobile.backloggamer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLoginEmail, edtLoginSenha;
    private Button btnLogin, btnLoginRegistrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginSenha = findViewById(R.id.edtLoginSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginRegistrar = findViewById(R.id.btnLoginRegistrar);

        btnLogin.setOnClickListener(v -> loginUser());

        btnLoginRegistrar.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = edtLoginEmail.getText().toString().trim();
        String password = edtLoginSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtLoginEmail.setError("Digite o email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtLoginSenha.setError("Digite a senha");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login realizado!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
