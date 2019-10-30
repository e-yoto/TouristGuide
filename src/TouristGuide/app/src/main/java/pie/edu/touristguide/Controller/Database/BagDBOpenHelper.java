package pie.edu.touristguide.Controller.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class BagDBOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "bag.db";
    private static final int DATABASE_VERSION = 1;

    public BagDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
