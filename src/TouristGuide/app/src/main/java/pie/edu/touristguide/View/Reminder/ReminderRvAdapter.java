package pie.edu.touristguide.View.Reminder;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pie.edu.touristguide.Model.Reminder;
import pie.edu.touristguide.R;

/**
 * @author BoTao Yu
 * populate views and bind data to them.
 *
 * ReminderRvAdapter stand for ReminderRecyclerviewAdapter
 */
public class ReminderRvAdapter extends RecyclerView.Adapter<ReminderRvAdapter.ViewHolder> {

    private List<Reminder> mReminders;
    private LayoutInflater mInflator;
    private Context context;
    private int itemCount;

    /**
     * initialize ReminderRvAdapter
     * @param reminders is data used to populate RecyclerView
     * @param context that this adapter is used in.
     */
    public ReminderRvAdapter(List<Reminder> reminders, Context context, int itemCount){
        this.mReminders = reminders;
        this.context = context;
        mInflator = LayoutInflater.from(context);
        this.itemCount = itemCount;
    }


    /**
     * inflate reminder_row_layout when onCreateViewHolder is called.
     * @param viewGroup that reminder_rv_row belong to.
     * @param i is the view type of the new view
     * @return the ViewHolder that is created using the view.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflator.inflate(R.layout.reminder_rv_row, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * bind data to views in the view holder
     * @param viewHolder that contains the views.
     * @param position of the ViewHolder in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Reminder reminder = mReminders.get(position);
        viewHolder.titleTv.setText(reminder.getTitle());
        viewHolder.timeTv.setText(reminder.getStartTime() + "-" + reminder.getEndTime());
        viewHolder.locationTv.setText(reminder.getLocation());
        viewHolder.weatherTv.setImageResource(reminder.getWeatherIconId());
    }

    /**
     * return the size that the adapter should be.
     * @return the size that the adapter should be.
     */
    @Override
    public int getItemCount() {
        return itemCount;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTv;
        TextView timeTv;
        TextView locationTv;
        ImageView weatherTv;

        /**
         * ViewHolder that holds the views of each list item.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            timeTv = itemView.findViewById(R.id.tv_start_time);
            locationTv = itemView.findViewById(R.id.tv_location);
            weatherTv = itemView.findViewById(R.id.iv_reminder_weather_row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Toast.makeText(context, mReminders.get(position).toString(), Toast.LENGTH_LONG).show();
        }
    }

}
