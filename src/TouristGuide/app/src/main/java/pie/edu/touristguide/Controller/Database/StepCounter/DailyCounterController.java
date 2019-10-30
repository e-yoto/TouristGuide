package pie.edu.touristguide.Controller.Database.StepCounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import pie.edu.touristguide.Model.StepCounter.DailyCounter;


public class DailyCounterController {
    private StepCounterDBAccessController databaseAccessController;
    private SQLiteDatabase sqLiteDatabase;
    private final String DB_TABLE_NAME = "dailycounter";
    private final String TAG = this.getClass().getSimpleName();

    public DailyCounterController(Context context){
        this.databaseAccessController = StepCounterDBAccessController.getInstance(context);
    }

    public DailyCounter getDailyCounter(){
        sqLiteDatabase = this.databaseAccessController.openDatabase();
        DailyCounter dailyCounter = null;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            int steps = cursor.getInt(cursor.getColumnIndex("steps"));
            int goal = cursor.getInt(cursor.getColumnIndex("goal"));
            int calories = cursor.getInt(cursor.getColumnIndex("calories"));
            int stepsLeft = cursor.getInt(cursor.getColumnIndex("steps_left"));
            Log.d("getDailyCounter",  ""+steps);
            dailyCounter = new DailyCounter(steps, goal, calories, stepsLeft);
            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            this.databaseAccessController.closeDatabase();
        }
        return dailyCounter;
    }

    public void createDailyCounter(){
        sqLiteDatabase = this.databaseAccessController.openDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM " + "'"+DB_TABLE_NAME+"'", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0){
            Log.d(TAG, "Don't create new daily step counter, data has saves");
            this.databaseAccessController.closeDatabase();
            return;
        }
        cursor.close();

        sqLiteDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("steps", 0);
            contentValues.put("goal", 100);
            contentValues.put("calories", 0);
            contentValues.put("steps_left", 100);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, contentValues);
            Log.d(TAG, "Daily table created");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqLiteDatabase.endTransaction();
            this.databaseAccessController.closeDatabase();
        }
    }

    public void updateDailyCounter(DailyCounter dailyCounter){
        sqLiteDatabase = this.databaseAccessController.openDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("steps", dailyCounter.getSteps());
            contentValues.put("goal", dailyCounter.getGoal());
            contentValues.put("calories", dailyCounter.getCalories());
            contentValues.put("steps_left", dailyCounter.getStepsLeft());
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
