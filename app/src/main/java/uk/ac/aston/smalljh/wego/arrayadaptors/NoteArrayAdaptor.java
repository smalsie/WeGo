package uk.ac.aston.smalljh.wego.arrayadaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import uk.ac.aston.smalljh.wego.Note;
import uk.ac.aston.smalljh.wego.R;

/**
 * Created by joshuahugh on 20/03/15.
 */
public class NoteArrayAdaptor extends ArrayAdapter<Note> {
    private final Context context;
    private final List<Note> notes;

    public NoteArrayAdaptor(Context context, List<Note> notes) {

        super(context, R.layout.home_fragment,notes);
        this.context = context;
        this.notes = notes;
    }

    /**
     * Overide the view method
     *
     * @return a view (the list of all of the events)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate events_list.xml
        View rowView = inflater.inflate(R.layout.notes_item, parent, false);

        SwipeLayout swipeLayout =  (SwipeLayout) rowView.findViewById(R.id.place_note_swipe);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

//set drag edge.
        swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);
        swipeLayout.setDragDistance(10);

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout swipeLayout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout swipeLayout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });


        //Each of the textviews to add specified content to
        TextView title= (TextView) rowView.findViewById(R.id.note_item_title);

        title.setText(notes.get(position).getTitle());

        ExpandableTextView notesText = (ExpandableTextView) rowView.findViewById(R.id.place_notes_expand_text_view);

        notesText.setText(notes.get(position).getNote());


        return rowView;
    }

}
