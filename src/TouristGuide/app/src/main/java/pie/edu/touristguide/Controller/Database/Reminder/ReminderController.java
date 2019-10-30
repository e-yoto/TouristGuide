package pie.edu.touristguide.Controller.Database.Reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Controller.Database.DBAccessController;
import pie.edu.touristguide.Model.Reminder;

/**
 * @author BoTao Yu
 *
 * Perform CRUD operations on reminder
 */
public class ReminderController {

    private ReminderDBAcessController reminderDBAcessController;
    private SQLiteDatabase sqLiteDatabase;
    private final String DB_TABLE_NAME = "reminder";
    private final String TAG = this.getClass().getSimpleName();

    //private final String ID_COLUMN = "id";
    private final String TITLE_COLUMN = "title";
    private final String START_TIME_COLUMN = "start_time";
    private final String END_TIME_COLUMN = "end_time";
    private final String WEATHER_ICON_ID_COLUMN = "weather_icon_id";
    private final String LOCATION_COLUMN = "location";
    private final String LATITUDE_COLUMN = "latitude";
    private final String LONGITUDE_COLUMN = "longitude";

    public ReminderController(Context context){
        this.reminderDBAcessController = ReminderDBAcessController.getInstance(context);
    }

    public List<Reminder> getAllReminders(){
        List<Reminder> reminders = new ArrayList<>();
        sqLiteDatabase = this.reminderDBAcessController.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));
                String startTime = cursor.getString(cursor.getColumnIndex(START_TIME_COLUMN));
                String endTime = cursor.getString(cursor.getColumnIndex(END_TIME_COLUMN));
                int weatherIconId = cursor.getInt(cursor.getColumnIndex(WEATHER_ICON_ID_COLUMN));
                String location = cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN));
                double latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE_COLUMN));
                double longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE_COLUMN));

                Reminder reminder = new Reminder( title, startTime, endTime, weatherIconId, location, latitude, longitude);
                reminders.add(reminder);

                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.reminderDBAcessController.closeDatabase();
        }
        Log.d(TAG, reminders.toString());
        return reminders;
    }

    public int getReminderListSize(){
        return getAllReminders().size();
    }

    public void createReminder(Reminder reminder){
        sqLiteDatabase = this.reminderDBAcessController.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues values = createContentValuesFrom(reminder);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, values);
        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            sqLiteDatabase.endTransaction();
            reminderDBAcessController.closeDatabase();
        }
    }

    public void updateReminder(Reminder reminder, int id){
        sqLiteDatabase = this.reminderDBAcessController.openDatabase();
        try {
            ContentValues values = createContentValuesFrom(reminder);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, values);
        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            sqLiteDatabase.endTransaction();
            reminderDBAcessController.closeDatabase();


        }
    }

    public void deleteReminder(int id){
        sqLiteDatabase = this.reminderDBAcessController.openDatabase();
        try {
            sqLiteDatabase.delete(DB_TABLE_NAME, "id = ? ", new String[]{String.valueOf(id)});
        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            reminderDBAcessController.closeDatabase();
        }
    }

    private ContentValues createContentValuesFrom(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUMN, reminder.getTitle());
        values.put(START_TIME_COLUMN, reminder.getStartTime());
        values.put(END_TIME_COLUMN, reminder.getEndTime());
        values.put(WEATHER_ICON_ID_COLUMN, reminder.getWeatherIconId());
        values.put(LOCATION_COLUMN, reminder.getLocation());
        values.put(LATITUDE_COLUMN, reminder.getLatitude());
        values.put(LONGITUDE_COLUMN, reminder.getLongtitude());
        return values;
    }


}
