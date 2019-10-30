package pie.edu.touristguide.Controller.Database.Login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Sebastien El-Hamaoui
 * Login Access Controller to instantiate the Database as well as opening and closing it.
 */

public class LoginDBAccessController {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static LoginDBAccessController instance;

    private LoginDBAccessController(Context context){
        this.openHelper = new LoginDBOpenHelper(context);
    }

    public static synchronized LoginDBAccessController getInstance(Context context){
        if(instance == null){
            instance = new LoginDBAccessController(context);
        }
        return instance;
    }

    public SQLiteDatabase openDatabase() {
        this.database = openHelper.getWritableDatabase();
        return this.database;
    }

    public void closeDatabase() {
        if (database != null) {
            this.database.close();
        }
    }
}
