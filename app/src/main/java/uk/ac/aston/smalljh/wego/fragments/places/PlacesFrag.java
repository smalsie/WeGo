package uk.ac.aston.smalljh.wego.fragments.places;

import android.support.v4.app.Fragment;

import uk.ac.aston.smalljh.wego.PlaceItem;

/**
 * Created by joshuahugh on 05/03/15.
 */
public abstract class PlacesFrag extends Fragment {

    protected PlaceItem placeItem;

    public abstract String getName();

    public void setPlaceItem(PlaceItem placeItem) {
        this.placeItem = placeItem;
    }
}
