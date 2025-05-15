package com.example.musicaiartes.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicaiartes.R;

public class TelaLogin extends AppCompatActivity {

    public EditText login;
    public EditText password;
    public TextView usuarioinvalido;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);

        Log.i("Debug", "Tela inicial carregada");

        login = findViewById(R.id.editLogin);
        password = findViewById(R.id.editPassword);
        usuarioinvalido = findViewById(R.id.usuarioinvalido);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fazLogin(View view) {
        String usuario = login.getText().toString();
        String senha = password.getText().toString();

        if (usuario.equals("admin") && senha.equals("admin")) {
            Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

            // Redirecionar para MainActivity
            Intent intent = new Intent(TelaLogin.this, MainActivity.class);
            startActivity(intent);
            finish(); // Encerra a tela de login para que o usuário não volte com o botão "Voltar"
        } else {
            usuarioinvalido.setVisibility(View.VISIBLE);

            // Usando Handler corretamente no Android 11+
            new Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            usuarioinvalido.setVisibility(View.INVISIBLE);
                        }
                    },
                    3000 // 3 segundos
            );
        }
    }
}
