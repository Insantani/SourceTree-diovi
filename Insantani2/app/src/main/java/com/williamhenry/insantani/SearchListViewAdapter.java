package com.williamhenry.insantani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchListViewAdapter extends ArrayAdapter<Product> {
    public SearchListViewAdapter(Context context, List<Product> newsList) {
        super(context, 0, newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.search_listview_layout, null);
        TextView titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        TextView contentTextView = (TextView) view.findViewById(R.id.content_text_view);
        ImageView picture = (ImageView) view.findViewById(R.id.icon);

        titleTextView.setText(getItem(position).getName());
        contentTextView.setText(getItem(position).getFarmerName());
        picture.setImageBitmap(getItem(position).getThumbnail());

        return view;
    }
}