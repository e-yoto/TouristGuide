package pie.edu.touristguide.View;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import pie.edu.touristguide.Controller.Database.StepCounter.DailyCounterController;
import pie.edu.touristguide.Controller.Database.StepCounter.HistoryCounterController;
import pie.edu.touristguide.Model.StepCounter.DailyCounter;
import pie.edu.touristguide.Model.StepCounter.HistoryCounter;
import pie.edu.touristguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepCounterFragment extends Fragment implements View.OnClickListener, SensorEventListener {
    View rootView;

    SensorManager sensorManager;

    ///////
    //Daily Counter views.
    TextView tvDailySteps;
    TextView tvGoal;
    ProgressBar pbDailyProgress;
    TextView tvCalories;
    TextView tvTimeLeft;
    TextView tvStepsLeft;
    Button btnEditDaily;
    Button btnResetDaily;

    DailyCounter dailyCounter;
    CountDownTimer countDownTimer;
    final static LocalTime END_OF_DAY = LocalTime.parse("23:59:59");
    long timeLeftInMilli;
    DailyCounterController dailyCounterController;

    ////////
    //Global Counter views.
    TextView tvHistorySteps;
    TextView tvKilometers;
    TextView tvFloors;
    TextView tvMiles;
    Button btnResetHistory;

    HistoryCounter historyCounter;
    HistoryCounterController historyCounterController;

    public StepCounterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflates view.
        rootView = inflater.inflate(R.layout.step_counter_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        //Daily Counter views.
        tvDailySteps = (TextView) rootView.findViewById(R.id.tv_daily_steps);
        tvGoal = (TextView) rootView.findViewById(R.id.tv_goal);
        pbDailyProgress = (ProgressBar) rootView.findViewById(R.id.pb_progress);
        tvCalories = (TextView) rootView.findViewById(R.id.tv_calories);
        tvTimeLeft = (TextView) rootView.findViewById(R.id.tv_time);
        tvStepsLeft = (TextView) rootView.findViewById(R.id.tv_steps_left);
        btnEditDaily = (Button) rootView.findViewById(R.id.btn_edit_daily);
        btnResetDaily = (Button) rootView.findViewById(R.id.btn_reset_daily);
        btnEditDaily.setOnClickListener(this);
        btnResetDaily.setOnClickListener(this);


        //History Counter Views
        tvHistorySteps = (TextView) rootView.findViewById(R.id.tv_history_steps);
        tvKilometers = (TextView) rootView.findViewById(R.id.tv_kilometers);
        tvFloors = (TextView) rootView.findViewById(R.id.tv_floors);
        tvMiles = (TextView) rootView.findViewById(R.id.tv_miles);
        btnResetHistory = (Button) rootView.findViewById(R.id.btn_reset_history);
        btnResetHistory.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Gets daily counter progress from database if it exists.
        dailyCounterController = new DailyCounterController(getActivity());
        dailyCounterController.createDailyCounter();
        dailyCounter = dailyCounterController.getDailyCounter();
        //Checks whether it's a new day to reset or not.
        checkDayToResetCounter();
        setUpDailyCounter(dailyCounter);

        historyCounterController = new HistoryCounterController(getActivity());
        historyCounterController.createHistoryCounter();
        historyCounter = historyCounterController.getHistoryCounter();
        setUpHistoryCounter(historyCounter);

        //Calculates the time between current time and end of day in milliseconds
        timeLeftInMilli = Duration.between(LocalTime.now(), END_OF_DAY).toMillis();
        Log.d("Time left", "" + timeLeftInMilli);

        //Countdown timer that performs methods every tick and when it reaches 0.
        countDownTimer = new CountDownTimer(timeLeftInMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilli = millisUntilFinished;
                updateTime();
            }

            @Override
            public void onFinish() {
                dailyCounter.resetProgress();
                dailyCounterController.updateDailyCounter(dailyCounter);
                Log.d("reset", "" + dailyCounter.getSteps());
                setUpDailyCounter(dailyCounter);
                tvTimeLeft.setText("End of Day");
            }
        }.start();

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            Toast.makeText(getContext(), "Step Sensor not available", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Step increases on every step sensor change.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        incrementStep();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_edit_daily:
                editGoal();
                break;
            case R.id.btn_reset_daily:
                resetCounter(R.id.btn_reset_daily);
                break;
            case R.id.btn_reset_history:
                resetCounter(R.id.btn_reset_history);
                break;
        }
    }

    /**
     * Changes the goal desired by the user.
     * It will recalculate all the data.
     */
    public void editGoal(){
        //Opens a new dialog for user to input a new goal.
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Edit number of steps:");

        //Includes an EditText view in the alert dialog.
        final EditText inputGoal = new EditText(getContext());
        inputGoal.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertBuilder.setView(inputGoal);

        alertBuilder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //Changes the progress bar's maximum capacity to the new goal input by the user.
                    dailyCounter.setGoal(Integer.parseInt(inputGoal.getText().toString()));
                    dailyCounterController.updateDailyCounter(dailyCounter);
                    setUpDailyCounter(dailyCounter);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Enter a number", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBuilder.show();
    }

    /**
     * Will reset the user's current progress.
     * It will recalculate all the data.
     */
    public void resetCounter(int id){
        final int btnId = id;
        //Opens a new dialog for user to reset its progress.
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Are you sure to reset your progress?");

        alertBuilder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (btnId == R.id.btn_reset_daily) {
                    dailyCounter.resetProgress();
                    dailyCounterController.updateDailyCounter(dailyCounter);
                    Log.d("reset", "" + dailyCounter.getSteps());
                    setUpDailyCounter(dailyCounter);
                }
                else if (btnId == R.id.btn_reset_history){
                    historyCounter.resetProgress();
                    historyCounterController.updateHistoryCounter(historyCounter);
                    Log.d("reset", "" + historyCounter.getSteps());
                    setUpHistoryCounter(historyCounter);
                }
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertBuilder.show();
    }

    /**
     * Converts milliseconds to hours, minutes and seconds.
     */
    public void updateTime(){
        int hours = (int) (timeLeftInMilli/(1000*60*60)) % 24;
        int minutes = (int) (timeLeftInMilli/(1000*60)) % 60;
        int seconds = (int) (timeLeftInMilli/(1000)) % 60;
        String timeString = "" + hours + ":";

        if(minutes < 10)
            timeString += "0";

        timeString += minutes + ":";

        if(seconds < 10)
            timeString += "0";

        timeString += seconds;

        tvTimeLeft.setText(timeString);
    }

    /**
     * Sets the statistics for the daily counter.
     * @param dailyCounter
     */
    public void setUpDailyCounter(DailyCounter dailyCounter){
        tvDailySteps.setText(String.valueOf(dailyCounter.getSteps()));
        tvGoal.setText(String.valueOf(dailyCounter.getGoal()));
        pbDailyProgress.setMax(dailyCounter.getGoal());
        pbDailyProgress.setProgress(dailyCounter.getSteps());
        tvCalories.setText(String.valueOf(dailyCounter.getCalories()));
        int stepsLeft = dailyCounter.getStepsLeft();
        if (stepsLeft > 0)
            tvStepsLeft.setText(String.valueOf(dailyCounter.getStepsLeft()));
        else
            tvStepsLeft.setText("Completed");
    }

    /**
     * Sets the statistics for history counter.
     * @param historyCounter
     */
    public void setUpHistoryCounter(HistoryCounter historyCounter){
        tvHistorySteps.setText(String.valueOf(historyCounter.getSteps()));
        tvKilometers.setText(String.valueOf(historyCounter.getKilometers()));
        tvFloors.setText(String.valueOf(historyCounter.getFloors()));
        tvMiles.setText(String.valueOf(historyCounter.getMiles()));
    }

    /**
     * Increases step as well as the other statistics related to the steps
     * Updates the views also as the steps increases.
     */
    public void incrementStep(){
        dailyCounter.incrementStep();
        historyCounter.incrementStep();
        setUpDailyCounter(dailyCounter);
        setUpHistoryCounter(historyCounter);
        dailyCounterController.updateDailyCounter(dailyCounter);
        historyCounterController.updateHistoryCounter(historyCounter);
    }

    @Override
    public void onPause() {
        super.onPause();

        //Saves current date to be compared with another day
        //Comparison will determine whether the daily step counter will reset or not
        LocalDate today = LocalDate.now();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("COUNTER_LAST_UPDATE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DATE", today.toString());
        editor.commit();
    }

    /**
     * Saves the current date using Shared Preferences to be compared on the next day
     * to see if it should reset or not.
     */
    public void checkDayToResetCounter(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("COUNTER_LAST_UPDATE", Context.MODE_PRIVATE);
        String lastUpdate = sharedPreferences.getString("DATE", "UNDEFINED");

        LocalDate today = LocalDate.now();
        if (!lastUpdate.equalsIgnoreCase(today.toString())){
            dailyCounter.resetProgress();
            dailyCounterController.updateDailyCounter(dailyCounter);
        }
    }
}
