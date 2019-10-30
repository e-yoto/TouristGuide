package pie.edu.touristguide.Controller.Database.Memories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoriesDBAccessController {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static MemoriesDBAccessController instance;

    private MemoriesDBAccessController(Context context){
        this.openHelper = new MemoriesDBOpenHelper(context);
    }

    public static synchronized MemoriesDBAccessController getInstance(Context context){
        if(instance == null){
            instance = new MemoriesDBAccessController(context);
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
