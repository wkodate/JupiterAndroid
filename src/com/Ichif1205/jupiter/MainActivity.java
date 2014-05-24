package com.Ichif1205.jupiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Ichif1205.jupiter.http.AsyncFetcher;

/**
 * MainActivity.
 *
 * @author wkodate
 *
 */
public class MainActivity extends FragmentActivity implements
        LoaderCallbacks<List<Map<String, String>>> {

    /**
     * ログ.
     */
    private static final String TAG = "MainActivity";

    /**
     * データ取得時のスリープ.
     */
    private static final int SLEEP_TIME = 3000;

    /**
     * タイトルのリスト.
     */
    private final List<String> titles;

    /**
     * リンクのリスト.
     */
    private final List<String> links;

    /**
     * ListView.
     */
    private ListView listView;

    /**
     * コンストラクタ.
     */
    public MainActivity() {
        Log.d(TAG, "Call Constructor.");
        // item取得
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Call onCreate.");

        try {
            // loaderの初期化
            getSupportLoaderManager().initLoader(0, null, this);
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listView = new ListView(this);
        setContentView(listView);

        // リストビューに入れるアイテムのAdapterを生成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_row, titles);

        // Adapterを指定
        listView.setAdapter(adapter);

        // クリックされた時の処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent,
                    final View view,
                    final int position, final long id) {
                Log.d(TAG, "Call onItemClick.");
                // リンク情報を取得して表示
                WebView webView = new WebView(view.getContext());
                String[] urls = links.toArray(new String[links.size()]);
                // 指定されたURLのロード
                webView.loadUrl(urls[position]);
            }
        });

    }

    @Override
    public final Loader<List<Map<String, String>>> onCreateLoader(final int arg0,
            final Bundle arg1) {
        Log.d(TAG, "Call onCreateLoader.");
        AsyncFetcher asyncFetcher = new AsyncFetcher(this);
        asyncFetcher.forceLoad();
        return asyncFetcher;
    }

    @Override
    public final void onLoadFinished(final Loader<List<Map<String, String>>> arg0,
            final List<Map<String, String>> itemList) {
        Log.d(TAG, "Call onLoadFinished.");
        for (Map<String, String> itemMap : itemList) {
            titles.add(itemMap.get(Constant.TITLE_FIELD));
            links.add(itemMap.get(Constant.LINK_FIELD));
        }

    }

    @Override
    public final void onLoaderReset(final Loader<List<Map<String, String>>> arg0) {
        Log.d(TAG, "Call onLoadReset.");
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        Log.d(TAG, "Call onCreateOptionsMenu.");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        Log.d(TAG, "Call onOptionsItemSelected.");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
