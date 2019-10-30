package pie.edu.touristguide.View.Weather;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import pie.edu.touristguide.Controller.APICall.GetWeatherDataTask;
import pie.edu.touristguide.Model.Weather;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

/**
 * @author Sebastien El-Hamaoui
 * Displays the current weather and the forecast for 7 days in a RecyclerView.
 */

public class WeatherFragment extends Fragment {
    //Variables Declaration.
    public static WeatherRVAdapter adapter;
    private static ArrayList<Weather> forecastWeatherList;
    static HashMap<String, String> weatherConditionList = new HashMap<>();
    View rootView;

    public WeatherFragment() {
        //Empty Constructor.
    }

    //Returns the view with all the UI elements and calls the API to fetch the data.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.weather_layout, container, false);

        //HashMap with the appropriate weather conditions and drawable image pairs.
        weatherConditionList.put("Thunderstorm", "ic_w_thunderstorm");
        weatherConditionList.put("Drizzle", "ic_w_rain");
        weatherConditionList.put("Rain", "ic_w_rain");
        weatherConditionList.put("Snow", "ic_w_snow");
        weatherConditionList.put("Clouds", "ic_w_cloudy");
        weatherConditionList.put("Smoke", "ic_w_mist");
        weatherConditionList.put("Haze", "ic_w_mist");
        weatherConditionList.put("Dust", "ic_w_mist");
        weatherConditionList.put("Fog", "ic_w_mist");
        weatherConditionList.put("Sand", "ic_w_mist");
        weatherConditionList.put("Ash", "ic_w_mist");
        weatherConditionList.put("Squall", "ic_w_mist");
        weatherConditionList.put("Tornado", "ic_w_mist");
        weatherConditionList.put("Clear", "ic_w_clear_sky");
        weatherConditionList.put("Mist", "ic_w_mist");

        //Declaring the views.
        TextView weatherCurrentLocation = rootView.findViewById(R.id.weather_region_location);
        TextView weatherCurrentDate = rootView.findViewById(R.id.weather_current_date);
        TextView weatherCurrentTemperature = rootView.findViewById(R.id.weather_current_temperature);
        TextView weatherCurrentCondition = rootView.findViewById(R.id.weather_current_condition);
        TextView weatherCurrentHumidity = rootView.findViewById(R.id.weather_current_humidity);
        TextView weatherCurrentWind = rootView.findViewById(R.id.weather_current_wind);
        ImageView weatherCurrentTemperatureImage = rootView.findViewById(R.id.weather_current_image);

        //Floating action button will be used to open the WeatherDialog fragment.
        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.fab_set_city);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //Weather API call for the current day's data.
        try {
            //Used to ignore the android restrictions on internet access.
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Weather currentWeather = GetWeatherDataTask.getCurrentWeatherObject();

                //Setting the current date's info TextViews.
                weatherCurrentLocation.setText(currentWeather.getWeatherLocation() + ", "
                                                + currentWeather.getWeatherCountryCode());
                weatherCurrentCondition.setText(currentWeather.getWeatherCondition());
                weatherCurrentTemperature.setText(currentWeather.getWeatherTemperature() + "°");
                weatherCurrentDate.setText(currentWeather.getWeatherDate());
                weatherCurrentHumidity.setText(currentWeather.getWeatherHumidity() + "%");
                weatherCurrentWind.setText(currentWeather.getWeatherWind() + "km/h");
                int resId = getResources().getIdentifier(weatherConditionList.get
                        (currentWeather.getWeatherCondition()), "drawable",
                        getActivity().getPackageName());
                weatherCurrentTemperatureImage.setImageResource(resId);

                forecastWeatherList = GetWeatherDataTask.getForecastWeatherObject();
            }

            //Recycler view and defining the horizontal layout.
            RecyclerView recyclerView = rootView.findViewById(R.id.rv_weather);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                    false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new WeatherRVAdapter(forecastWeatherList, getActivity());
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //Soft keyboard listener that will refresh the recycler view when the keyboard closes.
        KeyboardVisibilityEvent.setEventListener(getActivity(), new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(!isOpen) {
                    WeatherFragment.adapter.notifyDataSetChanged(); //Update RecyclerView.
                }
            }
        });

        return rootView;
    }

    //Opens the WeatherDialog fragment to receive the user-input.
    public void openDialog() {
        WeatherDialog weatherDialog = new WeatherDialog();
        weatherDialog.show(getFragmentManager(), "Search City Weather");
    }
}
