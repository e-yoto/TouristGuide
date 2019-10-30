package pie.edu.touristguide.View.Bag;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Emmanuel
 * Adapter that will allow tabs to navigate between carry-on and check-in inventory
 */
public class ItemPageAdapter extends FragmentStatePagerAdapter{

    public ItemPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment itemFragment = null;
//        switch(i){
//            case 0:
//                itemFragment = new ItemFragment();
//                ((ItemFragment) itemFragment).setPosition(i);
//                break;
//            case 1:
//                itemFragment = new ItemFragment();
//                ((ItemFragment) itemFragment).setPosition(i);
//                break;
//        }
//        return itemFragment;

        ItemFragment fragment = new ItemFragment();
//        fragment.setPosition(i);
        return  fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Carry on";
            case 1:
                return "Check in";
            default:
                return null;
        }
    }
}
