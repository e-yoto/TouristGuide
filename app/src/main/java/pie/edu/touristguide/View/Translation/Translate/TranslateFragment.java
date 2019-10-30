package pie.edu.touristguide.View.Translation.Translate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pie.edu.touristguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends Fragment implements View.OnClickListener {
    public TextView outputText;
    Spinner dropdown;

    public TranslateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.translate_layout, container, false);

        dropdown =  rootView.findViewById(R.id.spinner_lang);

        //create a list of items for the spinner.
        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_speak:
                if (outputText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please translate text first.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
