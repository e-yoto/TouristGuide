package pie.edu.touristguide.View.Fraud;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FraudPageAdapter extends FragmentStatePagerAdapter {

    public FraudPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fraudFragment = null;
        switch(i){
            case 0:
                fraudFragment = new FraudPreventionAddressFragment();
                ((FraudPreventionAddressFragment) fraudFragment).setPosition(i);
                break;
            case 1:
                fraudFragment = new FraudPreventionNumberFragment();
                ((FraudPreventionNumberFragment) fraudFragment).setPosition(i);
                break;
        }
        return fraudFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Address Verifier";
            case 1:
                return "Number Verifier";
        }
        return null;
    }
}
