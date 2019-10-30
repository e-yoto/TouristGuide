package pie.edu.touristguide.Controller.Database.StepCounter;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class StepCounterDBOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "stepcounter.db";
    private static final int DATABASE_VERSION = 1;

    public StepCounterDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
