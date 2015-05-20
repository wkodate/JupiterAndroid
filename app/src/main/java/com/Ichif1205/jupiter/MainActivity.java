package com.Ichif1205.jupiter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.Ichif1205.jupiter.ad.Asterisk;
import com.Ichif1205.jupiter.analytics.GoogleTracker;
import com.Ichif1205.jupiter.http.AsyncFetcher;
import com.Ichif1205.jupiter.item.ItemAdapter;
import com.Ichif1205.jupiter.item.RssItem;
import com.google.android.gms.analytics.HitBuilders;

import java.util.List;

import jp.maru.mrd.IconCell;
import jp.maru.mrd.IconLoader;

/**
 * MainActivity.
 *
 * @author wkodate
 * @version 1.0.1
 */
public class MainActivity extends FragmentActivity implements
        LoaderCallbacks<List<RssItem>> {

    /**
     * ログ.
     */
    private static final String TAG = "MainActivity";

    /**
     * インテントの種類.
     */
    private static final String INTENT_TYPE = "text/plain";

    /**
     * Asterisk.
     */
    private final Asterisk ast = new Asterisk(Secret.AST_MEDIA_CODE);

    /**
     * GoogleTracker.
     */
    private GoogleTracker tracker;

    /**
     * Fetcher.
     */
    private AsyncFetcher asyncFetcher;

    public MainActivity() {
        Log.d(TAG, "Call Constructor.");
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Call onCreate.");

        // loaderの初期化
        getSupportLoaderManager().initLoader(0, null, this);
        // プログレスバー
        setContentView(R.layout.listview_progress_bar);

        // GoogleAnalyticsの設定
        tracker = new GoogleTracker(this);

        // Send a screen view.
        tracker.sendInitialHit(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected final void onStart() {
        super.onStart();
        // トラッキング開始
        tracker.start();
    }

    @Override
    protected final void onStop() {
        super.onStop();
        // トラッキング終了
        tracker.stop();
    }

    @Override
    protected final void onResume() {
        super.onResume();
        // 広告の読み込み
        ast.start();
    }

    @Override
    protected final void onPause() {
        // 広告読み込みの終了
        ast.stop();
        super.onPause();
    }

    @Override
    public final Loader<List<RssItem>> onCreateLoader(final int itemCount,
                                                       final Bundle bundle) {
        // 新しいLoaderが作成された時に呼ばれる
        Log.d(TAG, "Call onCreateLoader.");
        asyncFetcher = new AsyncFetcher(this);
        asyncFetcher.forceLoad();
        return asyncFetcher;
    }

    @Override
    public final void onLoadFinished(final Loader<List<RssItem>> loader,
                                     final List<RssItem> itemDataList) {
        // 前に作成したloaderがloadを完了した時に呼ばれる
        Log.d(TAG, "Call onLoadFinished.");

        if (itemDataList == null || itemDataList.size() == 0) {
            Log.d(TAG, "ItemDataList is empty.");
            setContentView(R.layout.activity_main_empty_item);
            return;
        }

        setContentView(R.layout.activity_main);
        initializeAd();
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ItemAdapter(this, 0, itemDataList));
        asyncFetcher.stopLoading();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent,
                                    final View view,
                                    final int position, final long id) {
                Log.d(TAG, "Call onItemClick.");
                // position番目のItemDataを取得
                RssItem item = itemDataList.get(position);
                tracker.sendHit(item.link, item.rssTitle);
                Intent webviewIntent = getWebviewIntent(item.link,
                        item.title);
                startActivity(webviewIntent);
            }
        });
    }

    private void initializeAd() {
        setAdView();
        ast.start();
    }

    /**
     * アスタのアイコン広告の準備.
     */
    private void setAdView() {
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
     * 受け取ったURLとタイトルをwebviewへインテントするためのインスタンスを生成.
     *
     * @param url   URL.
     * @param title タイトル.
     * @return webview intent
     */
    private Intent getWebviewIntent(final String url, final String title) {
        Intent intent = new Intent(getApplicationContext(),
                WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        return intent;
    }

    @Override
    public final void onLoaderReset(final Loader<List<RssItem>> arg0) {
        // 前に作成したloaderがリセットされた時に呼ばれる
        Log.d(TAG, "Call onLoadReset.");
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        Log.d(TAG, "Call onCreateOptionsMenu.");
        // menuファイルの読み込み
        getMenuInflater().inflate(R.menu.main, menu);
        // プロバイダの取得と共有インテントのセット
        MenuItem actionItem = menu.findItem(R.id.share);
        ShareActionProvider actionProvider = (ShareActionProvider) actionItem
                .getActionProvider();
        // アクションビュー取得前にデフォルトの履歴をセット
        actionProvider.setShareIntent(getDefaultShareIntent());

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * シェア用のインテントを返す.
     *
     * @return Intent.
     */
    private Intent getDefaultShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType(INTENT_TYPE);
        shareIntent.putExtra(Intent.EXTRA_TEXT, Constant.SHARE_TEXT);
        return shareIntent;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem menuitem) {
        Log.d(TAG, "Call onOptionsItemSelected.");
        return super.onOptionsItemSelected(menuitem);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * コンストラクタ.
         */
        public PlaceholderFragment() {
        }

        @Override
        public final View onCreateView(final LayoutInflater inflater,
                                       final ViewGroup container,
                                       final Bundle savedInstanceState) {
            Log.d(TAG, "Call onCreateView.");
            View rootView = inflater.inflate(R.layout.fragment_main,
                    container, false);
            return rootView;
        }
    }

}
