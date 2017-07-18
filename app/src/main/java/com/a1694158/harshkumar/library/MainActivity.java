package com.a1694158.harshkumar.library;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends ActionBarActivity {


    private GridView mGridView;
    Firebaseimg frimg;
    DatabaseReference db;
    ProgressBar pw;

    EditText edt_search;
    Button btn_search;
    Spinner spin_search;
    ImageView img_close;
    FirebaseDatabase dbme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridView);
        pw = (ProgressBar) findViewById(R.id.prog_me);

        dbme = FirebaseDatabase.getInstance();

        db = dbme.getReference("books");
        frimg = new Firebaseimg(MainActivity.this, mGridView,db,pw);
        frimg.regetData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_search:
                displayDialog();
                return true;
            case R.id.menu_All_books:
                frimg = new Firebaseimg(MainActivity.this, mGridView,db,pw);
                frimg.regetData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void displayDialog()
    {
        final Dialog d = new Dialog(this,R.style.CustomDialog);
    //    d.setTitle("Search Books");
        d.setContentView(R.layout.searh_dialog);


        edt_search = (EditText) d.findViewById(R.id.dig_edtsearch);
        btn_search = (Button) d.findViewById(R.id.dig_btn);
        spin_search = (Spinner) d.findViewById(R.id.dig_spin);
        img_close = (ImageView) d.findViewById(R.id.ser_close);


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.searchCategory, android.R.layout.simple_spinner_dropdown_item);
        spin_search.setAdapter(adapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selected = spin_search.getSelectedItem().toString();
                final String user = edt_search.getText().toString().toLowerCase();
                frimg.regetData();
                if (selected.equals("Author"))
                {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dbsch =  database.getReference("authors");

                    dbsch.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           for(DataSnapshot ds : dataSnapshot.getChildren())
                           {
                               if(ds.child("name").getValue().toString().toLowerCase().contains(user.toLowerCase()))
                               {
                                  //  System.out.println("Checking if in search "+ds.child("name").getValue().toString()+"  "+"key..    "+ds.getKey());
                                   String key = ds.getKey();
                                   System.out.println("Check Key  in Main     "+key);
                                   dbme = FirebaseDatabase.getInstance();
                                   db = dbme.getReference("books");
                                   frimg = new Firebaseimg(MainActivity.this, mGridView,db,pw,key);
                                   frimg.regetData();

                               }
                           }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else if(selected.equals("Book Name")) {
                        getSearch(user);
                }
                else if(selected.equals("Publisher"))
                {
                    getSearch(user);
                }
                else if (selected.equals("Search By"))
                {
                    Toast.makeText(getApplicationContext(),"Please Select Search Category!",Toast.LENGTH_LONG).show();
                }
                d.dismiss();
            }
        });

        d.show();
    }
    public void getSearch(String userInput)
    {
        dbme = FirebaseDatabase.getInstance();
        db = dbme.getReference("books");
        frimg = new Firebaseimg(MainActivity.this, mGridView,db,pw,userInput);
        frimg.regetData();
    }
}
