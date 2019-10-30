package pie.edu.touristguide.Util;

import android.content.Context;
import pie.edu.touristguide.R;

import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.GetReminderDataTask;

public class WeatherIconUtils {

    public static int getWeatherIconsWith(String lat, String lng){
        String[] latAndLng = {lat, lng};
        try {
            String weatherIconsApiName = new GetReminderDataTask().execute(latAndLng).get();
            return convertIconName(weatherIconsApiName);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 404;
    }

    private static int convertIconName(String apiIconName){
        int iconId = 0;
        switch(apiIconName){
            case "01d":
            case "01n":
                iconId = R.drawable.ic_w_clear_sky;
                break;
            case "02d":
            case "02n":
                iconId = R.drawable.ic_w_few_clouds;
                break;
            case "03d":
            case "03n":
                iconId = R.drawable.ic_w_scattered_clouds;
                break;
            case "04d":
            case "04n":
                iconId = R.drawable.ic_w_broken_clouds;
                break;
            case "09d":
            case "09n":
                iconId = R.drawable.ic_w_shower_rain;
                break;
            case "10d":
            case "10n":
                iconId = R.drawable.ic_w_rain;
                break;
            case "11d":
            case "11n":
                iconId = R.drawable.ic_w_thunderstorm;
                break;
            case "13d":
            case "13n":
                iconId = R.drawable.ic_w_snow;
                break;
            case "50d":
            case "50n":
                iconId = R.drawable.ic_w_mist;
                break;
        }
        return iconId;
    }

}
