package pie.edu.touristguide.Controller.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author BoTao Yu
 */
public class DBAccessController {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static DBAccessController instance;

    private DBAccessController(SQLiteOpenHelper openHelper){
        this.openHelper = openHelper;
    }

    public static synchronized DBAccessController getInstance(SQLiteOpenHelper openHelper){
        if(instance == null){
            instance = new DBAccessController(openHelper);
        }
        return instance;
    }

    public SQLiteDatabase openDatabase(){
        this.sqLiteDatabase = openHelper.getWritableDatabase();
        return sqLiteDatabase;
    }
    public void closeDatabase(){
        if(sqLiteDatabase != null){
            this.sqLiteDatabase.close();
        }

    }

}
