package pie.edu.touristguide.Controller.Database.Reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author BoTao Yu
 *
 */
public class ReminderDBAcessController {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ReminderDBAcessController instance;

    private ReminderDBAcessController(Context context){
        this.openHelper = new ReminderDBOpenHelper(context);
    }

    public static synchronized ReminderDBAcessController getInstance(Context context){
        if(instance == null){
            instance = new ReminderDBAcessController(context);
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
