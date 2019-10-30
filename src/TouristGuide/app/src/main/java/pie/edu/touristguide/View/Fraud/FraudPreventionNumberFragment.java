package pie.edu.touristguide.View.Fraud;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.Fraud.GetPhoneDataTask;
import pie.edu.touristguide.Model.Fraud.PhoneNumber;
import pie.edu.touristguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FraudPreventionNumberFragment extends Fragment implements View.OnClickListener{
    private final static String TAG = FraudPreventionNumberFragment.class.getSimpleName();

    View rootView;
    EditText etNumber;
    Button btnFindFraud;

    int position = 0;

    public FraudPreventionNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflates view.
        rootView = inflater.inflate(R.layout.fraud_prevention_number_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNumber = (EditText) rootView.findViewById(R.id.input_phone_number);
        btnFindFraud = (Button) rootView.findViewById(R.id.btn_find_fraud);
        btnFindFraud.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.btn_find_fraud:
                findResults();
                break;
        }
    }

    /**
     * Find results based on the user input.
     */
    public void findResults(){
        String stringNumber = etNumber.getText().toString();
        Log.d(TAG, "Number: " + stringNumber);

        if (stringNumber.equals("")){
            buildAlertDialog(null, "Please enter required field");
            return;
        }

        PhoneNumber results = getPhoneInfoWith(stringNumber);
        Log.d(TAG, "Phone Information: " + results);

        //Creates a view where the results will be displayed.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View resultView = inflater.inflate(R.layout.fraud_result_layout, null);
        ImageView displayCheckLogo = (ImageView) resultView.findViewById(R.id.fraudulency_check);
        TextView displayCheckText = (TextView) resultView.findViewById(R.id.fraudulency_check_text);
        TextView displayResults = (TextView) resultView.findViewById(R.id.display_results);

        try{
            if (results.isValid()){
                //Shows a green checkmark to indicate that it is a valid address.
                displayCheckLogo.setImageResource(R.drawable.checked);
                displayCheckText.setText("Valid Phone Number");

                String stringResults = "Calling Code: " + results.getCallingCode() + "\n"
                        + "Line Type: " + results.getLineType() + "\n"
                        + "Carrier: " + results.getCarrier() + "\n"
                        + "Name: " + results.getName() + "\n"
                        + "Age: " + results.getAge() + "\n"
                        + "Gender: " + results.getGender() + "\n"
                        + "Street Line 1: " + results.getStreetOne() + "\n"
                        + "Street Line 2: " + results.getStreetTwo() + "\n"
                        + "City: " + results.getCity() + "\n"
                        + "Zip Code: " + results.getZip() + "\n"
                        + "State: " + results.getState() + "\n"
                        + "Country: " + results.getCountry();
                displayResults.setText(stringResults);
            }
            else{
                //Shows a red cross to indicate that it is not a valid address.
                displayCheckLogo.setImageResource(R.drawable.crossed);
                displayCheckText.setText("Invalid Phone Number");
                displayResults.setText("");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        buildAlertDialog(resultView, "");
    }

    /**
     * Builds and shows an alert dialog that will contain information about the address searched.
     * @param v
     * @param m
     */
    public void buildAlertDialog(View v, String m){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setView(v);
        alertDialog.setMessage(m);
        alertDialog.setNegativeButton("OK", null);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    /**
     * Retrieves the number's information based on the user input.
     * @param number
     * @return
     */
    private PhoneNumber getPhoneInfoWith(String number){
        PhoneNumber phoneNumber = new PhoneNumber();

        try {
            phoneNumber = new GetPhoneDataTask().execute(number).get();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.d(TAG, "Phone Number: " + phoneNumber);

        return phoneNumber;
    }

    public void setPosition(int position){
        this.position = position;
    }
}
