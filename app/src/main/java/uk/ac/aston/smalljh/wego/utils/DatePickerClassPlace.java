package uk.ac.aston.smalljh.wego.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import uk.ac.aston.smalljh.wego.AddPlaceActivity;
import uk.ac.aston.smalljh.wego.AddTripActivity;

/**
 * Created by joshuahugh on 18/03/15.
 */
public class DatePickerClassPlace extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int returnCode = 10;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        int year;
        int month;
        int day;

        if(getArguments() == null) {

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

        } else {

            year = getArguments().getInt("year");
            month = getArguments().getInt("month") - 1;
            day = getArguments().getInt("day");

        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            AddPlaceActivity activity = (AddPlaceActivity) getActivity();
            activity.onFinishEditDialog(year, monthOfYear, dayOfMonth);
            this.dismiss();


    }
}
