package com.Ichif1205.jupiter.item;

import android.text.TextUtils;

import com.Ichif1205.jupiter.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private String rssJson;

    public ItemJsonParser(String json) {
        this.rssJson = json;
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
        String imageUrl = json.getString(Constant.IMAGE_FIELD);
        if (!TextUtils.isEmpty(imageUrl)) {
            rssItem.imageUrl = imageUrl;
        }

        return rssItem;
    }

}
