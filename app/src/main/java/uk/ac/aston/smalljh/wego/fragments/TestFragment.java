package uk.ac.aston.smalljh.wego.fragments;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import uk.ac.aston.smalljh.wego.R;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class TestFragment extends Fragment {
    private static final int NUM_ITEMS = 10;

    private TestFragment2 pageAdapter;

    private ViewPager pager;

    private PagerSlidingTabStrip tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabhost, container, false);

        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        pager.setAdapter(new TestFragment2(getChildFragmentManager()));

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);


        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setTextColorResource(R.color.white);


        tabs.setIndicatorColorResource(R.color.white);
        tabs.setShouldExpand(true);

        changeColor(getResources().getColor(R.color.white));

        tabs.setViewPager(pager);



        getActivity().setTitle(R.string.add_place);
        return rootView;
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
