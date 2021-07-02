package com.oneapp.oneappandroidapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oneapp.oneappandroidapp.R;

public class TzcWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzc_web);
        WebView webTZC = findViewById(R.id.web_TZCXZ);
        webTZC.getSettings().setJavaScriptEnabled(true);
        webTZC.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webTZC.loadUrl("https://720yun.com/t/3e9judskOy4#scene_id=68800528");
    }
}