package uk.ac.aston.smalljh.wego;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import uk.ac.aston.smalljh.wego.fragments.AddPlaceFragment;
import uk.ac.aston.smalljh.wego.fragments.AddTripFragment;
import uk.ac.aston.smalljh.wego.fragments.GalleryFragment;
import uk.ac.aston.smalljh.wego.fragments.HomeFragment;
import uk.ac.aston.smalljh.wego.fragments.NearbyPlacesFragment;
import uk.ac.aston.smalljh.wego.fragments.PlacesFragment;
import uk.ac.aston.smalljh.wego.fragments.TripFragment;
import uk.ac.aston.smalljh.wego.fragments.UserInfo;

public class MainActivity extends ActionBarActivity {

    //Array of the menu items
    private String[] menuItems;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private CharSequence drawerTitle;
    private CharSequence title;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("SIGN UP", "Main Activity");

        userInfo = new UserInfo(getApplicationContext());

        int userId = userInfo.getUserID();

        if(userId <= 0)
            finish();

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());



        drawerTitle = "Menu";

        menuItems = getResources().getStringArray(R.array.menu_options);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, menuItems));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        getSupportActionBar().setTitle("Travel");

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
        drawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
       /* switch(item.getItemId()) {
        case R.id.action_logout:
            // create intent to perform web search for this planet

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }*/

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment frag = null;

        if(position == 0) {
            frag = new HomeFragment();

        } else if(position == 1) {
            frag = new TripFragment();

        } else if(position == 2) {
            frag = new PlacesFragment();

        }  else if(position == 3) {
            frag = new NearbyPlacesFragment();

        }  else if(position == 4) {
            frag = new GalleryFragment();

        }  else if(position == 5) {
            frag = new AddTripFragment();

        } else if(position == 6) {

            drawerLayout.closeDrawer(drawerList);

            Intent intent = new Intent(getApplicationContext(), AddPlaceActivity.class);
            startActivity(intent);

        } else if(position == 7) {

            drawerLayout.closeDrawer(drawerList);

            new AlertDialog.Builder(this)
                    .setTitle(R.string.logout_alert_title)
                    .setMessage(R.string.logout_alert_message)
                    .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            userInfo.setUserID(0);
                            Intent intent = new Intent(getApplicationContext(), LogOutActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    }).show();

        } else {
            frag = new TripFragment();
        }

        if(frag != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, frag)
                    .addToBackStack(null)
                    .commit();

            if(position< 6)
                drawerList.setItemChecked(position, true);

            setTitle(menuItems[position]);
            drawerLayout.closeDrawer(drawerList);

        }

    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(title);
    }


}




