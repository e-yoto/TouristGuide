package pie.edu.touristguide.Controller.Database.Memories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Model.Album;

/**
 * For CRUD operations on Albums
 */
public class AlbumController {
    private MemoriesDBAccessController databaseAccessHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String DB_TABLE_NAME = "album";
    private static final String TAG = MemoriesController.class.getSimpleName();

    public AlbumController (Context context){
        this.databaseAccessHelper = MemoriesDBAccessController.getInstance(context);
    }

    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DB_TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Album album = new Album();
                album.setName(cursor.getString(cursor.getColumnIndex("name")));
                albums.add(album);
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
        return albums;
    }

    public void createAlbum(Album newAlbum) {
        List<Album> albums = getAllAlbums();

        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("name", newAlbum.getName());

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

    public void deleteAlbum(Album album){
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        try {
            sqLiteDatabase.delete(DB_TABLE_NAME, "name = ?", new String[]{album.getName()});
        }

        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }

        finally {
            sqLiteDatabase.close();
            this.databaseAccessHelper.closeDatabase();
        }
    }

    public void udpateAlbum(Album previousAlbum, Album updatedAlbum){
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", updatedAlbum.getName());

            sqLiteDatabase.update(DB_TABLE_NAME, values,"name = ?", new String[]{previousAlbum.getName()});
        }

        catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace());
        }

        finally {
            databaseAccessHelper.closeDatabase();
        }
    }
}
