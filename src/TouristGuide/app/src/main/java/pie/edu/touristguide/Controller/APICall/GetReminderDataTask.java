package pie.edu.touristguide.Controller.APICall;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author BoTao Yu
 *
 * Get icons of the current weather from open weather api
 */
public class GetReminderDataTask extends AsyncTask<String[], Integer, String> {

    private static final String OPEN_WEATHER_MAP_API_KEY = "b5adc531951fd81eb7d7645fee4cf796";

    public static String getWeatherIcons(String lat, String lng){

        //http://api.openweathermap.org/data/2.5/weather?lat=45.5581968&lon=-73.8703848&appid=b5adc531951fd81eb7d7645fee4cf796

        Uri.Builder weatherUriBuilder = new Uri.Builder();
        weatherUriBuilder.scheme("https")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("lat", lat)
                .appendQueryParameter("lon", lng)
                .appendQueryParameter("appid", OPEN_WEATHER_MAP_API_KEY);
        String weatherStrURL = weatherUriBuilder.build().toString();
        Log.d("Test: get weather", weatherStrURL);

        try{
            JSONObject jsonObject = JSONUtil.getJSONObjectFrom(weatherStrURL);
            JSONObject firstWeather = jsonObject.getJSONArray("weather").getJSONObject(0);
            String icon = firstWeather.getString("icon");
            Log.d("Weather Icon: ", icon);
            return icon;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }




    @Override
    protected String doInBackground(String[]... strings) {
        return getWeatherIcons(strings[0][0], strings[0][1]);
    }
}
