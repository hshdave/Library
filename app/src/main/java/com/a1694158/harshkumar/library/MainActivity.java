package com.a1694158.harshkumar.library;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.GridView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity extends ActionBarActivity {


    private GridView mGridView;
    Firebaseimg frimg;
    DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridView);

        db = FirebaseDatabase.getInstance().getReference();
        frimg = new Firebaseimg(this,mGridView,db);
        frimg.regetData();


    }

}
