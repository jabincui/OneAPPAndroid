package com.oneapp.oneappandroidapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.view.ImgTxtButton;

public class LifeServiceActivity extends AppCompatActivity {

    private ImgTxtButton btnGdMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_service);

        btnGdMap = findViewById(R.id.btn_GDDT);
        btnGdMap.setMCallback(v -> {
            Intent intent = new Intent(LifeServiceActivity.this, GDMapActivity.class);
            startActivity(intent);
        });
    }
}