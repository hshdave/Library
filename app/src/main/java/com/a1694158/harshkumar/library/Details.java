package com.a1694158.harshkumar.library;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Details extends AppCompatActivity {

    TextView txt_title,txt_publisher,txt_date,txt_quantity,txt_author;
    ImageView img_cover;


    FirebaseDatabase fb = FirebaseDatabase.getInstance();
    DatabaseReference myref;
    Button btn_rent, btn_test;
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
        txt_author = (TextView) findViewById(R.id.txt_author);
        btn_rent = (Button) findViewById(R.id.btn_rent);
        btn_test = (Button) findViewById(R.id.test);


        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("key");

        myref = fb.getReferenceFromUrl(key);


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference myref;

                int qty = dataSnapshot.child("quantity").getValue(Integer.class);
                final int author = dataSnapshot.child("author_id").getValue(Integer.class);

                txt_title.setText(dataSnapshot.child("title").getValue(String.class));
                txt_publisher.setText(dataSnapshot.child("publisher").getValue(String.class));
                txt_date.setText(dataSnapshot.child("date").getValue(String.class));


                if (qty<=0)
                {
                    txt_quantity.setTextColor(getResources().getColor(R.color.red));
                    txt_quantity.setText(""+qty);
                    btn_rent.setClickable(false);
                }else
                {
                    txt_quantity.setText(""+qty);
                    btn_rent.setClickable(true);

                    btn_rent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"Thank you for rent book!",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                myref = fb.getReference("authors");
                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String aut = dataSnapshot.child(String.valueOf(author)).child("name").getValue(String.class);
                        txt_author.setText(aut);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Picassaimage.getwithImages(Details.this,dataSnapshot.child("cover").getValue(String.class),img_cover);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
