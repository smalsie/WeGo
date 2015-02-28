package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import uk.ac.aston.smalljh.wego.R;

public class MapPane extends Activity implements OnMapReadyCallback {

    private boolean fullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        fullscreen = false;

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final LinearLayout content = (LinearLayout) findViewById(R.id.locations);

        final ImageButton fullscreenButton = (ImageButton) findViewById(R.id.map_full_screen_button);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fullscreen) {

                    fullscreen = false;
                    content.setVisibility(View.VISIBLE);
                    fullscreenButton.setImageResource(R.drawable.ic_action_full_screen );

                } else {

                    fullscreen = true;
                    content.setVisibility(View.GONE);
                    fullscreenButton.setImageResource(R.drawable.ic_action_return_from_full_screen);

                }

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-33.867, 151.206);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));

        map.setMyLocationEnabled(true);
    }
}