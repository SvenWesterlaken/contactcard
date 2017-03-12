package com.svenwesterlaken.contactcardapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sven Westerlaken on 10-3-2017.
 */

public class RandomUserAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<RandomUserItem> users;

    public RandomUserAdapter(Context context, LayoutInflater layoutInflater, ArrayList<RandomUserItem> users) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.contact_list, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.userThumbnail);
            viewHolder.fullName = (TextView) convertView.findViewById(R.id.NameTV);

            // Koppel de view aan de viewHolder
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RandomUserItem user = users.get(position);

        viewHolder.fullName.setText(user.getName());
        Picasso.with(context).load(user.getImage()).into(viewHolder.imageView);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView fullName;
    }
}
