package com.example.musicaiartes.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
        setContentView(R.layout.activity_main);

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

        String msg = "O login é " + usuario + " e a senha é " + senha;

        // Verificação básica com valores fixos
        if (usuario.equals("admin") && senha.equals("admin")) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            // Você pode adicionar outra ação aqui, como abrir uma nova tela futuramente
        } else {
            usuarioinvalido.setVisibility(View.VISIBLE);
        }
    }
}
