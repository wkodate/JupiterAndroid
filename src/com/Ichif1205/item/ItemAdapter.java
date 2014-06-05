package com.Ichif1205.item;

import java.util.List;

import com.Ichif1205.jupiter.R;
import com.Ichif1205.jupiter.R.id;
import com.Ichif1205.jupiter.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ItemAdapter.
 *
 * @author wkodate
 *
 */
public class ItemAdapter extends ArrayAdapter<ItemData> {

    /**
     *
     */
    private final LayoutInflater layoutInflater;

    /**
     * @param context
     *            コンテキスト.
     * @param textViewResourceId
     *            TextViewのID.
     * @param objects
     *            ListViewに表示するオブジェクト.
     */
    public ItemAdapter(final Context context, final int textViewResourceId,
            final List<ItemData> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public final View getView(final int position, final View convertView,
            final ViewGroup parent) {
        // この位置のアイテムを取得
        ItemData itemData = getItem(position);
        View view = convertView;
        if (null == convertView) {
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        }

        // ItemDataをViewの各widgetにセットする
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(itemData.getTitle());
        //TextView rssUrlView = (TextView) view.findViewById(R.id.rss_url);
        //rssUrlView.setText(itemData.getRssUrl());
        TextView dateView = (TextView) view.findViewById(R.id.date);
        dateView.setText(itemData.getDate());

        return view;
    }

}