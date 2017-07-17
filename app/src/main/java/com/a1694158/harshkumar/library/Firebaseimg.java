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
    public Firebaseimg(Context c, GridView gv, DatabaseReference dbref, ProgressBar pw) {
        this.c = c;
        this.gv = gv;
        this.dbref = dbref;
        this.pw = pw;

    }

    public Firebaseimg(Context c, GridView gv, DatabaseReference dbref, ProgressBar pw,String key) {
        this.c = c;
        this.gv = gv;
        this.dbref = dbref;
        this.pw = pw;
        this.key = key;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        imgs.clear();
        if(key!=null&&!key.isEmpty()&&key!="")
        {
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

        /*dbref.addChildEventListener(new ChildEventListener() {
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
        });*/
    }

    public void normalData(DataSnapshot dataSnapshot)
    {
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            System.out.println("Datasnapshot Value without key  "+ ds.toString());
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
           // System.out.println("Datasnapshot Value with key  "+ ds.child("author_id").getValue().toString());

          //  System.out.println(ds.getValue().toString());
            String tit = ds.child("title").getValue().toString().toLowerCase();
            String pub = ds.child("publisher").getValue().toString().toLowerCase();
            GridItem grd;
            if(ds.child("author_id").getValue(Integer.class)==aut_key)
            {
            //    System.out.println("In Search    "+dataSnapshot.toString());
                grd  = ds.getValue(GridItem.class);
                imgs.add(grd);
            }else if (tit.contains(key))
            {

                System.out.println("Check Key Inside if      "+ds.getKey());
                grd  = ds.getValue(GridItem.class);
                imgs.add(grd);
            }else if (pub.contains(key))
            {
                System.out.println("Check Key Inside pub if      "+ds.getKey());
                grd  = ds.getValue(GridItem.class);
                imgs.add(grd);
            }

        }
        for (int i=0;i<imgs.size();i++)
        {
            System.out.println("Arraylist  "+imgs.get(i).getTitle());
        }
        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs,pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }



}
