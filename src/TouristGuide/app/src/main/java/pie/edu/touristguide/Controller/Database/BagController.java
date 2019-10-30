package pie.edu.touristguide.Controller.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Model.Item;

public class BagController {
    private BagDBAccessController databaseAccessHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String DB_TABLE_NAME = "bag";
    private static final String TAG = ContactController.class.getSimpleName();

    public BagController (Context context){
        this.databaseAccessHelper = BagDBAccessController.getInstance(context);
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item item = new Item();
                item.setTitle(cursor.getString(cursor.getColumnIndex("item_name")));
                item.setWeight(cursor.getDouble(cursor.getColumnIndex("weight")));
                item.setIsLiquid(cursor.getInt(cursor.getColumnIndex("is_liquid")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));
                items.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            this.databaseAccessHelper.closeDatabase();
        }
        return items;
    }

    public List<Item> getTypeItems(String type) {
        List<Item> items = new ArrayList<>();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item item = new Item();

                if (cursor.getString(cursor.getColumnIndex("type")).equals(type))
                {
                    item.setTitle(cursor.getString(cursor.getColumnIndex("item_name")));
                    item.setWeight(cursor.getDouble(cursor.getColumnIndex("weight")));
                    item.setIsLiquid(cursor.getInt(cursor.getColumnIndex("is_liquid")));
                    item.setType(cursor.getString(cursor.getColumnIndex("type")));
                    items.add(item);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            this.databaseAccessHelper.closeDatabase();
        }
        return items;
    }

    public void createItem(Item newItem) {
        List<Item> items = getAllItems();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("item_name", newItem.getTitle());
            values.put("weight", newItem.getWeight());
            values.put("is_liquid", newItem.getIsLiquid());
            values.put("type", newItem.getType());

            sqLiteDatabase.insert(DB_TABLE_NAME, TAG, values);
            sqLiteDatabase.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.wtf(TAG, e.fillInStackTrace());
        }

        finally {
            sqLiteDatabase.endTransaction();
            this.databaseAccessHelper.closeDatabase();
        }
    }

    public double getWeight(String type){
        double total = 0;

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item item = new Item();

                if (cursor.getString(cursor.getColumnIndex("type")).equals(type))
                {
                    total += cursor.getDouble(cursor.getColumnIndex("weight"));
                }
                cursor.moveToNext();
            }
            cursor.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            this.databaseAccessHelper.closeDatabase();
        }

        return total;
    }

    public void updateItem(Item oldItem, Item toUpdate){
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("item_name", toUpdate.getTitle());
            values.put("weight", toUpdate.getWeight());
            values.put("is_liquid", toUpdate.getIsLiquid());
            values.put("type", toUpdate.getType());

            sqLiteDatabase.update(DB_TABLE_NAME, values, "item_name = ?", new String[]{oldItem.getTitle()});
        } catch (Exception e) {
            Log.wtf(TAG, e.fillInStackTrace());
        } finally {
            databaseAccessHelper.closeDatabase();
        }
    }

    public void deleteItem(Item toDelete){
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        try {
            sqLiteDatabase.delete(DB_TABLE_NAME, "item_name = ?", new String[]{toDelete.getTitle()});
        }
        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }
        finally {
            sqLiteDatabase.close();
            this.databaseAccessHelper.closeDatabase();
        }
    }
}
