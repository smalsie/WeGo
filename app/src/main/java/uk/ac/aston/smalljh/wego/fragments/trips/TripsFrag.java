package uk.ac.aston.smalljh.wego.fragments.trips;

import android.support.v4.app.Fragment;

import uk.ac.aston.smalljh.wego.TripItem;

/**
 * Created by joshuahugh on 05/03/15.
 */
public abstract class TripsFrag extends Fragment {

    protected TripItem tripItem;

    public abstract String getName();

    public void setTripItem(TripItem tripItem) {
        this.tripItem = tripItem;
    }

    public void refresh() { }
}
