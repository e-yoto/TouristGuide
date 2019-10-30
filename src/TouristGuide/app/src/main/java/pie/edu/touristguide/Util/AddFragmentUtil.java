package pie.edu.touristguide.Util;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pie.edu.touristguide.R;

/**
 * @author Botao Yu
 * Utilities for AddFragment
 */
public class AddFragmentUtil {

    /**
     * Set tool bar to save_data_toolbar when the application is AddFragment.
     * Set tool bar to main_toolbar when the application exit out of AddFragment.
     * To set a tool bar, the visibility of that tool bar will be set to VISIBLE and the
     * visibility of the other will be set to GONE.
     * @param toolbarId of the toolbar
     * @param activity that the toolbar is in
     *
     */
    public static void setToolBar(int toolbarId, Activity activity) {
        DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
        switch (toolbarId) {
            case R.id.main_toolbar:
                drawerLayout.findViewById(R.id.main_toolbar).setVisibility(View.VISIBLE);
                drawerLayout.findViewById(R.id.save_data_toolbar).setVisibility(View.GONE);
                break;
            case R.id.save_data_toolbar:
                drawerLayout.findViewById(R.id.main_toolbar).setVisibility(View.GONE);
                drawerLayout.findViewById(R.id.save_data_toolbar).setVisibility(View.VISIBLE);
                break;
        }
    }
}
