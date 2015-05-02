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
    public List<ItemData> parse() {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        List<ItemData> itemDataList = new ArrayList<>();
        for (int i = 0; i < Constant.ITEM_VIEW_COUNT; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                itemDataList.add(extractItemFields(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return itemDataList;
    }

    private ItemData extractItemFields(JSONObject json) throws JSONException {
        ItemData itemData = new ItemData();

        itemData.setLink(json.getString(Constant.LINK_FIELD));
        itemData.setTitle(json.getString(Constant.TITLE_FIELD));
        itemData.setRssTitle(json.getString(Constant.RSS_TITLE_FIELD));
        itemData.setDate(json.getString(Constant.DATE_FIELD));
        itemData.setDescription(json.getString(Constant.DESC_FIELD));
        itemData.setImage(convertUrlToBitmap(json.getString(Constant.IMAGE_FIELD)));

        return itemData;
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
