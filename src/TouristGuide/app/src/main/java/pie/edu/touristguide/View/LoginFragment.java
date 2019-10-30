package pie.edu.touristguide.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pie.edu.touristguide.Controller.Database.Login.LoginController;
import pie.edu.touristguide.R;

/**
 * @author Sebastien El-Hamaoui
 * Login fragment allowing users to login to the application.
 */

public class LoginFragment extends Fragment {
    //Variables declaration.
    private LoginController loginController;
    private EditText username, password;
    private TextView invalidInput;
    View rootView;

    public LoginFragment() {
        //Empty Constructor.
    }

    //Returns the view with all the UI elements and fetches the credentials from the database. (WIP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_layout, container, false);

        //Declaration of the views.
        username = rootView.findViewById(R.id.input_username);
        password = rootView.findViewById(R.id.input_password);
        invalidInput = rootView.findViewById(R.id.input_invalid);
        Button btnLogin = rootView.findViewById(R.id.btnLogin);

        //Calls the validation method to evaluate the input.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(username.getText().toString(), password.getText().toString());
            }
        });

        return rootView;
    }

    //Returns the user to the home fragment if the input is correct or displays an error message.
    private void validation(String username, String password) {
        if((username.equals("admin")) && (password.equals("admin"))) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, new HomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else
            invalidInput.setText("Invalid Username & Password combination!");
    }
}
