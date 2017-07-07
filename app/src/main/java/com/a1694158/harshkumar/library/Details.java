package com.a1694158.harshkumar.library;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    TextView txt_title,txt_publisher,txt_date,txt_quantity;
    ImageView img_cover;


    FirebaseDatabase fb = FirebaseDatabase.getInstance();
    DatabaseReference myref;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_publisher = (TextView) findViewById(R.id.txt_publisher);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_quantity = (TextView) findViewById(R.id.txt_quantity);
        img_cover = (ImageView) findViewById(R.id.img_details);


        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("key");

        myref = fb.getReferenceFromUrl(key);


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int qty = dataSnapshot.child("quantity").getValue(Integer.class);

                txt_title.setText(dataSnapshot.child("title").getValue(String.class));
                txt_publisher.setText(dataSnapshot.child("publisher").getValue(String.class));
                txt_date.setText(dataSnapshot.child("date").getValue(String.class));
                txt_quantity.setText(""+qty);

                Picassaimage.getImages(Details.this,dataSnapshot.child("cover").getValue(String.class),img_cover);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
