package pie.edu.touristguide.View.Contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import pie.edu.touristguide.R;

/**
 * @author BoTao Yu
 * set up buttons when SwipeLayout is swipe
 */
public class ContactSwipeListener implements SwipeLayout.SwipeListener {
    private Context context;
    private boolean disableClick;

    public ContactSwipeListener(Context context) {
        this.context = context;
    }

    @Override
    public void onStartOpen(SwipeLayout layout) {

        //view setup for the BottomViews
        ImageView editIv = layout.findViewById(R.id.iv_edit_bottom_view);
        editIv.setOnClickListener(new BottomViewClickListener(layout, context));
        ImageView deleteIv = layout.findViewById(R.id.iv_delete_bottom_view);
        deleteIv.setOnClickListener(new BottomViewClickListener(layout, context));
    }


    @Override
    public void onOpen(SwipeLayout layout) {
    }

    @Override
    public void onStartClose(SwipeLayout layout) {

    }

    @Override
    public void onClose(SwipeLayout layout) {

    }

    @Override
    public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

    }

    @Override
    public void onHandRelease(final SwipeLayout layout, float xvel, float yvel) {

    }
}
