package com.a1694158.harshkumar.library;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Harsh on 7/3/2017.
 */

public class GridViewAdapter extends BaseAdapter {


    Context c;
    ArrayList<GridItem> images;
    LayoutInflater inflater;
    ProgressBar pw;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef1 = database.getReference();

    String ls;

    public GridViewAdapter(Context c, ArrayList<GridItem> images,ProgressBar pw) {
        this.c = c;
        this.images = images;
        this.pw = pw;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.grid_item,parent,false);
        }

      //  System.out.println("Iharsh"+images.get(position).getCover());
        final ImageView img = (ImageView) convertView.findViewById(R.id.grid_item_image);

        String country = Locale.getDefault().getCountry();

        if (country.equals("CA"))
        {
            Log.d("Country","You are in canada");
        }
        else if(country.equals("US")){
            Log.d("Country","You are in USA");

            if (images.get(position).isShipToUSA())
            {
                Picassaimage.getImages(c,images.get(position).getCover(),img,pw);
            }
        }

               Picassaimage.getImages(c,images.get(position).getCover(),img,pw);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef1.child("books").orderByChild("title").equalTo(images.get(position).getTitle()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef1 = database.getReference();
                        ls = myRef1.child("books").child(dataSnapshot.getKey()).toString();

                      //  Toast.makeText(c,ls,Toast.LENGTH_LONG).show();

                        Intent i = new Intent(c,Details.class);
                        i.putExtra("key",ls);
                        c.startActivity(i);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    return convertView;
    }

}
