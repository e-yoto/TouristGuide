package pie.edu.touristguide.View.Contact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.io.ByteArrayOutputStream;

import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

/**
 * @author BoTao Yu
 * logics for buttons that are revealed when SwipeLayout is swiped.
 */
public class BottomViewClickListener implements View.OnClickListener {

    SwipeLayout layout;
    Context context;

    public BottomViewClickListener(SwipeLayout layout, Context context) {
        this.layout = layout;
        this.context = context;
    }

    @Override
    public void onClick(View v) {

        //initialize TextViews
        TextView nameTv = layout.findViewById(R.id.tv_contact_name);
        TextView timeAndLocationTv = layout.findViewById(R.id.tv_contact_time_and_location);
        TextView numberTv = layout.findViewById(R.id.tv_contact_number);
        ImageView profileIv = layout.findViewById(R.id.iv_contact_picture);

        //get data from TextViews
        String name = nameTv.getText().toString();
        String timeAndLocation = timeAndLocationTv.getText().toString();
        //example: timeAndLocation is 01:30 AM at Vanier College, remove "01:30 AM at"
        String location = timeAndLocation.substring(12);
        String number = numberTv.getText().toString();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) profileIv.getDrawable();
        byte[] bitMapBytes = this.bitMapToBytes(bitmapDrawable.getBitmap());
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

        switch (v.getId()){
            case R.id.iv_edit_bottom_view:
                //store data in bundle
                bundle.putString(context.getString(R.string.contact_name_key), name);
                bundle.putString(context.getString(R.string.contact_location_key), location);
                bundle.putString(context.getString(R.string.contact_number_key), number);
                bundle.putByteArray(context.getString(R.string.contact_bitmap_bytes_key), bitMapBytes);
                //replace fragment
                replaceFragment(fragmentManager, bundle);
                break;
            case R.id.iv_delete_bottom_view:
                Log.d("oldPhoneNumber", String.valueOf(number));
                Fragment contactFragment = fragmentManager.findFragmentById(R.id.fragment_holder);
                ((CRUDListener)contactFragment).deleteContact(Integer.parseInt(number));
                break;
        }
    }

    /**
     * replace fragment_holder with AddFragment that have the bundle as arguments.
     * @param fragmentManager to be use to replace fragment in fragment_holder
     * @param bundle to send to AddFragment as arguments
     */
    private void replaceFragment(FragmentManager fragmentManager, Bundle bundle){
        AddFragment addFragment = new AddFragment();
        addFragment.setArguments(bundle);
        FragmentNavigationUtil.replaceFragment(((Activity)context), addFragment);
    }

    private byte[] bitMapToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
