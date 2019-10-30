package pie.edu.touristguide.View.Reminder;

import android.widget.LinearLayout;

import java.io.Serializable;

/**
 * @author BoTao Yu
 * Serialize reminder_add layout in order to pass it to TimePickerFragment.
 *
 */
public class SerializableRootView implements Serializable {

    LinearLayout rootLayout;

    public SerializableRootView(LinearLayout rootLayout) {
        this.rootLayout = rootLayout;
    }

    public LinearLayout getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(LinearLayout rootLayout) {
        this.rootLayout = rootLayout;
    }


}
