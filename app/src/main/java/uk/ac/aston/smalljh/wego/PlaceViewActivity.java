package uk.ac.aston.smalljh.wego;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.fragments.places.PlacesCompanionsFragment;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesFrag;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesGalleryFragment;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesMapFragment;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesNotesFragment;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesOverviewFragment;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesPlacesFragment;


public class PlaceViewActivity extends ActionBarActivity implements AddCompanionDialog.AddCompanionDialogListener, AddNoteDialog.AddNoteDialogListener {

    private PagerSlidingTabStrip tabs;
    private PlaceItem placeItem;
    private PlacesViewAdapter pagesViewAdaptor;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_view);

        long placeID = (long) getIntent().getLongExtra("Place", 0);

        if(placeID == 0)
            finish();

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        SQLiteDatabase db = dh.getReadableDatabase();

        placeItem = dh.getPlace(placeID, db);

        Log.i("LATLNG", placeItem.getGPlace().getLatitude() + " : " + placeItem.getGPlace().getLongitude());

        pagesViewAdaptor = new PlacesViewAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.places_pager);
        pager.setAdapter(pagesViewAdaptor);

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.places_tabs);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setTextColorResource(R.color.white);


        tabs.setIndicatorColorResource(R.color.white);
        tabs.setShouldExpand(true);

        changeColor(getResources().getColor(R.color.white));

        tabs.setViewPager(pager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.dark_purple));

        }


        getSupportActionBar().setTitle(placeItem.getTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        placeItem.putCompanion(inputText, getApplicationContext());
        pagesViewAdaptor.refresh(1);

        pager.setCurrentItem(1, false);

    }

    @Override
    public void onFinishNoteEditDialog(String title, String note) {
        placeItem.putNote(title, note, getApplicationContext());
        pagesViewAdaptor.refresh(2);

        pager.setCurrentItem(2, false);
    }

    private class PlacesViewAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;
        private List<PlacesFrag> fragments;

        public PlacesViewAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;

            this.fragments = new ArrayList<PlacesFrag>();
            fragments.add(new PlacesOverviewFragment());

            fragments.add(new PlacesCompanionsFragment());
            fragments.add(new PlacesNotesFragment());

            for(PlacesFrag pf : fragments)
                pf.setPlaceItem(placeItem);

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getName();
        }

        public void refresh(int index) {
            fragments.get(index).refresh();
        }
    }

    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_background);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

        }


    }
}
