package com.Ichif1205.jupiter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * AsyncFetcher.
 *
 * @author wkodate
 *
 */
public class AsyncFetcher extends AsyncTaskLoader<String> {

    /**
     * ログ.
     */
    private static final String TAG = "AsyncFetcher";

    /**
     * API取得サーバのURL.
     */
    private static final String API_URL = "http://www6178uo.sakura.ne.jp/jupiter/db2json/db2json.php";

    /**
     * コネクションタイムアウト時間.
     */
    private static final int CONNECTION_TIMEOUT = 10000;

    /**
     * 取得タイムアウト時間.
     */
    private static final int SO_TIMEOUT = 10000;

    /**
     * HttpClient.
     */
    private final HttpClient httpClient;

    /**
     * HttpResponse.
     */
    private HttpResponse httpResponse;

    /**
     * コンストラクタ.
     *
     * @param context
     *            コンテキスト.
     */
    public AsyncFetcher(final Context context) {
        super(context);
        this.httpClient = new DefaultHttpClient();
    }

    @Override
    public final String loadInBackground() {

        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), SO_TIMEOUT);

        // Json取得
        HttpGet httpGet = new HttpGet(API_URL);
        try {
            httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                InputStream inStream = httpResponse.getEntity().getContent();
                InputStreamReader inStreamReader = new InputStreamReader(inStream);
                BufferedReader bReader = new BufferedReader(inStreamReader);
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = bReader.readLine()) != null) {
                    json.append(line);
                }
                String jsonString = json.toString();
                inStream.close();
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Log.d(TAG, jsonObj.getString("link"));
                    Log.d(TAG, jsonObj.getString("title"));
                }
                return jsonString;
            } else {
                Log.d(TAG, Integer.toString(statusCode));
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}