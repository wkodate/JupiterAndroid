package com.Ichif1205.jupiter.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    private String json;

    public ItemJsonParser(String json) {
        this.json = json;
    }

    /**
     * JSON文字列から各Itemの詳細情報を取得.
     *
     * @return itemList
     */
    public List<RssItem> parse() {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(json);
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

        rssItem.setLink(json.getString(Constant.LINK_FIELD));
        rssItem.setTitle(json.getString(Constant.TITLE_FIELD));
        rssItem.setRssTitle(json.getString(Constant.RSS_TITLE_FIELD));
        rssItem.setDate(json.getString(Constant.DATE_FIELD));
        //rssItem.setDescription(json.getString(Constant.DESC_FIELD));
        rssItem.setImage(convertUrlToBitmap(json.getString(Constant.IMAGE_FIELD)));

        return rssItem;
    }

    /**
     * URLをBitmapに変換.
     *
     * @param urlStr URL文字列.
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
