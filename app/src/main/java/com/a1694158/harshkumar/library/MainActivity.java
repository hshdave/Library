package com.a1694158.harshkumar.library;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;


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

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();


        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);


        mGridView = (GridView) findViewById(R.id.gridView);
        pw = (ProgressBar) findViewById(R.id.prog_me);

        dbme = FirebaseDatabase.getInstance();

        db = dbme.getReference("books");
        frimg = new Firebaseimg(MainActivity.this, mGridView,db,pw);
        frimg.regetData();

        addMenuItemInNavMenuDrawer();

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
            case R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
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
                                   frimg = new Firebaseimg(MainActivity.this, mGridView, db, pw, key, "");
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
        frimg = new Firebaseimg(MainActivity.this, mGridView, db, pw, userInput, "");
        frimg.regetData();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(final MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        FirebaseDatabase dbs = FirebaseDatabase.getInstance();
        DatabaseReference dr = dbs.getReference("category");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("Category in selection " + ds.getValue(String.class));

                    if (menuItem.getTitle().equals(ds.getValue(String.class))) {
                        Toast.makeText(getApplicationContext(), "Selected on  " + ds.getValue(String.class), Toast.LENGTH_LONG).show();
                        dbme = FirebaseDatabase.getInstance();
                        db = dbme.getReference("books");
                        frimg = new Firebaseimg(MainActivity.this, mGridView, db, pw, "", menuItem.getTitle().toString());
                        frimg.regetData();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try {
            // fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        // FragmentManager fragmentManager = getSupportFragmentManager();
        // fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void addMenuItemInNavMenuDrawer() {
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        final Menu menu = nvDrawer.getMenu();
        //final Menu cat = menu.addSubMenu("Categories");


        FirebaseDatabase dbs = FirebaseDatabase.getInstance();
        DatabaseReference dr = dbs.getReference("category");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("Check All category   " + ds.getValue(String.class));
                    menu.add(ds.getValue(String.class));
                    nvDrawer.invalidate();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
