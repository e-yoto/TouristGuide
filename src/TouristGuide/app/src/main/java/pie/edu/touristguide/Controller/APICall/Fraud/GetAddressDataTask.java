package pie.edu.touristguide.Controller.APICall.Fraud;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import pie.edu.touristguide.Controller.APICall.JSONUtil;

public class GetAddressDataTask extends AsyncTask<String, Integer, JSONObject> {
    //TODO: Get new API Key if it reaches limit
    //Current API Query Usages: 115/200 (by April 21, 2019)

    private final static String TAG = GetAddressDataTask.class.getSimpleName();
    private final static String API_KEY = "063a465f300b4eb891fe369a8f01a1a0";
    private static JSONObject addressInfo = new JSONObject();

    /**
     * Retrieves and returns information about the address.
     * @param streetOne
     * @param streetTwo
     * @param zip
     * @param country
     * @return
     */
    public static JSONObject getAddressInfo(String streetOne, String streetTwo, String zip, String country){
        JSONObject apiResult;
        try {
            //URL FORMAT:
            //https://proapi.whitepages.com/3.1/location_intel?api_key=063a465f300b4eb891fe369a8f01a1a0
            //&city="Montreal
            //&country_code=CA
            //&postal_code=POSTAL   --required
            //&state_code=QC
            //&street_line_1=999+Heaven+Street   --required
            //&street_line_2=null
            Uri.Builder addressUriBuilder = new Uri.Builder();
            addressUriBuilder.scheme("https")
                    .authority("proapi.whitepages.com")
                    .appendPath("3.1")
                    .appendPath("location_intel")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("street_line_1", streetOne)
                    .appendQueryParameter("street_line_2", streetTwo)
                    .appendQueryParameter("postal_code", zip)
                    .appendQueryParameter("country_code", country);

            String addressURL = addressUriBuilder.build().toString();
            Log.d(TAG, "Address URL: " + addressURL);

            apiResult = JSONUtil.getJSONObjectFrom(addressURL);

            boolean isValid = apiResult.getBoolean("is_valid");
            Log.d(TAG, "Address Validity: " + isValid);

            String aStreetOne = apiResult.getString("street_line_1");
            String aStreetTwo = apiResult.getString("street_line_2");
            String aCity = apiResult.getString("city");
            String aZip = apiResult.getString("postal_code");
            String aState = apiResult.getString("state_code");
            String aCountry = apiResult.getString("country_code");

            addressInfo.put("is_valid", isValid);
            addressInfo.put("street_one", aStreetOne);
            addressInfo.put("street_two", aStreetTwo);
            addressInfo.put("city", aCity);
            addressInfo.put("zip", aZip);
            addressInfo.put("state", aState);
            addressInfo.put("country", aCountry);

        }
        catch (JSONException e){//change later
            e.printStackTrace();
        }
        Log.d(TAG, "Address Information: " + addressInfo);

        return addressInfo;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        return getAddressInfo(strings[0], strings[1], strings[2], strings[3]);
    }
}
