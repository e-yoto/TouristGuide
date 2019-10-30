package pie.edu.touristguide.View.Reminder;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pie.edu.touristguide.R;

/**
 * @author BoTao Yu
 * Create TimePickerDialog when either the start_time or the end_time TextView is clicked.
 *
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private final String TAG = TimePickerFragment.class.getSimpleName();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //get current hour and minute
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        //initialize TimePickerDialog with the current hour and minute
        return new TimePickerDialog(getActivity(), this, hour, minute, false);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String dateIn12HourFormat = null;
        try {
            //Convert time from 24 hour format to 12 hour format
            SimpleDateFormat _24HourFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourFormat = new SimpleDateFormat("hh:mm a");

            Date dateIn24HourFormat = _24HourFormat.parse(hourOfDay + ":" + minute);
            dateIn12HourFormat = _12HourFormat.format(dateIn24HourFormat);
            Log.d(TAG +" : dateIn12HourFormat", dateIn12HourFormat);
            Log.d(TAG +" : dateIn24HourFormat", dateIn24HourFormat.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Using SerializableRootView rather that view specified in this parameter
        //if the view specified in this parameter is used, the TextView will not change accordingly after 1 reminder has been added.
        SerializableRootView serializableRootView = (SerializableRootView) getArguments().getSerializable("test");
        LinearLayout linearLayout = serializableRootView.getRootLayout();
        EditText timeTv = null;

        //Initialize the TextView that has been clicked to start this fragment.
        Log.d(TAG, getTag());
        if(getTag() == getString(R.string.time_picker_fragment_start)){
            timeTv = linearLayout.findViewById(R.id.tv_start_time);
        }
        else if(getTag() == getString(R.string.time_picker_fragment_end)){
            timeTv = linearLayout.findViewById(R.id.tv_end_time);
        }

        timeTv.setText(dateIn12HourFormat);
        Log.d(TAG + " in timeTv", timeTv.getText().toString());
    }
}
