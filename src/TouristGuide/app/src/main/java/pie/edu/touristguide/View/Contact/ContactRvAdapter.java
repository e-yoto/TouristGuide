package pie.edu.touristguide.View.Contact;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pie.edu.touristguide.R;
import pie.edu.touristguide.Model.Contact;

/**
 * @author BoTao Yu
 * populate views and bind data to them.
 *
 * ContactRvAdapter stand for ContactRecyclerViewAdapter
 */
public class ContactRvAdapter extends RecyclerView.Adapter<ContactRvAdapter.ViewHolder> implements View.OnClickListener {

    private List<Contact> contacts;
    private Context mContext;
    private LayoutInflater mLayoutInflator;
    private SwipeLayout swipeLayout;

    /**
     * initialize ContactRvAdapter
     * @param contacts is data used to populate RecyclerView
     * @param mContext that this adapter is used in.
     */
    public ContactRvAdapter(List<Contact> contacts, Context mContext) {
        this.contacts = contacts;
        this.mContext = mContext;
        this.mLayoutInflator = LayoutInflater.from(mContext);
    }

    /**
     * inflate contact_row_layout when onCreateViewHolder is called.
     * @param viewGroup that contact_row_layout belong to.
     * @param i is the view type of the new view
     * @return the ViewHolder that is created using the view.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = mLayoutInflator.inflate(R.layout.contact_row_layout, viewGroup, false);
        swipeLayout = (SwipeLayout) view.findViewById(R.id.contact_swipe_layout);
        swipeLayout.setOnClickListener(this);
        //The animation that is set when the layout is swipe (Little difference if set to LayDown)
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //bottomWrapper contains the BottomViews to show when the layout is swipe
        LinearLayout bottomWrapper = view.findViewById(R.id.bottom_views_wrapper);

        //When swipeLayout is drag from the right show BottomWrapper
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, bottomWrapper);
        swipeLayout.addSwipeListener(new ContactSwipeListener(mContext));
        return new ViewHolder(view);
    }

    /**
     * bind data to views in the view holder
     * @param viewHolder that contains the views.
     * @param position of the ViewHolder in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Contact contact = contacts.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getBitMapBytes(), 0, contact.getBitMapBytes().length);
        viewHolder.contactPicture.setImageBitmap(bitmap);

        viewHolder.contactName.setText(contact.getName());
        String timeAndLocation = getAdjustedTime(contact.getRawOffSet());
        timeAndLocation += " at " + contact.getLocation();
        viewHolder.contactTimeAndLocation.setText(timeAndLocation);
        viewHolder.numberTv.setText(String.valueOf(contact.getPhoneNumber()));

    }

    private String getAdjustedTime(long rawOffSet){
        //current time stamp
        long timeStamp = System.currentTimeMillis()/1000;
        //adjust time stamp to GMT
        //TODO: this is hardcoded, it will not work for devices that are not in Montreal.
        timeStamp += 86400;
        long adjustedTimeStamp = timeStamp + rawOffSet;
        Log.d("Current Timestamp", String.valueOf(timeStamp));
        Log.d("raw off set", String.valueOf(rawOffSet));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String adjustedTimeStr = simpleDateFormat.format(new Date(adjustedTimeStamp * 1000));
        Log.d("Testing:Time", adjustedTimeStr);
        return adjustedTimeStr;
    }
    /**
     * return the size that the adapter should be.
     * @return the size that the adapter should be.
     */
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onClick(View v) {
        if(swipeLayout.getOpenStatus() == SwipeLayout.Status.Close){
            TextView contactNumberTv = v.findViewById(R.id.tv_contact_number);
            String phoneNumber = contactNumberTv.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            mContext.startActivity(intent);
        }
    }

    /**
     * ViewHolder that holds the views of each list item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView contactPicture;
        TextView contactName;
        TextView contactTimeAndLocation;
        TextView numberTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactPicture = itemView.findViewById(R.id.iv_contact_picture);
            contactName = itemView.findViewById(R.id.tv_contact_name);
            contactTimeAndLocation = itemView.findViewById(R.id.tv_contact_time_and_location);
            numberTv = itemView.findViewById(R.id.tv_contact_number);
        }
    }
}
