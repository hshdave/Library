package com.a1694158.harshkumar.library;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Harsh on 7/3/2017.
 */

public class GridViewAdapter extends BaseAdapter {


    Context c;
    ArrayList<GridItem> images;
    LayoutInflater inflater;
    public GridViewAdapter(Context c, ArrayList<GridItem> images) {
        this.c = c;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.grid_item,parent,false);
        }

        System.out.println("Iharsh"+images.get(position).getCover());
        ImageView img = (ImageView) convertView.findViewById(R.id.grid_item_image);
        Picassaimage.getImages(c,images.get(position).getCover(),img);
        System.out.println("Titles......."+images.get(position).getTitle());


        return convertView;
    }
}
