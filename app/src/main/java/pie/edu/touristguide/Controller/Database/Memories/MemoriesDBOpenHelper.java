package pie.edu.touristguide.Controller.Database.Memories;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MemoriesDBOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "memories.db";
    private static final int DATABASE_VERSION = 1;

    public MemoriesDBOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
