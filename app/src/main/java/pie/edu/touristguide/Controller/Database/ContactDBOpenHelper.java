package pie.edu.touristguide.Controller.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * @author BoTao Yu
 */
public class ContactDBOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 1;

    public ContactDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
