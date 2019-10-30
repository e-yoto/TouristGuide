package pie.edu.touristguide.View.Reminder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.R;

/**
 * @author BoTao Yu
 *
 * Weather RecyclerView Adapter
 * When one of the weather icon is clicked, it will have a rectangle border to show that it is being selected.
 */
public class WeatherRvAdapter extends RecyclerView.Adapter<WeatherViewHolder>{

    private static final String TAG = WeatherRvAdapter.class.getSimpleName();
    private List<Integer> weatherIconIds = new ArrayList<>();
    private ImageView selectedIv = null;

    public ImageView getSelectedIv() {
        return selectedIv;
    }

    /**
     * Initialize WeatherRvAdapter will the weather icons.
     */
    public WeatherRvAdapter() {
        weatherIconIds.add(R.drawable.ic_w_clear_sky);
        weatherIconIds.add(R.drawable.ic_w_few_clouds);
        weatherIconIds.add(R.drawable.ic_w_scattered_clouds);
        weatherIconIds.add(R.drawable.ic_w_broken_clouds);
        weatherIconIds.add(R.drawable.ic_w_shower_rain);
        weatherIconIds.add(R.drawable.ic_w_rain);
        weatherIconIds.add(R.drawable.ic_w_thunderstorm);
        weatherIconIds.add(R.drawable.ic_w_snow);
        weatherIconIds.add(R.drawable.ic_w_mist);
    }

    /**
     * inflate reminder_weather_rv_row layout
     * @param viewGroup that the layout belong to.
     * @param i is the view type of the new view
     * @return the ViewHolder that is created using the view.
     */
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reminder_weather_rv_row, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    /**
     * bind data to views in the view holder
     * @param weatherViewHolder that contains the views.
     * @param i is the position of the ViewHolder in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        weatherViewHolder.weatherIv.setImageResource(weatherIconIds.get(i));
        weatherViewHolder.weatherIv.setTag(weatherIconIds.get(i));

        weatherViewHolder.weatherIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedIv != null){
                    //deselect the weather ImageView
                    selectedIv.setBackground(null);
                    selectedIv.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    selectedIv.setImageTintList(ColorStateList.valueOf(Color.BLACK));
                }


                selectedIv = (ImageView) v;
                Log.d(TAG, "iconId: " + selectedIv.getTag());

                ((ImageView) v).setImageTintList(ColorStateList.valueOf(Color.WHITE));
                v.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                v.setBackground(v.getContext().getDrawable(R.drawable.rectangle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return weatherIconIds.size();
    }
}
