package com.example.liurongchan.traingdemo.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by liurongchan on 14/10/23.
 */
public class ArticleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Example);
        setContentView(R.layout.article_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl(getResources().getString(R.string.article_url));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.article_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home :
                    finish();
                    break;
            }
        return true;
    }
}
