package pie.edu.touristguide.View;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.GetRecommendationTask;
import pie.edu.touristguide.Controller.Database.Reminder.ReminderController;
import pie.edu.touristguide.Model.Reminder;
import pie.edu.touristguide.R;
import pie.edu.touristguide.View.Reminder.ReminderRvAdapter;
import pie.edu.touristguide.View.Weather.WeatherFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View rootView;

    ReminderController reminderController;
    List<Reminder> reminders;
    RecyclerView reminderRv;
    ReminderRvAdapter reminderRvAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.home_layout, container, false);




        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout homeLinearLayout = view.findViewById(R.id.linear_layout_home);

        TextView boredTV = (TextView) view.findViewById(R.id.tipText);
        String recommendation = "";
        try {
            recommendation = (new GetRecommendationTask()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boredTV.setText(recommendation);

        reminderController = new ReminderController(getContext());
        reminders = reminderController.getAllReminders();
        if(reminders.isEmpty()){
            reminders.add(new Reminder("Sample Reminder", "10:00am", "11:00am",
                    R.drawable.ic_w_clear_sky, "Canada", 100, 100));
        }
        FrameLayout reminderLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.reminder_layout, null, false);
        reminderRv = reminderLayout.findViewById(R.id.rv_reminders);
        reminderLayout.removeView(reminderRv);
        reminderRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        reminderRvAdapter = new ReminderRvAdapter(reminders, getContext(), 1);
        reminderRv.setAdapter(reminderRvAdapter);
        homeLinearLayout.addView(reminderRv);
        registerForContextMenu(reminderRv);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        reminderRvAdapter.notifyDataSetChanged();
        return true;
    }
}
