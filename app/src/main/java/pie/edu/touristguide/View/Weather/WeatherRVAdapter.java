package pie.edu.touristguide.View.Weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import pie.edu.touristguide.Model.Weather;
import pie.edu.touristguide.R;

import static pie.edu.touristguide.View.Weather.WeatherFragment.weatherConditionList;

/**
 * @author Sebastien El-Hamaoui
 * RecyclerView Adapter for the 7 days Weather forecast.
 */

public class WeatherRVAdapter extends
        RecyclerView.Adapter<WeatherRVAdapter.ViewHolder>  {
    //Variables declaration.
    private ArrayList<Weather> mForecastWeather;
    private LayoutInflater mInflater;
    private Context context;

    //Constructor to set the List and inflater.
    public WeatherRVAdapter(ArrayList<Weather> forecastWeather, Context context) {
        this.mForecastWeather = forecastWeather;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //Inflates the forecast card and returns the ViewHolder.
    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View weatherForecastView = mInflater.inflate(R.layout.weather_row_layout, viewGroup,
                                false);
        ViewHolder viewHolder = new ViewHolder(weatherForecastView);
        return viewHolder;
    }

    //Sets the view's content with the given data.
    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder viewHolder, int i) {
        Weather weatherForecast = mForecastWeather.get(i);

        viewHolder.weatherForecastDate.setText(weatherForecast.getWeatherDate());
        viewHolder.weatherForecastTemperature.setText(weatherForecast.getWeatherTemperature() + "°");

        int resId = context.getResources().getIdentifier(weatherConditionList.get
                (weatherForecast.getWeatherCondition()), "drawable",
                context.getPackageName());
        viewHolder.weatherForecastImage.setImageResource(resId);
    }

    //Returns the amount of items in the list.
    @Override
    public int getItemCount() {
        return mForecastWeather.size();
    }

    //ViewHolder for the recycler to define the forecast card's views.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView weatherForecastDate;
        TextView weatherForecastTemperature;
        ImageView weatherForecastImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherForecastDate = itemView.findViewById(R.id.weather_forecast_date);
            weatherForecastTemperature = itemView.findViewById(R.id.weather_forecast_temperature);
            weatherForecastImage = itemView.findViewById(R.id.weather_forecast_image);
        }
    }
}
