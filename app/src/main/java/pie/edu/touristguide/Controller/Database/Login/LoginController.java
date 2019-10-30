package pie.edu.touristguide.Controller.Database.Login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import pie.edu.touristguide.Model.User;

/**
 * @author Sebastien El-Hamaoui
 * Login Controller containing the CRUD methods that will interact with the database.
 */

public class LoginController {
    private LoginDBAccessController DBAccessController;
    private SQLiteDatabase sqLiteDatabase;
    private final String DB_TABLE_NAME = "credentials";
    private final String TAG = this.getClass().getSimpleName();

    public LoginController(Context context){
        this.DBAccessController = LoginDBAccessController.getInstance(context);
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        sqLiteDatabase = this.DBAccessController.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                users.add(new User(username, password));
                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.DBAccessController.closeDatabase();
        }
        return users;
    }

    public void createUser(User user){
        sqLiteDatabase = this.DBAccessController.openDatabase();
        sqLiteDatabase.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());

            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, values);
        }
        catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }
        finally {
            sqLiteDatabase.endTransaction();
            DBAccessController.closeDatabase();
        }
    }

    public void updateUser(User newUser, String username){
        sqLiteDatabase = this.DBAccessController.openDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("username", newUser.getUsername());
            values.put("password", newUser.getPassword());

            sqLiteDatabase.update(DB_TABLE_NAME, values, "username = ? ", new String[] {username});

        }
        catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }
        finally {
            DBAccessController.closeDatabase();
        }
    }

    public void deleteUser(String username){
        sqLiteDatabase = this.DBAccessController.openDatabase();

        try {
            sqLiteDatabase.delete(DB_TABLE_NAME, "username = ? ", new String[]{username});

        }
        catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }
        finally {
            DBAccessController.closeDatabase();
        }
    }
}
