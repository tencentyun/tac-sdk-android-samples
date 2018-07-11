package com.tencent.tac.tacanalytics;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tencent.tac.R;
import com.tencent.tac.analytics.TACAnalyticsService;

/**
 * <p>
 * Copyright 2010-2017 Tencent Cloud. All Rights Reserved.
 */

public class WebViewActivity extends AppCompatActivity {


    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);

        TACAnalyticsService.getInstance().monitorWebViewEvent(webView);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 尽量保证放在第一行
                if(TACAnalyticsService.getInstance().handleWebViewUrl(view, url)){
                    return true;
                }
                super.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/app_link_h5.html");
    }
}