package pie.edu.touristguide.Controller.APICall;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pie.edu.touristguide.Model.PublicHoliday;
import static pie.edu.touristguide.View.PublicHolidays.PublicHolidaysDialog.countryCode;
import static pie.edu.touristguide.View.PublicHolidays.PublicHolidaysFragment.currentYear;

/**
 * @author Sebastien El-Hamaoui
 * Public Holidays API call.
 */

public class GetPublicHolidayTask extends AsyncTask<String[], Integer, String> {
    //Variables declaration.
    private static final String CALENDARIFIC_API_KEY = "95cdca5488ec597c1763ccf9e90b10b3e5ab0044";
    private static PublicHoliday publicHoliday;
    private static ArrayList<PublicHoliday> publicHolidaysList = new ArrayList<>();


    public static ArrayList<PublicHoliday> getPublicHolidayObject() {

        //https://calendarific.com/api/v2/holidays?&api_key=95cdca5488ec597c1763ccf9e90b10b3e5ab0044&country=CA&year=2019

        //API call for the public holidays data.
        try {
            //API call itself.
            Uri.Builder publicHolidaysUriBuilder = new Uri.Builder();
            publicHolidaysUriBuilder.scheme("https")
                    .authority("calendarific.com")
                    .appendPath("api")
                    .appendPath("v2")
                    .appendPath("holidays")
                    .appendQueryParameter("api_key", CALENDARIFIC_API_KEY)
                    .appendQueryParameter("country", countryCode)
                    .appendQueryParameter("year", currentYear);
            String publicHolidaysQueryUrl = publicHolidaysUriBuilder.build().toString();
            Log.d("Calendarific API Call Test: ", publicHolidaysQueryUrl);

            JSONObject jsonObject = JSONUtil.getJSONObjectFrom(publicHolidaysQueryUrl);

            //Reset the list to prevent RecyclerView duplication problem.
            publicHolidaysList = new ArrayList<>();

            //Gets the holiday name data.
            JSONObject responseData = jsonObject.getJSONObject("response");
            String holidaysData = responseData.getString("holidays");
            JSONArray holidaysArray = new JSONArray(holidaysData);

            //Loop to get every single holiday in the year.
            for(int i = 0; i < holidaysArray.length(); i++) {
                JSONObject holidayPart = holidaysArray.getJSONObject(i);
                String holidayName = holidayPart.getString("name");

                //Gets the holiday date data.
                JSONObject holidayDatePart = holidayPart.getJSONObject("date");
                String holidayDate = holidayDatePart.getString("iso");

                publicHoliday = new PublicHoliday(holidayName, holidayDate);
                publicHolidaysList.add(publicHoliday);
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return publicHolidaysList;
    }

    @Override
    protected String doInBackground(String[]... strings) {
        return null;
    }
}
