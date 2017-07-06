package com.a1694158.harshkumar.library;

import android.content.Context;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Harsh on 7/3/2017.
 */

public class Firebaseimg  {

    Context c;
    ArrayList<GridItem> imgs = new ArrayList<>();

    GridViewAdapter adapter;
    GridView gv;
    DatabaseReference dbref;

    public Firebaseimg(Context c, GridView gv, DatabaseReference dbref) {
        this.c = c;
        this.gv = gv;
        this.dbref = dbref;

    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        imgs.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {

            GridItem grd = new GridItem();
            grd.setCover(ds.getValue(GridItem.class).getCover());
            imgs.add(grd);

        }

        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs);
            gv.setAdapter(adapter);
        }
    }


    public void regetData()
    {
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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



}
