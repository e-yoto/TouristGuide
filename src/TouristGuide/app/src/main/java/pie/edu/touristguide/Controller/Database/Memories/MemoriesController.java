package pie.edu.touristguide.Controller.Database.Memories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Model.Album;
import pie.edu.touristguide.Model.Memory;

public class MemoriesController {
    private MemoriesDBAccessController databaseAccessHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String DB_TABLE_NAME = "memory";
    private String COLUMN_IMAGE = "image";
    private static final String TAG = MemoriesController.class.getSimpleName();

    public MemoriesController (Context context){
        this.databaseAccessHelper = MemoriesDBAccessController.getInstance(context);
    }

    public List<Memory> getAllMemories() {
        List<Memory> memories = new ArrayList<>();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Memory memory = new Memory();
                memory.setAlbum(cursor.getString(cursor.getColumnIndex("album")));
                memory.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                memory.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                memory.setId(cursor.getInt(cursor.getInt(cursor.getColumnIndex("id"))));
                memories.add(memory);
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
        return memories;
    }

    public void createMemory(Memory newMemory) {
        List<Memory> memories = getAllMemories();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("album", newMemory.getAlbum());
            values.put("image", newMemory.getImage());
            values.put("description", newMemory.getDescription());

            sqLiteDatabase.insert(DB_TABLE_NAME, TAG, values);
            sqLiteDatabase.setTransactionSuccessful();
        }
        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }

        finally {
            sqLiteDatabase.endTransaction();
            this.databaseAccessHelper.closeDatabase();
        }
    }

    public void deleteMemory(Memory memory){
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        try {
            sqLiteDatabase.delete(DB_TABLE_NAME, "album = ?", new String[]{memory.getAlbum()});
        }
        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }
        finally {
            sqLiteDatabase.close();
            this.databaseAccessHelper.closeDatabase();
        }

    }

    /**
     * When album changes name, we must move the memories to the new album
     * @return
     */
    public List<Memory> updateMemoriesAlbum(Album oldAlbum, Album newAlbum) {
        List<Memory> memories = new ArrayList<>();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Memory memory = new Memory();

                if (cursor.getString(cursor.getColumnIndex("album")).equals(oldAlbum.getName())){
                    ContentValues values = new ContentValues();
                    values.put("album", newAlbum.getName());
                    values.put("image", cursor.getBlob(cursor.getColumnIndex("image")));
                    values.put("description", cursor.getBlob(cursor.getColumnIndex("image")));
                    values.put("id", cursor.getInt(cursor.getInt(cursor.getColumnIndex("id"))));

                    memory.setId(cursor.getInt(cursor.getInt(cursor.getColumnIndex("id"))));

                    sqLiteDatabase.update(DB_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(memory.getId())});
                    cursor.moveToNext();
                }

            }
            cursor.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            this.databaseAccessHelper.closeDatabase();
        }
        return memories;
    }


    public void updateMemory(Memory previousMemory, Memory newMemory) {
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("album", newMemory.getAlbum());
            values.put("image", previousMemory.getImage());
            values.put("description", newMemory.getDescription());

            sqLiteDatabase.update(DB_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(previousMemory.getId())});
        }

        catch (Exception e){
            Log.w(TAG, e.fillInStackTrace());
        }

        finally {
            databaseAccessHelper.closeDatabase();
        }
    }

    public byte[] getImage(String name) {
        byte[] data = null;
        Cursor cursor = null;
        //-- 1) Open the database.
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT image FROM " + DB_TABLE_NAME + " WHERE name = ?", new String[]{name});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                data = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                break;  // Assumption: name is unique
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //-- 3) closeDatabase the database and release any locked resources.
            this.databaseAccessHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        }
        return data;
    }

    /**
     * Get only the memories that match the album clicked
     * @return
     */
    public List<Memory> getAlbumMemories(String album) {
        List<Memory> memories = new ArrayList<>();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (cursor.getString(cursor.getColumnIndex("album")).equals(album)) {
                    Memory memory = new Memory();
                    memory.setAlbum(cursor.getString(cursor.getColumnIndex("album")));
                    memory.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                    memory.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    memories.add(memory);
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
        return memories;
    }

}
