package com.Ichif1205.jupiter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.Ichif1205.jupiter.Constant;
import com.Ichif1205.jupiter.item.ItemData;

/**
 * AsyncFetcher.
 *
 * @author wkodate
 *
 */
public class AsyncFetcher extends AsyncTaskLoader<List<ItemData>> {

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
        Log.d(TAG, "Call Constructor.");
        this.httpClient = new DefaultHttpClient();
    }

    @Override
    public final List<ItemData> loadInBackground() {
        // バックグラウンドで実行する処理
        Log.d(TAG, "Call loadInBackground.");

        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), SO_TIMEOUT);
        HttpGet httpGet = new HttpGet(Constant.API_URL);
        try {
            httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                String jsonString = getContent(httpResponse);
                List<ItemData> itemDataList = getItemInfo(jsonString);
                return itemDataList;
            } else {
                Log.d(TAG, Integer.toString(statusCode));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTPレスポンスからコンテンツを取得.
     *
     * @param httpRes
     *            HTTPレスポンス.
     * @return コンテンツ.
     */
    private String getContent(final HttpResponse httpRes) {
        Log.d(TAG, "Call getContent.");
        try {
            InputStream inStream = httpRes.getEntity().getContent();
            InputStreamReader inStreamReader = new InputStreamReader(inStream);
            BufferedReader bReader = new BufferedReader(inStreamReader);
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
                json.append(line);
            }
            inStream.close();
            return json.toString();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON文字列から各Itemの詳細情報を取得.
     *
     * @param jsonStr
     *            JSON文字列.
     * @return
     * @return itemList
     */
    private List<ItemData> getItemInfo(final String jsonStr) {
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            List<ItemData> itemDataList = new ArrayList<ItemData>();
            for (int i = 0; i < Constant.ITEM_VIEW_COUNT; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ItemData itemData = new ItemData();
                itemData.setLink(jsonObject.getString(Constant.LINK_FIELD));
                itemData.setTitle(jsonObject.getString(Constant.TITLE_FIELD));
                itemData.setRssUrl(jsonObject.getString(Constant.RSS_LINK_FIELD));
                itemData.setDescription(jsonObject.getString(Constant.DESC_FIELD));
                //itemData.setImage(convertUrlToBitmap(jsonObject.getString(Constant.IMAGE_FIELD)));
                itemDataList.add(itemData);
            }
            return itemDataList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * URLをBitmapに変換.
     *
     * @param urlStr
     *            URL文字列.
     * @return Bitmap.
     */
    private Bitmap convertUrlToBitmap(final String urlStr) {
        try {
            InputStream is = new URL(urlStr).openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}