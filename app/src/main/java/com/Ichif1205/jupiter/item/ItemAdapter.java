package com.Ichif1205.jupiter.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ichif1205.jupiter.R;

import java.util.List;

/**
 * ItemAdapter.
 *
 * @author wkodate
 */
public class ItemAdapter extends ArrayAdapter<RssItem> {

    /**
     * ログ.
     */
    private static final String TAG = "ItemAdapter";

    /**
     * LayoutInflater.
     */
    private final LayoutInflater layoutInflater;

    /**
     * @param context            コンテキスト.
     * @param textViewResourceId TextViewのID.
     * @param objects            ListViewに表示するオブジェクト.
     */
    public ItemAdapter(final Context context, final int textViewResourceId,
                       final List<RssItem> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public final View getView(final int position, final View convertView,
                              final ViewGroup parent) {
        RssItem rssItem = getItem(position);
        View view = convertView;
        if (getItemViewType(position) == 0) {
            if (null == view) {
                view = layoutInflater.inflate(R.layout.item_layout_image,
                        parent, false);
            }
            TextView titleView = (TextView) view.findViewById(R.id.title);
            titleView.setText(rssItem.getTitle());
            TextView rssTitleView = (TextView) view.findViewById(R.id.rssTitle);
            rssTitleView.setText(rssItem.getRssTitle());
            TextView dateView = (TextView) view.findViewById(R.id.date);
            dateView.setText(rssItem.getDate());
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageBitmap(rssItem.getImage());
        } else {
            if (null == view) {
                view = layoutInflater.inflate(R.layout.item_layout, parent,
                        false);
            }
            TextView titleView = (TextView) view.findViewById(R.id.title);
            titleView.setText(rssItem.getTitle());
            TextView rssTitleView = (TextView) view.findViewById(R.id.rssTitle);
            rssTitleView.setText(rssItem.getRssTitle());
            TextView dateView = (TextView) view.findViewById(R.id.date);
            dateView.setText(rssItem.getDate());
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        RssItem item = getItem(position);
        if (item.getImage() != null) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

}