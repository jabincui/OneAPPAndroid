package com.oneapp.oneappandroidapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oneapp.oneappandroidapp.R;

public class UniWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_web);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView webUni = findViewById(R.id.web_uni);
        webUni.getSettings().setJavaScriptEnabled(true);
        webUni.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webUni.loadUrl(url);
    }
}