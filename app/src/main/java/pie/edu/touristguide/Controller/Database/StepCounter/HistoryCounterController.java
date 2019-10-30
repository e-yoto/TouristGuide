package pie.edu.touristguide.Controller.Database.StepCounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import pie.edu.touristguide.Model.StepCounter.HistoryCounter;

public class HistoryCounterController {
    private StepCounterDBAccessController databaseAccessController;
    private SQLiteDatabase sqLiteDatabase;
    private final String DB_TABLE_NAME = "historycounter";
    private final String TAG = this.getClass().getSimpleName();

    public HistoryCounterController(Context context){
        this.databaseAccessController = StepCounterDBAccessController.getInstance(context);
    }

    public HistoryCounter getHistoryCounter(){
        sqLiteDatabase = this.databaseAccessController.openDatabase();
        HistoryCounter historyCounter = null;

        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            int steps = cursor.getInt(cursor.getColumnIndex("steps"));
            int kilometers = cursor.getInt(cursor.getColumnIndex("kilometers"));
            int floor = cursor.getInt(cursor.getColumnIndex("floors"));
            int miles = cursor.getInt(cursor.getColumnIndex("miles"));
            Log.d("getHistoryCounter", ""+steps);
            historyCounter = new HistoryCounter(steps, kilometers, floor, miles);
            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.databaseAccessController.closeDatabase();
        }

        return historyCounter;
    }

    public void createHistoryCounter(){
        sqLiteDatabase = this.databaseAccessController.openDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM " + "'"+DB_TABLE_NAME+"'", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0){
            Log.d(TAG, "Don't create new history step counter, data has saves");
            this.databaseAccessController.closeDatabase();
            return;
        }
        cursor.close();

        sqLiteDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("steps", 0);
            contentValues.put("kilometers", 0);
            contentValues.put("floors", 0);
            contentValues.put("miles", 0);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, contentValues);
            Log.d(TAG, "History table created");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqLiteDatabase.endTransaction();
            this.databaseAccessController.closeDatabase();
        }
    }

    public void updateHistoryCounter(HistoryCounter historyCounter){
        sqLiteDatabase = this.databaseAccessController.openDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("steps", historyCounter.getSteps());
            contentValues.put("kilometers", historyCounter.getKilometers());
            contentValues.put("floors", historyCounter.getFloors());
            contentValues.put("miles", historyCounter.getMiles());
            String whereClause = "rowid = 1";
            sqLiteDatabase.update(DB_TABLE_NAME, contentValues, whereClause, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.databaseAccessController.closeDatabase();
        }
    }
}
