package com.example.gameoflife;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Thread() {
            public void run() {
                // загрузка начальных данных пользователя


                // имитация загрузки
                imitateLoading();

                // после загрузки переходим в главное меню
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);// по-умолчанию после загрузки запускается список операций
                startActivity(intent);
            }
        }.start();


    }

    private void imitateLoading() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
