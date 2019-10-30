package pie.edu.touristguide.Controller.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BagDBAccessController {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static BagDBAccessController instance;

    private BagDBAccessController(Context context){
        this.openHelper = new BagDBOpenHelper(context);
    }

    public static synchronized BagDBAccessController getInstance(Context context){
        if(instance == null){
            instance = new BagDBAccessController(context);
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
