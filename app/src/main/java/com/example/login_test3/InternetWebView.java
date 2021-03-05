package com.example.login_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InternetWebView extends BasicActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_internet_web_view);

        WebView webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        String blog_url = intent.getExtras().getString("blog_url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(blog_url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());



    }

}


