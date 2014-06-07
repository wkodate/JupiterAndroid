package com.Ichif1205.jupiter;

import java.util.ArrayList;
import java.util.List;

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

import com.Ichif1205.jupiter.http.AsyncFetcher;
import com.Ichif1205.jupiter.item.ItemAdapter;
import com.Ichif1205.jupiter.item.ItemData;

/**
 * MainActivity.
 *
 * @author wkodate
 *
 */
public class MainActivity extends FragmentActivity implements
        LoaderCallbacks<List<ItemData>> {

    /**
     * ログ.
     */
    private static final String TAG = "MainActivity";

    /**
     * intentで渡すURLの配列.
     */
    private String[] urls;

    /**
     * コンストラクタ.
     */
    public MainActivity() {
        Log.d(TAG, "Call Constructor.");
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Call onCreate.");
        setTitle(this.getClass().getSimpleName());
        // loaderの初期化
        getSupportLoaderManager().initLoader(0, null, this);
        // プログレスバー
        setContentView(R.layout.listview_progress_bar);

    }

    @Override
    public final Loader<List<ItemData>> onCreateLoader(final int arg0,
            final Bundle arg1) {
        // 新しいLoaderが作成された時に呼ばれる
        Log.d(TAG, "Call onCreateLoader.");
        AsyncFetcher asyncFetcher = new AsyncFetcher(this);
        asyncFetcher.forceLoad();
        return asyncFetcher;
    }

    @Override
    public final void onLoadFinished(final Loader<List<ItemData>> arg0,
            final List<ItemData> itemDataList) {
        // 前に作成したloaderがloadを完了した時に呼ばれる
        Log.d(TAG, "Call onLoadFinished.");
        // データ作成
        List<ItemData> items = new ArrayList<ItemData>();
        List<String> links = new ArrayList<String>();
        for (int i = 0; i < itemDataList.size(); i++) {
            links.add(itemDataList.get(i).getLink());
        }
        urls = links.toArray(new String[links.size()]);
        setContentView(R.layout.activity_main);

        // リストビューに入れるアイテムのAdapterを生成
        ItemAdapter adapter = new ItemAdapter(this, 0, itemDataList);

        // Adapterを指定
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // クリックされた時の処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent,
                    final View view,
                    final int position, final long id) {
                Log.d(TAG, "Call onItemClick.");
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", urls[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    public final void onLoaderReset(final Loader<List<ItemData>> arg0) {
        // 前に作成したloaderがリセットされた時に呼ばれる
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
