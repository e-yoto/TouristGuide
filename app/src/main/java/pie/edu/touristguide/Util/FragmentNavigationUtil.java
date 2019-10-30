package pie.edu.touristguide.Util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import pie.edu.touristguide.R;

/**
 * @author Botao Yu
 * Animation utilities for Fragment transistion
 */
public class FragmentNavigationUtil {

    //TODO: Need to Improve replaceFragment. Because Saving every previous fragments creates BAD user experience.
    /**
     * Replace fragment_holder with the specified fragment.
     * Add fragment to back stack in order to navigate between fragments with animations.
     *
     * Reference: <a href="https://stackoverflow.com/questions/21026409/fragment-transaction-animation-slide-in-and-slide-out"/>
     * @param fragment that has just been replaced by new fragment.
     */
    public static void replaceFragment(Activity activity, Fragment fragment){
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        FragmentTransaction transaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
