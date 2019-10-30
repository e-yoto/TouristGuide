package pie.edu.touristguide.Controller.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Model.Contact;

/**
 * @author BoTao Yu
 *
 * Perform CRUD operations on contact
 */
public class ContactController {

    private DBAccessController DBAccessController;
    private SQLiteDatabase sqLiteDatabase;
    private final String DB_TABLE_NAME = "contact";
    private final String TAG = this.getClass().getSimpleName();

    public ContactController(Context context){
        ContactDBOpenHelper openHelper = new ContactDBOpenHelper(context);
        this.DBAccessController = DBAccessController.getInstance(openHelper);
    }

    public List<Contact> getAllContacts(){
        List<Contact> contacts = new ArrayList<>();

        sqLiteDatabase = this.DBAccessController.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                long phoneNumber = cursor.getInt(cursor.getColumnIndex("phone_number"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                long rawOffSet = cursor.getInt(cursor.getColumnIndex("raw_offset"));
                byte[] bitMapBytes = cursor.getBlob(cursor.getColumnIndex("bitmap_bytes"));
                contacts.add(new Contact(name, phoneNumber, location, rawOffSet, bitMapBytes));
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.DBAccessController.closeDatabase();
        }
        return contacts;
    }

    public void createContact(Contact contact){
        sqLiteDatabase = this.DBAccessController.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues values = convertToContentValues(contact);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, values);

        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            sqLiteDatabase.endTransaction();
            DBAccessController.closeDatabase();
        }
    }

    public void updateContact(Contact newContact, long phoneNumber){
        sqLiteDatabase = this.DBAccessController.openDatabase();
        try {
            ContentValues values = convertToContentValues(newContact);
            sqLiteDatabase.update(DB_TABLE_NAME, values, "phone_number = ? ", new String[]{String.valueOf(phoneNumber)});

        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            DBAccessController.closeDatabase();
        }
    }

    public void deleteContact(long oldPhoneNumber){
        sqLiteDatabase = this.DBAccessController.openDatabase();
        try {
            sqLiteDatabase.delete(DB_TABLE_NAME, "phone_number = ? ", new String[]{String.valueOf(oldPhoneNumber)});

        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            DBAccessController.closeDatabase();
        }
    }

    public void updateContact(Contact contact){
        sqLiteDatabase = this.DBAccessController.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues values = convertToContentValues(contact);
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.insert(DB_TABLE_NAME, null, values);

        }catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        }finally {
            sqLiteDatabase.endTransaction();
            DBAccessController.closeDatabase();
        }
    }

    private ContentValues convertToContentValues(Contact contact){
        ContentValues values = new ContentValues();
        values.put("phone_number", contact.getPhoneNumber());
        values.put("name", contact.getName());
        values.put("location", contact.getLocation());
        values.put("raw_offset", contact.getRawOffSet());
        values.put("bitmap_bytes", contact.getBitMapBytes());
        return values;
    }
}
