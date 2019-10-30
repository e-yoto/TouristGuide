package pie.edu.touristguide.View.Reminder;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pie.edu.touristguide.Controller.Database.Reminder.ReminderController;
import pie.edu.touristguide.Model.Reminder;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.AddFragmentUtil;
import pie.edu.touristguide.Util.FragmentNavigationUtil;
import pie.edu.touristguide.Util.LocationUtil;

/**
 * @author BoTao Yu
 * Using a fragment, add a Reminder.
 *
 * credit for TextInputLayout: http://www.androidtutorialshub.com/android-material-design-floating-label-for-edittext-tutorial/
 */
public class AddFragment extends Fragment implements View.OnClickListener, EditText.OnFocusChangeListener{

    private final String TAG = AddFragment.class.getSimpleName();
    ImageView cancelIv;
    ImageView addIv;

    TextInputEditText titleEt;
    TextInputEditText startTimeEt;
    TextInputEditText endTimeEt;
    TextInputEditText locationEt;

    TextInputLayout titleTextInputLayout;
    TextInputLayout startTimeTextInputLayout;
    TextInputLayout endTimeTextInputLayout;
    TextInputLayout locationTextInputLayout;

    RecyclerView weatherRv;
    WeatherRvAdapter weatherRvAdapter;


    private ReminderController reminderController;

    private SerializableRootView serializableRootView;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reminder_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize
        viewSetUp(view);
        reminderController = new ReminderController(getActivity());
        serializableRootView = new SerializableRootView((LinearLayout) view);
    }

    private void viewSetUp(View view){
        //toolbar
        cancelIv = view.findViewById(R.id.iv_cancel);
        addIv = view.findViewById(R.id.iv_add);



        //Editboxs
        titleEt = view.findViewById(R.id.et_title);
        startTimeEt = view.findViewById(R.id.tv_start_time);
        endTimeEt = view.findViewById(R.id.tv_end_time);
        locationEt = view.findViewById(R.id.et_location);

        //listeners
        cancelIv.setOnClickListener(this);
        addIv.setOnClickListener(this);
        startTimeEt.setOnFocusChangeListener(this);
        endTimeEt.setOnFocusChangeListener(this);


        //weather icons
        weatherRv = view.findViewById(R.id.rv_reminder_weather);
        weatherRvAdapter = new WeatherRvAdapter();
        weatherRv.setAdapter(weatherRvAdapter);
        weatherRv.setLayoutManager(new GridLayoutManager(getContext(), 3));

        //Errors
        titleTextInputLayout = view.findViewById(R.id.text_input_layout_title);
        startTimeTextInputLayout = view.findViewById(R.id.text_input_layout_start_time);
        endTimeTextInputLayout = view.findViewById(R.id.text_input_layout_end_time);
        locationTextInputLayout = view.findViewById(R.id.text_input_layout_location);


    }

    @Override
    public void onResume() {
        super.onResume();
        AddFragmentUtil.setToolBar(R.id.save_data_toolbar, getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        AddFragmentUtil.setToolBar(R.id.main_toolbar, getActivity());
    }

    /**
     * Do different operations depending on the view's ID.
     * @param view that is clicked
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_cancel:
                //go to ReminderFragment and does not do any CRUD operations
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show();
                FragmentNavigationUtil.replaceFragment(getActivity(), new ReminderFragment());
                break;
            case R.id.iv_add:
                resetErrorTextView();
                if(getIsInputValid()){
                    Toast.makeText(getActivity(), "data saved", Toast.LENGTH_LONG).show();
                    createNewReminder();
                    FragmentNavigationUtil.replaceFragment(getActivity(), new ReminderFragment());
                }else{
                    Toast.makeText(getContext(), "Please input All fields", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("test", serializableRootView);
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setArguments(bundle);

        if(hasFocus){
            switch (v.getId()){
                case R.id.tv_start_time:
                    timePickerFragment.show(getFragmentManager(), getString(R.string.time_picker_fragment_start));
                    break;
                case R.id.tv_end_time:
                    timePickerFragment.show(getFragmentManager(), getString(R.string.time_picker_fragment_end));
                    break;
            }
        }
    }

    /**
     * set isInputValid to false and show error message if one of the input is not valid.
     * @return isInputValid
     */
    private boolean getIsInputValid(){
        isEditTextValid(titleEt, titleTextInputLayout, "*Please enter a title");
        isEditTextValid(startTimeEt, startTimeTextInputLayout, "*Please select a start time");
        isEditTextValid(endTimeEt, endTimeTextInputLayout, "*Please select a end time");
        isEditTextValid(locationEt, locationTextInputLayout, "*Please enter a location");

        String location = locationEt.getText().toString();
        if(!location.isEmpty()){
            if(!LocationUtil.isLocationValid(location, getContext()))
                isEditTextValid(locationEt, locationTextInputLayout, "*" + location + " is not a valid location");
        }


        if(weatherRvAdapter.getSelectedIv() == null){
            createErrorFor(weatherRv, "*Please select a weather");
            isInputValid = false;
        }

        return isInputValid;
    }

    private boolean isInputValid = true;

    private void isEditTextValid(TextInputEditText editText, TextInputLayout layout, String message){
        if(editText.getText().toString().isEmpty()){
            layout.setHint("");
            layout.setError(message);
            if(isInputValid)
                isInputValid = false;
        }
    }

    private void resetErrorTextView(){
        isInputValid = true;
        View[] views = new View[]{weatherRv};
        for(View view: views){
            LinearLayout linearLayout = ((LinearLayout) view.getParent());
            linearLayout.removeAllViews();
            linearLayout.addView(view);
        }
        resetTextInputLayout();
    }

    /**
     * create error message for Views that are not InputEditText
     * @param childView that errorMessage is to be set on
     * @param errorMessage
     */
    private void createErrorFor(View childView, String errorMessage){
        LinearLayout parentView = (LinearLayout) childView.getParent();
        parentView.removeAllViews();

        TextView textView = new TextView(getContext());
        textView.setText(errorMessage);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(getContext().getColor(R.color.red));
        textView.setTextSize(16);
        textView.setId(View.NO_ID);

        parentView.addView(textView);
        parentView.addView(childView);

    }


    private void resetTextInputLayout(){
        titleTextInputLayout.setErrorEnabled(false);
        startTimeTextInputLayout.setErrorEnabled(false);
        endTimeTextInputLayout.setErrorEnabled(false);
        locationTextInputLayout.setErrorEnabled(false);
    }

    private void createNewReminder(){
        String title = titleEt.getText().toString();
        String startTime = startTimeEt.getText().toString();
        String endTime = endTimeEt.getText().toString();
        String location = locationEt.getText().toString();
        int weatherIconId = (int) weatherRvAdapter.getSelectedIv().getTag();
        double[] coordinates = LocationUtil.getCoordinatesFrom(location, getContext());
        Reminder reminder = new Reminder(title, startTime, endTime, weatherIconId, location, coordinates[0], coordinates[1]);
        Log.d(TAG, reminder.toString());
        reminderController.createReminder(reminder);
    }



}
