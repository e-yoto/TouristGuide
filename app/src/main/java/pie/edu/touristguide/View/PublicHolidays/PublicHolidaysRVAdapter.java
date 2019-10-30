package pie.edu.touristguide.View.PublicHolidays;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import pie.edu.touristguide.Model.PublicHoliday;
import pie.edu.touristguide.R;

/**
 * @author Sebastien El-Hamaoui
 * RecyclerView Adapter for the PublicHoliday objects.
 */

public class PublicHolidaysRVAdapter extends
        RecyclerView.Adapter<PublicHolidaysRVAdapter.ViewHolder>  {
    //Variables declaration.
    private ArrayList<PublicHoliday> mPublicHolidays;
    private LayoutInflater mInflater;

    //Constructor to set the list and inflater.
    public PublicHolidaysRVAdapter(ArrayList<PublicHoliday> publicHolidays, Context context) {
        this.mPublicHolidays = publicHolidays;
        this.mInflater = LayoutInflater.from(context);
    }

    //Inflates the holiday card and returns the ViewHolder.
    @NonNull
    @Override
    public PublicHolidaysRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View publicHolidaysView = mInflater.inflate(R.layout.public_holidays_rv_layout,
                                    viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(publicHolidaysView);
        return viewHolder;
    }

    //Sets the view's content with the given data.
    @Override
    public void onBindViewHolder(@NonNull PublicHolidaysRVAdapter.ViewHolder viewHolder, int i) {
        PublicHoliday publicHolidays = mPublicHolidays.get(i);

        viewHolder.holidayTitle.setText(publicHolidays.getHolidayName());
        viewHolder.holidayDate.setText(publicHolidays.getHolidayDate());

        //Randomly selecting between 4 sample images for holidays.
        int[] publicHolidayImages = {R.drawable.public_holidays_christmas,
                                    R.drawable.public_holidays_easter,
                                    R.drawable.public_holidays_halloween,
                                    R.drawable.public_holidays_newyear};
        Random random = new Random();
        viewHolder.holidayImage.setImageResource
                (publicHolidayImages[random.nextInt(publicHolidayImages.length)]);
    }

    //Returns the amount of items in the list.
    @Override
    public int getItemCount() {
        return mPublicHolidays.size();
    }

    //ViewHolder for the recycler to define the holiday card's views.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView holidayTitle;
        TextView holidayDate;
        ImageView holidayImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            holidayTitle = itemView.findViewById(R.id.holiday_name);
            holidayDate = itemView.findViewById(R.id.holiday_date);
            holidayImage = itemView.findViewById(R.id.holiday_image);
        }
    }
}
