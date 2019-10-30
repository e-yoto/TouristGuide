package pie.edu.touristguide.View.Reminder;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.GetReminderDataTask;
import pie.edu.touristguide.Controller.Database.Reminder.ReminderController;
import pie.edu.touristguide.Model.Reminder;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;
import pie.edu.touristguide.View.MainActivity;

/**
 * @author Botao Yu
 * Display Reminders in a recyclerview.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    private List<Reminder> reminders;
    private ReminderRvAdapter adapter;
    private int position = 0;
    private RecyclerView reminderRv;

    private ReminderController reminderController;

    private Context context;

    public ReminderFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.reminder_layout, container, false);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        context = getContext();
        //view setup
        FloatingActionButton addFAB = view.findViewById(R.id.fab_add);
        addFAB.setOnClickListener(this);

        reminders = new ArrayList<>();
        reminderController = new ReminderController(getContext());
        reminders = reminderController.getAllReminders();
        reminderRv = view.findViewById(R.id.rv_reminders);
        reminderRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReminderRvAdapter(reminders, getContext(), reminders.size());
        reminderRv.setAdapter(adapter);


    }


    /**
     * Do different operations depending on the view's ID.
     * @param view that is clicked
     */
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.fab_add:
                FragmentNavigationUtil.replaceFragment(getActivity(), new AddFragment());
                break;
        }
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Reminder> newReminders = reminderController.getAllReminders();
        Menu menu = MainActivity.optionMenu;
        menu.findItem(R.id.menu_item_location).setIconTintList(ColorStateList.valueOf(Color.BLACK));
        menu.findItem(R.id.menu_item_weather).setIconTintList(ColorStateList.valueOf(Color.BLACK));
        menu.findItem(R.id.menu_item_time).setIconTintList(ColorStateList.valueOf(Color.BLACK));
        item.setIconTintList(ColorStateList.valueOf(Color.WHITE));
        //sort reminders
        switch (item.getItemId()){
            case R.id.menu_item_location:
                Log.d(TAG + ": onPageSelected", "Location");
                Collections.sort(newReminders, new Reminder.LocationComparator());
                break;
            case R.id.menu_item_weather:
                Log.d(TAG + ": onPageSelected", "Weather");
                Collections.sort(newReminders, new Reminder.WeatherComparator());
                break;
            case R.id.menu_item_time:
                Log.d(TAG + ": onPageSelected", "Time");
                Collections.sort(newReminders, new Reminder.TimeComparator());
                break;
        }

        ReminderRvAdapter reminderRvAdapter = new ReminderRvAdapter(newReminders, context, newReminders.size());
        reminderRv.setAdapter(reminderRvAdapter);
        reminderRv.invalidate();

        //Credit: https://stackoverflow.com/questions/11419141/fading-in-text-in-android-using-animationutils-loadanimation
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.enter_from_up);
        reminderRv.startAnimation(animation);


        Log.d(TAG, newReminders.toString());
        return true;
    }

}


