package uk.ac.aston.smalljh.wego.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class TestFragment2 extends FragmentPagerAdapter {

    private List<Fragment> fragments;


    public TestFragment2(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        fragments.add(new PlacesFragment());
        fragments.add(new PlacesFragment());
        fragments.add(new PlacesFragment());
        fragments.add(new PlacesFragment());
        fragments.add(new PlacesFragment());
        fragments.add(new PlacesFragment());
        fragments.add(new PlacesFragment());
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
        return "Test";
    }
}
