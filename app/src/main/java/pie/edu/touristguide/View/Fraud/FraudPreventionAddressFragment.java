package pie.edu.touristguide.View.Fraud;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.Fraud.GetAddressDataTask;
import pie.edu.touristguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FraudPreventionAddressFragment extends Fragment implements View.OnClickListener, OnCountryPickerListener {
    private final static String TAG = FraudPreventionAddressFragment.class.getSimpleName();

    View rootView;
    EditText etStreetOne;
    EditText etStreetTwo;
    EditText etZip;
    EditText etCountry;
    Button btnFindFraud;
    CountryPicker countryPicker;

    int position = 0;

    public FraudPreventionAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflates view
        rootView = inflater.inflate(R.layout.fraud_prevention_address_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnFindFraud = (Button) rootView.findViewById(R.id.btn_find_fraud);
        etStreetOne = (EditText) rootView.findViewById(R.id.input_street_line_1);
        etStreetTwo = (EditText) rootView.findViewById(R.id.input_street_line_2);
        etZip = (EditText) rootView.findViewById(R.id.input_zip);
        etCountry = (EditText) rootView.findViewById(R.id.input_country_code);
        countryPicker = new CountryPicker.Builder().with(getActivity())
                .listener(this).build();
        etCountry.setOnClickListener(this);
        btnFindFraud.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Sets the tabs for address and phone verifiers
        TabLayout tabLayout = getActivity().findViewById(R.id.tablayout_fraud);
        ViewPager viewPager = getActivity().findViewById(R.id.pager_fraud);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.input_country_code:
                countryPicker.showDialog(getActivity().getSupportFragmentManager());
                break;
            case R.id.btn_find_fraud:
                findResults();
                break;
        }
    }

    /**
     * Sets the edittext's text of the country's code
     * @param country
     */
    @Override
    public void onSelectCountry(Country country) {
        etCountry.setText(country.getCode());
    }


    /**
     * Finds and displays the results based from the user inputs.
     */
    public void findResults(){
        String streetOne = etStreetOne.getText().toString();
        String streetTwo = etStreetTwo.getText().toString();
        String zip = etZip.getText().toString();
        String country = etCountry.getText().toString();

        if (streetOne.equals("") || zip.equals("")){
            buildAlertDialog(null, "Please enter required fields");
            return;
        }

        JSONObject results = getAddressInfoWith(streetOne, streetTwo, zip, country);
        Log.d(TAG, "Results: " + results);

        //Creates a view where the results will be displayed.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View resultView = inflater.inflate(R.layout.fraud_result_layout, null);
        ImageView displayCheckLogo = (ImageView) resultView.findViewById(R.id.fraudulency_check);
        TextView displayCheckText = (TextView) resultView.findViewById(R.id.fraudulency_check_text);
        TextView displayResults = (TextView) resultView.findViewById(R.id.display_results);

        try{
            if (results.getBoolean("is_valid")){
                //Shows a green checkmark to indicate that it is a valid address.
                displayCheckLogo.setImageResource(R.drawable.checked);
                displayCheckText.setText("Valid Address");

                String stringResults = "Street Line 1: " + results.getString("street_one") + "\n"
                        + "Street Line 2: " + results.getString("street_two") + "\n"
                        + "City: " + results.getString("city") + "\n"
                        + "Zip Code: " + results.getString("zip") + "\n"
                        + "State: " + results.getString("state") + "\n"
                        + "Country: " + results.getString("country");
                displayResults.setText(stringResults);
            }
            else{
                //Shows a red cross to indicate that it is not a valid address.
                displayCheckLogo.setImageResource(R.drawable.crossed);
                displayCheckText.setText("Invalid Address");
                displayResults.setText("");
            }
        }
        catch (JSONException e){
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
     * Retrieves the address' information based on the user inputs.
     * @param streetOne
     * @param streetTwo
     * @param zip
     * @param country
     * @return
     */
    private JSONObject getAddressInfoWith(String streetOne, String streetTwo, String zip, String country){
        JSONObject addressInfo = new JSONObject();

        try {
            addressInfo = new GetAddressDataTask().execute(streetOne, streetTwo, zip, country).get();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.d(TAG, "Address Information: " + addressInfo);

        return addressInfo;
    }

    public void setPosition(int position){
        this.position = position;
    }
}
