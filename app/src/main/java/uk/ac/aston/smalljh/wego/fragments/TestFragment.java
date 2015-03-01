package uk.ac.aston.smalljh.wego.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aston.smalljh.wego.R;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class TestFragment extends Fragment {

    private FragmentTabHost tabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.test_host);

        tabHost.addTab(tabHost.newTabSpec("simple").setIndicator("Simple"),
                PlacesFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Simple2"),
                TripFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Simple2"),
                TripFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Simple2"),
                TripFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Simple2"),
                TripFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Simple2"),
                TripFragment.class, null);

        return tabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabHost = null;
    }
}
