package com.Ichif1205.jupiter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

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
     * プログレスバー.
     */
    private ProgressBar progressBar;

    /**
     * 表示数するURL.
     */
    private WebView webView;

    /**
     * 表示するURL.
     */
    private String permanentLink;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // url取得
        permanentLink = getIntentedUrl();
        // リンク情報を表示
        webView = (WebView) findViewById(R.id.webview);
        setStateOfWebView();
        webView.loadUrl(permanentLink);
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

            @Override
            public void onPageStarted(final WebView view, final String url,
                    final android.graphics.Bitmap bitmap) {
                // 読み込みを開始した時の処理
                super.onPageStarted(view, url, bitmap);
                // プログレスバーを表示
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                // 読み込みが完了した時の処理
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

        });
        webView.setWebChromeClient(new CustomWebChromeClient());
    }

    /**
     * 拡張したWebChromeClient.
     *
     * @author wkodate
     *
     */
    protected class CustomWebChromeClient extends WebChromeClient {
        @Override
        public final void onProgressChanged(final WebView view, final int progress) {
            // プログレスバーの進捗を更新
            progressBar.setProgress(progress);
        }

    }
}
