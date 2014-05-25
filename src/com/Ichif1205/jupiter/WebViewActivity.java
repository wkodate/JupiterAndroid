package com.Ichif1205.jupiter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * WebViewActivity.
 *
 * @author wkodate
 *
 */
public class WebViewActivity extends Activity {

    /**
     * ログ.
     */
    private static final String TAG = "WebViewActivity";

    /**
     * 表示数するURL.
     */
    private WebView webView;

    /**
     * 表示数するURL.
     */
    private String url;

    /**
     * コンストラクタ.
     */
    public WebViewActivity() {
        Log.d(TAG, "Call Constructor.");
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Call onCreate.");
        setContentView(R.layout.activity_webview);
        // url取得
        url = getIntentedUrl();
        // リンク情報を表示
        webView = (WebView) findViewById(R.id.webview);
        setStateOfWebView();
        webView.loadUrl(url);
    }

    /**
     * Intentで送られてきたURLの取得.
     *
     * @return intentで送られてきたURL.
     */
    private String getIntentedUrl() {
        Intent intent = getIntent();
        String intentedUrl = (String) intent.getExtras().get("url");
        Log.i(TAG, intentedUrl);
        return intentedUrl;
    }

    /**
     * WebViewの設定.
     */
    private void setStateOfWebView() {
        WebSettings settings = webView.getSettings();
        // JavaScriptを有効
        settings.setJavaScriptEnabled(true);
        // Formデータの保存を無効
        settings.setSaveFormData(false);
        // Zoom機能を無効
        settings.setSupportZoom(false);

        // WebViewの通知リクエストの処理
        webView.setWebViewClient(new WebViewClient() {
            // ページ遷移する前に呼ばれる
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view,
                    final String url) {
                // 外部ブラウザでなく内部ブラウザを利用
                return false;
            }

            // エラー時に呼ばれる
            @Override
            public void onReceivedError(final WebView view, final int errorCode,
                    final String description, final String failingUrl) {
            }
        });
    }
}
