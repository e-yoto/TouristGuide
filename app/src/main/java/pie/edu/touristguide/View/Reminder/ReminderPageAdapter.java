package pie.edu.touristguide.View.Reminder;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * @author BoTao Yu
 * work with the tab bar.
 *
 * THIS IS NOT USED.
 *
 * reference: https://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
 */
public class ReminderPageAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = ReminderPageAdapter.class.getSimpleName();

    public ReminderPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ReminderFragment reminderFragment = new ReminderFragment();
        reminderFragment.setPosition(position);
        return reminderFragment;
    }



    @Override
    public int getCount() {
        return 3;
    }

    /**
     * change title depending on how reminder are sorted
     * @param position of the tab
     * @return title of the tab.
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Location";
            case 1:
                return "Weather";
            case 2:
                return "Time";
            default:
                return null;
        }
    }



}
