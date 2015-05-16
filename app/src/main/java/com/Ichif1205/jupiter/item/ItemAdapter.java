package com.Ichif1205.jupiter.item;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ichif1205.jupiter.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * ItemAdapter.
 *
 * @author wkodate
 */
public class ItemAdapter extends ArrayAdapter<RssItem> {

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
            ViewHolderWithImage holder;
            if (null == view) {
                view = layoutInflater.inflate(R.layout.item_layout_image,
                        parent, false);
                holder = new ViewHolderWithImage(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolderWithImage) view.getTag();
            }
            holder.titleTextView.setText(rssItem.title);
            holder.rssTitleTextView.setText(rssItem.rssTitle);
            holder.dateTextView.setText(rssItem.date);
            Glide.with(getContext())
                    .load(rssItem.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.loading_image)
                    .crossFade()
                    .into(holder.imageView);
        } else {
            ViewHolderWithoutImage holder;
            if (null == view) {
                view = layoutInflater.inflate(R.layout.item_layout, parent,
                        false);
                holder = new ViewHolderWithoutImage(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolderWithoutImage) view.getTag();
            }
            holder.titleTextView.setText(rssItem.title);
            holder.rssTitleTextView.setText(rssItem.rssTitle);
            holder.dateTextView.setText(rssItem.date);
        }
        return view;
    }

    private static class ViewHolderWithImage {
        TextView titleTextView;
        TextView rssTitleTextView;
        TextView dateTextView;
        ImageView imageView;

        public ViewHolderWithImage(View view) {
            this.titleTextView = (TextView) view.findViewById(R.id.title);
            this.rssTitleTextView = (TextView) view.findViewById(R.id.rssTitle);
            this.dateTextView = (TextView) view.findViewById(R.id.date);
            this.imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    private static class ViewHolderWithoutImage {
        TextView titleTextView;
        TextView rssTitleTextView;
        TextView dateTextView;

        public ViewHolderWithoutImage(View view) {
            this.titleTextView = (TextView) view.findViewById(R.id.title);
            this.rssTitleTextView = (TextView) view.findViewById(R.id.rssTitle);
            this.dateTextView = (TextView) view.findViewById(R.id.date);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(getItem(position).imageUrl)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

}