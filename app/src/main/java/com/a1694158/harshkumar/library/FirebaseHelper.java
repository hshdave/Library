package com.a1694158.harshkumar.library;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Harsh on 7/4/2017.
 */

public class FirebaseHelper {
    DatabaseReference  db;
    ArrayList<GridItem> gridItems = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public void fetchdata(DataSnapshot dataSnapshot)
    {
        gridItems.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            System.out.println("Check me       "+ds.getValue());
            GridItem grd = ds.getValue(GridItem.class);
            gridItems.add(grd);
        }
    }

    public ArrayList<GridItem> retrive()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchdata(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchdata(dataSnapshot);
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
        return gridItems;
    }

}
