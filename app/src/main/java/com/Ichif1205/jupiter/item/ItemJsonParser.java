package com.Ichif1205.jupiter.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.Ichif1205.jupiter.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ItemJsonParser
 *
 * Created by wkodate on 2015/04/25.
 */
public class ItemJsonParser {

    private static final String TAG = "ItemJsonParser";

    private static final int IN_SAMPLE_SIZE = 2;

    private static final Bitmap.Config IN_PREFERRED_CONFIG =  Bitmap.Config.RGB_565;

    private String rssJson;

    private BitmapFactory.Options bitmapOptions;

    public ItemJsonParser(String json) {
        this.rssJson = json;
        this.bitmapOptions = createBitmapOptionsInstance();
    }

    private BitmapFactory.Options createBitmapOptionsInstance() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = IN_PREFERRED_CONFIG;
        options.inSampleSize = IN_SAMPLE_SIZE;
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * JSON文字列から各Itemの詳細情報を取得.
     *
     * @return itemList
     */
    public List<RssItem> parse() {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(rssJson);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        List<RssItem> rssItemList = new ArrayList<>();
        for (int i = 0; i < Constant.ITEM_VIEW_COUNT; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                rssItemList.add(extractItemFields(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rssItemList;
    }

    private RssItem extractItemFields(JSONObject json) throws JSONException {
        RssItem rssItem = new RssItem();

        rssItem.link = json.getString(Constant.LINK_FIELD);
        rssItem.title = json.getString(Constant.TITLE_FIELD);
        rssItem.rssTitle = json.getString(Constant.RSS_TITLE_FIELD);
        rssItem.date = json.getString(Constant.DATE_FIELD);
        //rssItem.description(json.getString(Constant.DESC_FIELD));
        rssItem.image = convertUrlToBitmap(json.getString(Constant.IMAGE_FIELD));

        return rssItem;
    }

    /**
     * URLをBitmapに変換.
     *
     * @param urlStr URL文字列.
     * @return Bitmap.
     */
    private Bitmap convertUrlToBitmap(final String urlStr) {
        InputStream is = null;
        try {
            is = new URL(urlStr).openStream();
            return BitmapFactory.decodeStream(is, null, bitmapOptions);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
