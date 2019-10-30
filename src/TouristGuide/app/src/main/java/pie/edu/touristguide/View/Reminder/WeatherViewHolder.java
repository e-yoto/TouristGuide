package pie.edu.touristguide.View.Reminder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import pie.edu.touristguide.R;

/**
 * @author BoTao Yu
 */
public class WeatherViewHolder extends RecyclerView.ViewHolder {

    ImageView weatherIv;
    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        weatherIv = itemView.findViewById(R.id.iv_reminder_weather);
    }
}
