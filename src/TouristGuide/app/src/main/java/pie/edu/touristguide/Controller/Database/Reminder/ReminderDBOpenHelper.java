package pie.edu.touristguide.Controller.Database.Reminder;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * @author BoTao Yu
 */
public class ReminderDBOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "reminder.db";
    private static final int DATABASE_VERSION = 1;

    public ReminderDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
