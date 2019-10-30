package pie.edu.touristguide.Controller.APICall.Fraud;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import pie.edu.touristguide.Controller.APICall.JSONUtil;
import pie.edu.touristguide.Model.Fraud.PhoneNumber;

public class GetPhoneDataTask extends AsyncTask<String, Integer, PhoneNumber> {
    //TODO: Get new API Key if it reaches limit
    //Current API Query Usages: 40/200 (by April 21, 2019)

    private final static String TAG = GetPhoneDataTask.class.getSimpleName();
    private final static String API_KEY = "2e23c91e834e4a7099657aede2bfd16c";

    /**
     * Retrieves and returns information about the phone number.
     * @param number
     * @return
     */
    public static PhoneNumber getPhoneInfo(String number){
        JSONObject apiResult;
        PhoneNumber phoneNumber;
        Boolean isValid = false;

        try{
            //URL FORMAT:
            //https://proapi.whitepages.com/3.0/phone.json?api_key=2e23c91e834e4a7099657aede2bfd16c
            //&phone=5145145144
            Uri.Builder phoneUriBuilder = new Uri.Builder();
            phoneUriBuilder.scheme("https")
                    .authority("proapi.whitepages.com")
                    .appendPath("3.0")
                    .appendPath("phone.json")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("phone", number);

            String phoneURL = phoneUriBuilder.build().toString();
            Log.d(TAG, "Phone URL: " + phoneURL);

            apiResult = JSONUtil.getJSONObjectFrom(phoneURL);

            isValid = apiResult.getBoolean("is_valid");
            Log.d(TAG, "Phone Validity: " + isValid);

            String callingCode = apiResult.getString("country_calling_code");
            String lineType = apiResult.getString("line_type");
            String carrier = apiResult.getString("carrier");

            String name;
            String age;
            String gender;
            if (apiResult.getJSONArray("belongs_to").length() != 0){
                name = apiResult.getJSONArray("belongs_to").getJSONObject(0).getString("name");
                age = apiResult.getJSONArray("belongs_to").getJSONObject(0).getString("age_range");
                gender = apiResult.getJSONArray("belongs_to").getJSONObject(0).getString("gender");
            }
            else {
                name = "Unidentified";
                age = "Unidentified";
                gender = "Unidentified";
            }
            Log.d(TAG, "Owner Name: " + name);

            String streetOne = apiResult.getJSONArray("current_addresses").getJSONObject(0).getString("street_line_1");
            String streetTwo = apiResult.getJSONArray("current_addresses").getJSONObject(0).getString("street_line_2");
            String city = apiResult.getJSONArray("current_addresses").getJSONObject(0).getString("city");
            String zip = apiResult.getJSONArray("current_addresses").getJSONObject(0).getString("postal_code");
            String state = apiResult.getJSONArray("current_addresses").getJSONObject(0).getString("state_code");
            String country = apiResult.getJSONArray("current_addresses").getJSONObject(0).getString("country_code");

            if (isValid) {
                phoneNumber = new PhoneNumber(isValid, callingCode, lineType, carrier,
                        name, age, gender, streetOne, streetTwo, city, zip, state, country);
            }
            else {
                phoneNumber = new PhoneNumber();
                phoneNumber.setValid(isValid);
            }

        }
        catch (JSONException e){
            e.printStackTrace();

            phoneNumber = new PhoneNumber();
            phoneNumber.setValid(isValid);
        }

        return phoneNumber;
    }

    @Override
    protected PhoneNumber doInBackground(String... strings) {
        return getPhoneInfo(strings[0]);
    }
}
