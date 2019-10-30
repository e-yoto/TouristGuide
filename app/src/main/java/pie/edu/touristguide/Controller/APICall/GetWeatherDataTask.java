package pie.edu.touristguide.Controller.APICall;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import pie.edu.touristguide.Model.Weather;
import static pie.edu.touristguide.View.Weather.WeatherDialog.cityName;

/**
 * @author Sebastien El-Hamaoui
 * Current Weather and Forecast API calls.
 */

public class GetWeatherDataTask extends AsyncTask<String[], Integer, String> {
    //Variables declaration.
    private static final String OPEN_WEATHER_MAP_API_KEY = "b5adc531951fd81eb7d7645fee4cf796";
    private static final String FORECAST_DAYS = "7";
    private static Weather currentWeather;
    private static Weather forecastWeather;
    private static ArrayList<Weather> forecastWeatherList = new ArrayList<>();

    //Returns a weather object containing the data for the current weather.
    public static Weather getCurrentWeatherObject() {

        //http://api.openweathermap.org/data/2.5/weather?q=Montreal&appid=5c695a874cbe5109ce0e2515705bc696

        //API call for the current weather data.
        try {
            //API call itself.
            Uri.Builder weatherUriBuilder = new Uri.Builder();
            weatherUriBuilder.scheme("https")
                    .authority("api.openweathermap.org")
                    .appendPath("data")
                    .appendPath("2.5")
                    .appendPath("weather")
                    .appendQueryParameter("q", cityName)
                    .appendQueryParameter("appid", OPEN_WEATHER_MAP_API_KEY);
            String weatherQueryURL = weatherUriBuilder.build().toString();
            Log.d("Current Weather API Call Test: ", weatherQueryURL);

            JSONObject jsonObject = JSONUtil.getJSONObjectFrom(weatherQueryURL);

            //Fetch Weather condition data in the Array.
            String weatherData = jsonObject.getString("weather");
            JSONArray weatherArray = new JSONArray(weatherData);
            JSONObject weatherPart = weatherArray.getJSONObject(0);
            String weatherCondition = weatherPart.getString("main");

            //Fetch Temperature and Humidity data in the object.
            String mainData = jsonObject.getString("main");
            JSONObject mainPart = new JSONObject(mainData);
            Double temperature = mainPart.getDouble("temp");
            String humidity = mainPart.getString("humidity");

            //Converting from Kelvin to Celsius and shortening to natural value.
            Double temperatureCelsius = temperature - 273.15;
            String temperatureCelsiusShortened = String.format("%.0f", temperatureCelsius);

            //Fetch Wind data in the object.
            String windData = jsonObject.getString("wind");
            JSONObject windPart = new JSONObject(windData);
            Double wind = windPart.getDouble("speed");

            //Converting from m/s to km/h and shorten to 2 decimal places.
            Double windConverted = wind * 3.6;
            String windShortened = String.format("%.2f", windConverted);

            //Fetch Date data in the object.
            //long unixDateFormat = jsonObject.getLong("dt");

            //Converting the date from UnixTime to Readable format.
            //Date date = new java.util.Date(unixDateFormat * 1000);
            //Only gets locale time currently. (WIP)
            Date date = Calendar.getInstance(TimeZone.getTimeZone(cityName)).getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, d   H:mm a");
            //simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC-5"));
            String formattedDate = simpleDateFormat.format(date);

            //Fetch Country code data in the object.
            String sysData = jsonObject.getString("sys");
            JSONObject sysPart = new JSONObject(sysData);
            String countryCode = sysPart.getString("country");

            //Fetch Country name data in the object.
            String countryName = jsonObject.getString("name");

            //Creates a Weather object with the current day's weather.
            currentWeather = new Weather(countryName, formattedDate, windShortened,
                    humidity, temperatureCelsiusShortened, weatherCondition, countryCode);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    //Returns an ArrayList with the 7 Weather objects containing the fetched data.
    public static ArrayList<Weather> getForecastWeatherObject() {

        //http://api.openweathermap.org/data/2.5/forecast?q=Montreal&cnt=7&appid=5c695a874cbe5109ce0e2515705bc696

        //API call for the 7 days forecast weather data.
        try {
            //API call itself.
            Uri.Builder weatherUriBuilder = new Uri.Builder();
            weatherUriBuilder.scheme("https")
                    .authority("api.openweathermap.org")
                    .appendPath("data")
                    .appendPath("2.5")
                    .appendPath("forecast")
                    .appendQueryParameter("q", cityName)
                    .appendQueryParameter("cnt", FORECAST_DAYS)
                    .appendQueryParameter("appid", OPEN_WEATHER_MAP_API_KEY);
            String weatherQueryURL = weatherUriBuilder.build().toString();
            Log.d("Forecast Weather API Call Test: ", weatherQueryURL);

            //Reset the list to prevent RecyclerView duplication problem.
            forecastWeatherList = new ArrayList<>();

            //Declaring needed data for the loop and fetching the JSON list object.
            JSONObject jsonObject = JSONUtil.getJSONObjectFrom(weatherQueryURL);
            String listData = jsonObject.getString("list");
            JSONArray listArray = new JSONArray(listData);
            //Fetch the unix timestamp from the forecast and subtract by 1day / 86400unixunits.
            long unixDateFormat = listArray.getJSONObject(1).getLong("dt") - 86400;

            //Loop that evaluates 7 Weather objects for the forecast.
            for (int j = 0; j < 7; j++) {

                //List data in the Array.
                String formattedDate = "";
                String temperatureCelsiusShortened = "";
                String forecastWeatherCondition = "";

                //Setting count.
                JSONObject listPart = listArray.getJSONObject(j);

                //Incrementing by 1 day.
                unixDateFormat += 86400;

                //Converting the date from UnixTime to Readable format.
                Date date = new java.util.Date(unixDateFormat * 1000L);
                SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("E, d");
                simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC-5"));
                formattedDate = simpleDateFormat.format(date);

                //Fetching the temperature.
                JSONObject mainPart = listPart.getJSONObject("main");
                Double forecastTemperature = mainPart.getDouble("temp");

                //Converting from Kelvin to Celsius and shortening to natural value.
                Double temperatureCelsius = forecastTemperature - 273.15;
                temperatureCelsiusShortened = String.format("%.0f", temperatureCelsius);

                //Fetch Weather condition data in the Array.
                JSONArray conditionPart = listPart.getJSONArray("weather");
                JSONObject weatherData = conditionPart.getJSONObject(0);
                forecastWeatherCondition = weatherData.getString("main");

                //Create the object and add it to the ArrayList.
                forecastWeather = new Weather(formattedDate, temperatureCelsiusShortened,
                                                forecastWeatherCondition);
                forecastWeatherList.add(forecastWeather);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return forecastWeatherList;
    }

    @Override
    protected String doInBackground(String[]... strings) {
        return null;
    }
}
