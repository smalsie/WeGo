package uk.ac.aston.smalljh.wego;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by joshuahugh on 19/03/15.
 */
public class AddCompanionDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText name;

    public AddCompanionDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_companion, container);
        name = (EditText) view.findViewById(R.id.add_companion_name);
        getDialog().setTitle(getString(R.string.add_companion));

        Button submitBtn = (Button) view.findViewById(R.id.add_companion_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.add_companion_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            submit();
            return true;
        }
        return false;
    }

    public interface AddCompanionDialogListener {
        void onFinishEditDialog(String inputText);
    }

    private void submit() {
        AddCompanionDialogListener activity = (AddCompanionDialogListener) getActivity();
        activity.onFinishEditDialog(name.getText().toString());
        this.dismiss();
    }

    private void cancel() {
        this.dismiss();
    }
}