package uk.ac.aston.smalljh.wego.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aston.smalljh.wego.R;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class GalleryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery, container, false);

        getActivity().setTitle(R.string.gallery);
        return rootView;
    }
}
