package com.a1694158.harshkumar.library;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GridView mGridView;
    private ProgressBar mProgressBar;
    public ArrayList<String> gridItems;
    private GridViewAdapter mGridAdapter;
    Map<String,Object> map1;
    Firebaseimg frimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("books");

        frimg = new Firebaseimg(this,mGridView,myRef);

        frimg.regetData();



       /* gridItems = new ArrayList<>();
       myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());
                System.out.println("Inside ondatacahnged"+ gridItems.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    private void collectPhoneNumbers(Map<String,Object> books) {

        for (Map.Entry<String, Object> entry : books.entrySet()){
            Map singleUser = (Map) entry.getValue();
            gridItems.add((String) singleUser.get("cover"));
        }

    }

}
