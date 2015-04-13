package com.ballsapp.www.ballsapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;

public class Portal extends Activity {

    private WebView web_view;
    private final String android_address = "http://www.ballsapp.com/and/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_portal);

        web_view = (WebView) findViewById(R.id.webview);
        web_view.setWebViewClient(new WebViewClient());
        web_view.addJavascriptInterface(new WebAppInterface(this, web_view), "BallsAppAndroid");
        web_view.loadUrl(android_address);

        WebSettings web_settings = web_view.getSettings();
        web_settings.setJavaScriptEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        web_view.loadUrl(android_address);
    }
}
