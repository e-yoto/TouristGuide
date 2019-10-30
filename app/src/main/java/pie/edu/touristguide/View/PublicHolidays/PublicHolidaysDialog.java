package pie.edu.touristguide.View.PublicHolidays;

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
 * Takes the user-input for the country name to call the API.
 */

public class PublicHolidaysDialog extends AppCompatDialogFragment {
    //Variables declaration.
    EditText inputCountryCode;
    public static String countryCode = "CA";

    //Builds the Dialog Fragment and accepts the country name as user-input.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.public_holidays_dialog_layout, null);

        inputCountryCode = view.findViewById(R.id.input_country_code);

        //Actions to perform depending on the chosen UI element.
        builder.setView(view).setTitle("Search Country Holidays")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Closes keyboard and updates RecyclerView.
                        UIUtil.hideKeyboard(getActivity());
                        PublicHolidaysFragment.adapter.notifyDataSetChanged();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countryCode = inputCountryCode.getText().toString();
                        //Closes keyboard, updates RecyclerView and PublicHolidaysFragment.
                        UIUtil.hideKeyboard(getActivity());
                        PublicHolidaysFragment.adapter.notifyDataSetChanged();
                        FragmentNavigationUtil.replaceFragment(getActivity(), new PublicHolidaysFragment());
                    }
                });

        return builder.create();
    }
}
