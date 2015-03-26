package uk.ac.aston.smalljh.wego.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.AddPlaceActivity;
import uk.ac.aston.smalljh.wego.ImagePreview;
import uk.ac.aston.smalljh.wego.MapActivity;
import uk.ac.aston.smalljh.wego.MapPane;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.ImageItem;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class GalleryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery, container, false);

        DatabaseHelper dh = new DatabaseHelper(getActivity().getApplicationContext());

        ArrayList<ImageItem> images = dh.getAllImages();

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        GridViewAdapter customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.gallery_item, images);
        gridView.setAdapter(customGridAdapter);

        setHasOptionsMenu(true);

        Button sort = (Button) rootView.findViewById(R.id.add_pic_button);
        sort.setText("Sort");

        sort.setVisibility(View.VISIBLE);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        getActivity().setTitle(R.string.gallery);
        return rootView;
    }

    private class GridViewAdapter extends ArrayAdapter<ImageItem> {
        private Context context;
        private int layoutResourceId;
        private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

        public GridViewAdapter(Context context, int layoutResourceId,
                               ArrayList<ImageItem> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageTitle = (TextView) row.findViewById(R.id.text);
                holder.image = (ImageView) row.findViewById(R.id.image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            final ImageItem item = data.get(position);
            holder.imageTitle.setText(item.getTitle());
            holder.image.setImageBitmap(item.getImage());

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ImagePreview.class);
                    intent.putExtra("ImageID", item.getID());
                    startActivity(intent);
                }
            });

            return row;
        }

        class ViewHolder {
            TextView imageTitle;
            ImageView image;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_map) {

            Intent intent = new Intent(getActivity().getApplicationContext(), MapActivity.class);

            intent.putExtra("Images", 10);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gallery, menu);


    }

}
