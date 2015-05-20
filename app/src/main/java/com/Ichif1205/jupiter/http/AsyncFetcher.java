package com.Ichif1205.jupiter.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.Ichif1205.jupiter.Secret;
import com.Ichif1205.jupiter.item.ItemJsonParser;
import com.Ichif1205.jupiter.item.RssItem;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * AsyncFetcher.
 *
 * @author wkodate
 */
public class AsyncFetcher extends AsyncTaskLoader<List<RssItem>> {

    /**
     * ログ.
     */
    private static final String TAG = "AsyncFetcher";

    /**
     * コネクションタイムアウト時間.
     */
    private static final int CONNECTION_TIMEOUT = 10000;

    /**
     * 取得タイムアウト時間.
     */
    private static final int SO_TIMEOUT = 10000;

    private final HttpClient httpClient;

    private HttpResponse httpResponse;

    /**
     * コンストラクタ.
     *
     * @param context コンテキスト.
     */
    public AsyncFetcher(final Context context) {
        super(context);
        Log.d(TAG, "Call Constructor.");
        this.httpClient = new DefaultHttpClient();
    }

    @Override
    public final List<RssItem> loadInBackground() {
        // バックグラウンドで実行する処理
        Log.d(TAG, "Call loadInBackground.");

        httpResponse = fetch();
        if (isInvalidResponse()) {
            return null;
        }
        ItemJsonParser parser = new ItemJsonParser(getContentJson());
        return parser.parse();
    }

    private HttpResponse fetch() {
        try {
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), SO_TIMEOUT);
            HttpGet httpGet = new HttpGet(Secret.API_URL);
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isInvalidResponse() {
        if (httpResponse == null) {
            return true;
        }
        int status = httpResponse.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK != status) {
            Log.d(TAG, "Invalid status code: " + Integer.toString(status));
            return true;
        }
        return false;
    }

    /**
     * HTTPレスポンスからコンテンツを取得.
     *
     * @return コンテンツ.
     */
    private String getContentJson() {
        Log.d(TAG, "Call getContent.");
        try {
            InputStream inStream = httpResponse.getEntity().getContent();
            InputStreamReader inStreamReader = new InputStreamReader(inStream);
            BufferedReader bReader = new BufferedReader(inStreamReader);
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
                json.append(line);
            }
            inStream.close();
            return json.toString();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}