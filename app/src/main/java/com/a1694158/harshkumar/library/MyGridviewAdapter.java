package com.a1694158.harshkumar.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Harsh on 7/4/2017.
 */

public class MyGridviewAdapter extends BaseAdapter {

    ArrayList<GridItem> gridItems;
    Context c;

    public MyGridviewAdapter(ArrayList<GridItem> gridItems, Context c) {
        this.gridItems = gridItems;
        this.c = c;
    }


    @Override
    public int getCount() {
        return gridItems.size();
    }

    @Override
    public Object getItem(int position) {
        return gridItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.grid_item,parent,false);
        }

        ImageView img_v = (ImageView) convertView.findViewById(R.id.grid_item_image);


        final GridItem gcov = (GridItem) this.getItem(position);

        System.out.println("All About Covers  "+gcov.getCover());

        Picassaimage.getImages(c,gcov.getCover(),img_v);

        return convertView;
    }
}
