package com.Ichif1205.jupiter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.Ichif1205.jupiter.ad.Asterisk;
import com.Ichif1205.jupiter.item.WebViewItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jp.maru.mrd.IconCell;
import jp.maru.mrd.IconLoader;

/**
 * WebViewActivity.
 *
 * @author wkodate
 */
public class WebViewActivity extends Activity {

    private static final String TAG = "WebViewActivity";

    /**
     * 広告を表示するか否か.
     */
    private static final boolean DISPLAY_AD = true;

    /**
     * Asterisk.
     */
    private final Asterisk ast = new Asterisk(Secret.AST_MEDIA_CODE);

    /**
     * プログレスバー.
     */
    private ProgressBar progressBar;

    private WebView webView;

    private WebViewItem webViewItem;

    public WebViewActivity() {
        Log.d(TAG, "Call Constructor.");
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Call onCreate.");
        setActivityView();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // リンク情報を読み込み
        webView = (WebView) findViewById(R.id.webview);
        webViewItem = new WebViewItem(getIntent());
        setStateOfWebView();
        webView.loadUrl(webViewItem.permanentLink);

        initializeAd();
        // アイコンをセット
        setWebViewButton();
    }

    private void setActivityView() {
        if (DISPLAY_AD) {
            setContentView(R.layout.activity_webview);
            return;
        }
        setContentView(R.layout.activity_webview_wo_ad);
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
     */
    protected class CustomWebChromeClient extends WebChromeClient {
        @Override
        public final void onProgressChanged(final WebView view, final int progress) {
            // プログレスバーの進捗を更新
            progressBar.setProgress(progress);
        }
    }

    private void initializeAd() {
        setAdView();
        ast.start();
    }

    /**
     * アスタのアイコン広告をセット.
     */
    public final void setAdView() {
        if (!DISPLAY_AD) {
            return;
        }
        if (ast.isStarting() || !ast.isValidCode()) {
            return;
        }
        // 広告のアイコンをビューにセット
        IconLoader<Integer> iconLoader = ast.initIconLoader(this);
        ((IconCell) findViewById(R.id.myCell1)).setTitleColor(Asterisk.AD_TITLE_COLOR);
        ((IconCell) findViewById(R.id.myCell1)).addToIconLoader(iconLoader);
        ((IconCell) findViewById(R.id.myCell2)).setTitleColor(Asterisk.AD_TITLE_COLOR);
        ((IconCell) findViewById(R.id.myCell2)).addToIconLoader(iconLoader);
        ((IconCell) findViewById(R.id.myCell3)).setTitleColor(Asterisk.AD_TITLE_COLOR);
        ((IconCell) findViewById(R.id.myCell3)).addToIconLoader(iconLoader);
        ((IconCell) findViewById(R.id.myCell4)).setTitleColor(Asterisk.AD_TITLE_COLOR);
        ((IconCell) findViewById(R.id.myCell4)).addToIconLoader(iconLoader);
        ((IconCell) findViewById(R.id.myCell5)).setTitleColor(Asterisk.AD_TITLE_COLOR);
        ((IconCell) findViewById(R.id.myCell5)).addToIconLoader(iconLoader);
    }

    /**
     * WebView用のボタンをセット.
     * <p/>
     * TODO: activeじゃない場合はクリックできないようにする
     */
    private void setWebViewButton() {

        // 戻るボタンの設定
        ImageButton backButton = (ImageButton) findViewById(R.id.backward);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                webView.goBack();
            }
        });

        // 進むボタンの設定
        ImageButton forwardButton = (ImageButton) findViewById(R.id.forward);
        forwardButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                webView.goForward();
            }
        });

        // 更新ボタンの設定
        ImageButton reloadButton = (ImageButton) findViewById(R.id.reload);
        reloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                webView.reload();
            }
        });

        // 共有ボタンの設定
        ImageButton twitterButton = (ImageButton) findViewById(R.id.twitter);
        twitterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                String url = "http://twitter.com/share?text=" + encodeTweetText();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    /**
     * つぶやく文字列をURLエンコードして返す.
     */
    private String encodeTweetText() {
        try {
            return URLEncoder.encode(webViewItem.title + " " + webViewItem.permanentLink + " "
                    + Constant.HASH_TAG, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected final void onResume() {
        super.onResume();
        ast.start();
    }

    @Override
    protected final void onPause() {
        ast.stop();
        super.onPause();
    }

}