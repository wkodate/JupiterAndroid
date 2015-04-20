package com.Ichif1205.jupiter.item;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ichif1205.jupiter.R;

/**
 * ItemAdapter.
 *
 * @author wkodate
 *
 */
public class ItemAdapter extends ArrayAdapter<ItemData> {

    /**
     * ログ.
     */
    private static final String TAG = "ItemAdapter";

    /**
     * LayoutInflater.
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
            if (itemData.getImage() != null && !"".equals(itemData.getImage())) {
                // 画像があるとき
                view = layoutInflater.inflate(R.layout.item_layout_image,
                        parent, false);
            } else {
                // 画像がないとき
                view = layoutInflater.inflate(R.layout.item_layout, parent,
                        false);
            }
        }
        if (itemData.getImage() != null && !"".equals(itemData.getImage())) {
            TextView titleView = (TextView) view.findViewById(R.id.title);
            titleView.setText(itemData.getTitle());
            TextView rssTitleView = (TextView) view.findViewById(R.id.rssTitle);
            rssTitleView.setText(itemData.getRssTitle());
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageBitmap(itemData.getImage());
            imageView.setImageBitmap(itemData.getImage());
        } else {
            // ItemDataをViewの各widgetにセットする
            TextView titleView = (TextView) view.findViewById(R.id.title);
            titleView.setText(itemData.getTitle());
            TextView rssTitleView = (TextView) view.findViewById(R.id.rssTitle);
            rssTitleView.setText(itemData.getRssTitle());
            TextView dateView = (TextView) view.findViewById(R.id.date);
            dateView.setText(itemData.getDate());
        }
        return view;
    }

}