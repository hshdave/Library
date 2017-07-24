package com.a1694158.harshkumar.library;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
    Button btn_search, btn_close;
    Spinner spin_search;
    FirebaseDatabase dbme;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (isNetworkAvailable()) {


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
            getData("", "");

            addMenuItemInNavMenuDrawer();
        } else {
            Snackbar bar = Snackbar.make(findViewById(R.id.drawer_layout), "No Internet Connection!", Snackbar.LENGTH_INDEFINITE);
            bar.setAction("Setting", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
                }
            });
            bar.show();
        }
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
        d.setTitle("Search Book");
        d.setContentView(R.layout.search_dialog);
        d.getWindow().setTitleColor(Color.parseColor("#0097A7"));
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        edt_search = (EditText) d.findViewById(R.id.dig_edtsearch);
        btn_search = (Button) d.findViewById(R.id.dig_btn);
        spin_search = (Spinner) d.findViewById(R.id.dig_spin);
        btn_close = (Button) d.findViewById(R.id.dig_cancel);


        btn_close.setOnClickListener(new View.OnClickListener() {
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
                    d.dismiss();

                    dbsch.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           for(DataSnapshot ds : dataSnapshot.getChildren())
                           {
                               if(ds.child("name").getValue().toString().toLowerCase().contains(user.toLowerCase()))
                               {
                                  //  System.out.println("Checking if in search "+ds.child("name").getValue().toString()+"  "+"key..    "+ds.getKey());
                                   String key = ds.getKey();
                                   Log.d("Check Key  in Main", key);
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
                    d.dismiss();
                }
                else if(selected.equals("Publisher"))
                {
                    getSearch(user);
                    d.dismiss();
                }
                else if (selected.equals("Search By"))
                {
                    Toast.makeText(getApplicationContext(),"Please Select Search Category!",Toast.LENGTH_LONG).show();
                    edt_search.setText("");
                }

            }
        });

        d.show();
    }
    public void getSearch(String userInput)
    {
        getData(userInput, "");
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

        FirebaseDatabase dbs = FirebaseDatabase.getInstance();
        DatabaseReference dr = dbs.getReference("category");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("Category in selection " + ds.getValue(String.class));

                    if (menuItem.getTitle().equals(ds.getValue(String.class))) {

                        Log.d("Selected on Drawer : ", ds.getValue(String.class));

                        getData("", menuItem.getTitle().toString());

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        if (isNetworkAvailable()) {
            drawerToggle.syncState();
        }

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

        FirebaseDatabase dbs = FirebaseDatabase.getInstance();
        DatabaseReference dr = dbs.getReference("category");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    menu.add(ds.getValue(String.class));
                    nvDrawer.invalidate();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getData(String key, String cat) {
        dbme = FirebaseDatabase.getInstance();
        db = dbme.getReference("books");
        frimg = new Firebaseimg(MainActivity.this, mGridView, db, pw, key, cat);
        frimg.regetData();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onRestart() {
        startActivity(getIntent());
        super.onRestart();
    }
}
