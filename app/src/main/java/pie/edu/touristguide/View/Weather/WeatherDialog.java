package pie.edu.touristguide.View.Weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

/**
 * @author Sebastien El-Hamaoui
 * Takes the user-input for the city name to call the API.
 */

public class WeatherDialog extends AppCompatDialogFragment {
    //Variables declaration.
    EditText inputCityName;
    public static String cityName = "Montreal";

    //Builds the DialogFragment and accepts the city name as user-input.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.weather_dialog_layout, null);

        inputCityName = view.findViewById(R.id.input_city);

        //Actions to perform depending on the chosen UI element.
        builder.setView(view).setTitle("Search City Weather")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Closes keyboard and updates RecyclerView.
                        UIUtil.hideKeyboard(getActivity());
                        WeatherFragment.adapter.notifyDataSetChanged();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cityName = inputCityName.getText().toString();
                        //Closes keyboard, updates RecyclerView and WeatherFragment.
                        UIUtil.hideKeyboard(getActivity());
                        WeatherFragment.adapter.notifyDataSetChanged();
                        FragmentNavigationUtil.replaceFragment(getActivity(), new WeatherFragment());
                    }
                });

        return builder.create();
    }
}
