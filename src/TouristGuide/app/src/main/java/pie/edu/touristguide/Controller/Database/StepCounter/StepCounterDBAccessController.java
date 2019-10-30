package pie.edu.touristguide.Controller.Database.StepCounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StepCounterDBAccessController {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static StepCounterDBAccessController instance;

    private StepCounterDBAccessController(Context context){
        this.openHelper = new StepCounterDBOpenHelper(context);
    }

    public static synchronized StepCounterDBAccessController getInstance(Context context){
        if (instance == null)
            instance = new StepCounterDBAccessController(context);

        return instance;
    }

    public SQLiteDatabase openDatabase() {
        this.database = openHelper.getWritableDatabase();
        return database;
    }

    public void closeDatabase() {
        if (database != null) {
            this.database.close();
        }
    }
}
