package uk.ac.aston.smalljh.wego;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by joshuahugh on 19/03/15.
 */
public class FilterMapDialog extends DialogFragment {

    private EditText title, note;

    public FilterMapDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_map, container);

        DatePicker startDate = (DatePicker) view.findViewById(R.id.dpResult);
        DatePicker endDate = (DatePicker) view.findViewById(R.id.dpResultEnd);

        getDialog().setTitle(getString(R.string.add_note));

        Button submitBtn = (Button) view.findViewById(R.id.add_trip_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.add_trip_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
    public interface FilterMapDialogListener {
        void onFinishNoteEditDialog(String title, String note);
    }

    private void submit() {
        FilterMapDialogListener activity = (FilterMapDialogListener) getActivity();
        activity.onFinishNoteEditDialog(title.getText().toString(), note.getText().toString());
        this.dismiss();
    }

    private void cancel() {
        this.dismiss();
    }
}