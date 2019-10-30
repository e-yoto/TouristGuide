package pie.edu.touristguide.Controller.Database.Login;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * @author Sebastien El-Hamaoui
 * Login Open Helper declaring the database name and version.
 */

public class LoginDBOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "credentials.db";
    private static final int DATABASE_VERSION = 1;

    public LoginDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
