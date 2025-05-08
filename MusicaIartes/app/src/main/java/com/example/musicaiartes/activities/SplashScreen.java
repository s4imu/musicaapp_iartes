package com.example.musicaiartes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicaiartes.R;
import com.example.musicaiartes.activities.TelaLogin;

public class SplashScreen extends AppCompatActivity {

    static int TEMPO_SPLASH_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                irParaProximoActivity();
            }

            private void irParaProximoActivity() {
                Intent intent = new Intent(SplashScreen.this, TelaLogin.class);
                startActivity(intent);
                finish(); //finaliza o activity
            }
        }, TEMPO_SPLASH_SCREEN);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}