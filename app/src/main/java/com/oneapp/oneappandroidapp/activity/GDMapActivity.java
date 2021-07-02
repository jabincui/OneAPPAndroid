package com.oneapp.oneappandroidapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oneapp.oneappandroidapp.R;

public class GDMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_d_map);
        WebView webGD = findViewById(R.id.web_GDDT);
        webGD.getSettings().setJavaScriptEnabled(true);
        webGD.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webGD.loadUrl("https://oneapp.businsight.net/oneapp/Welcome");
    }
}