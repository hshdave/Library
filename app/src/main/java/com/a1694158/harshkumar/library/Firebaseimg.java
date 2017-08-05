package com.a1694158.harshkumar.library;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

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

    GridItem grd;
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

        if (cat != null && !cat.isEmpty())
        {
            byCategory(dataSnapshot, cat);
        } else if (key != null && !key.isEmpty()) {
            withKeyLocation(dataSnapshot, key);

        } else {
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

    private void normalData(DataSnapshot dataSnapshot)
    {

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Boolean canada = ds.child("shipToCanada").getValue(Boolean.class);
            Boolean usa = ds.child("shipToUSA").getValue(Boolean.class);

            getData(ds, canada, usa);

        }

        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs,pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


    private void withKeyLocation(DataSnapshot dataSnapshot, String key) {
        String country = Locale.getDefault().getCountry();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            Boolean canada = ds.child("shipToCanada").getValue(Boolean.class);
            Boolean usa = ds.child("shipToUSA").getValue(Boolean.class);
            if (isCanada(country)) {
                if (canada.equals(true) || usa.equals(false)) {

                    System.out.println("Get Data from main Datasnap  " + ds.toString());

                    withKeyDataTest(ds, key);

                }
            } else if (isUSA(country)) {
                Log.d("Check Country", "You are in USA");
                if (usa.equals(true) || canada.equals(false)) {

                    withKeyDataTest(ds, key);
                }
            }

        }

    }


    private void withKeyDataTest(DataSnapshot ds, String key) {

        int aut_key = 0;

        if (key.matches("[0-9]+")) {
            aut_key = Integer.parseInt(key);
        }
        String tit = ds.child("title").getValue().toString().toLowerCase();
        String pub = ds.child("publisher").getValue().toString().toLowerCase();
        GridItem grd;


        if (ds.child("author_id").getValue(Integer.class) == aut_key) {
            System.out.println("in key data " + ds.toString());

            grd = ds.getValue(GridItem.class);
            imgs.add(grd);
        } else if (tit.contains(key)) {
            grd = ds.getValue(GridItem.class);
            imgs.add(grd);
        } else if (pub.contains(key)) {
            grd = ds.getValue(GridItem.class);
            imgs.add(grd);
        }

        if (imgs.size() > 0) {
            adapter = new GridViewAdapter(c, imgs, pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void byCategory(DataSnapshot dataSnapshot, String cat)
    {
        String country = Locale.getDefault().getCountry();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Boolean canada = ds.child("shipToCanada").getValue(Boolean.class);
            Boolean usa = ds.child("shipToUSA").getValue(Boolean.class);
            GridItem grd;
            for (DataSnapshot ds2 : ds.child("category").getChildren()) {
                if (isCanada(country)) {
                    if (canada.equals(true) || usa.equals(false)) {
                        Log.d("Category in Canada", ds2.getValue().toString());
                        if (ds2.getValue(String.class).contains(cat)) {
                            grd = ds.getValue(GridItem.class);
                            imgs.add(grd);
                        }
                    }
                } else if (isUSA(country)) {
                    Log.d("Check cat Country", "You are in USA");
                    if (usa.equals(true) || canada.equals(false)) {
                        Log.d("Category in USA", ds2.getValue().toString());
                        if (ds2.getValue(String.class).contains(cat)) {
                            grd = ds.getValue(GridItem.class);
                            imgs.add(grd);
                        }
                    }
                }
            }

        }
        if (imgs.size()>0)
        {
            adapter= new GridViewAdapter(c,imgs,pw);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private Boolean isCanada(String count) {
        if (count.equals("CA")) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isUSA(String count) {
        if (count.equals("US")) {
            return true;
        } else {
            return false;
        }
    }

    private void getData(DataSnapshot ds, Boolean can, Boolean us) {
        String country = Locale.getDefault().getCountry();
        if (isCanada(country)) {
            if (can.equals(true) || us.equals(false)) {
                Log.d("Check Country", "You are in CANADA");
                grd = ds.getValue(GridItem.class);
                imgs.add(grd);
            }

        } else if (isUSA(country)) {
            Log.d("Check Country", "You are in USA");
            if (us.equals(true) || can.equals(false)) {
                grd = ds.getValue(GridItem.class);
                imgs.add(grd);
            }
        } else {
            Toast.makeText(c, "No Books Available in your region", Toast.LENGTH_LONG).show();
        }

    }


}
