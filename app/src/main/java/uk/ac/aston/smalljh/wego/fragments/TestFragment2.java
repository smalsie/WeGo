package uk.ac.aston.smalljh.wego.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.fragments.places.*;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class TestFragment2 extends FragmentPagerAdapter {

    private List<PlacesFrag> fragments;


    public TestFragment2(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<PlacesFrag>();
        fragments.add(new PlacesOverviewFragment());
        fragments.add(new PlacesPlacesFragment());
        fragments.add(new PlacesNotesFragment());
        fragments.add(new PlacesMapFragment());
        fragments.add(new PlacesCompanionsFragment());
        fragments.add(new PlacesGalleryFragment());
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
}
