package com.a1694158.harshkumar.library;

import android.content.Context;
import android.content.Intent;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Harsh on 7/3/2017.
 */

public class Firebaseimg  {

    private Context c;
    ArrayList<GridItem> imgs = new ArrayList<>();

    GridViewAdapter adapter;
    GridView gv;
    DatabaseReference dbref;
    ProgressBar pw;
    String key="";
    String cat = "";
    public Firebaseimg(Context c, GridView gv, DatabaseReference dbref, ProgressBar pw) {
        this.c = c;
        this.gv = gv;
        this.dbref = dbref;
        this.pw = pw;

    }

    public Firebaseimg(Context c, GridView gv, DatabaseReference dbref, ProgressBar pw, String key, String cat) {
        this.c = c;
        this.gv = gv;
        this.dbref = dbref;
        this.pw = pw;
        this.key = key;
        this.cat = cat;
    }


    private void fetchData(DataSnapshot dataSnapshot)
    {
        imgs.clear();

        if (cat != null && !cat.isEmpty() && cat != "")
        {
            //  System.out.println("Get Title Check on Click  "+cat);

            // byCategory(dataSnapshot,cat);
        } else if (key != null && !key.isEmpty() && key != "") {
            withKeyData(dataSnapshot,key);

        }else {

            normalData(dataSnapshot);
        }



    }


    public void regetData()
    {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void normalData(DataSnapshot dataSnapshot)
    {
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            GridItem grd  = ds.getValue(GridItem.class);
            imgs.add(grd);
        }

        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs,pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public void withKeyData(DataSnapshot dataSnapshot,String key)
    {

        int aut_key = 0;

            if(key.matches("[0-9]+"))
            {
                aut_key =  Integer.parseInt(key);
            }

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String tit = ds.child("title").getValue().toString().toLowerCase();
            String pub = ds.child("publisher").getValue().toString().toLowerCase();
            GridItem grd;
            if(ds.child("author_id").getValue(Integer.class)==aut_key)
            {
                grd  = ds.getValue(GridItem.class);
                imgs.add(grd);
            }else if (tit.contains(key))
            {
                grd  = ds.getValue(GridItem.class);
                imgs.add(grd);
            }else if (pub.contains(key))
            {
                grd  = ds.getValue(GridItem.class);
                imgs.add(grd);
            }

        }
        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs,pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

 /*   public void byCategory(DataSnapshot dataSnapshot,String cat)
    {
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String catls = ds.child("category").toString();
            GridItem grd;
          //  System.out.println("Get Child of the categoties "+catls);

            for(DataSnapshot ds2 : ds.child("category").getChildren())
            {
                //System.out.println("In iteration "+ds2.getValue().toString());

                if(ds2.getValue(String.class).contains(cat))
                {
                    grd  = ds.getValue(GridItem.class);
                    imgs.add(grd);
                    System.out.println("Only Contais Node  "+ds.toString());
                }
            }

        }
        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs,pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }*/



}
