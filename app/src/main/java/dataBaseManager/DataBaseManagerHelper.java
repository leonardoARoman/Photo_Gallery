package dataBaseManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import model.Album;

/**
 * Created by leonardoroman on 12/11/17.
 */

public class DataBaseManagerHelper extends SQLiteOpenHelper {
    // DATABASE
    private static final String DATABASE_NAME = "photos19DB_v3";
    // TABLES
    private static final String TABLE_ALBUM = "album";
    private static final String TABLE_PHOTO = "photo";
    // ATTRIBUTES
    private static final String ALBUM_NAME   = "aName";
    private static final String ALBUM_NUMBER = "aNumber";

    private static final String PHOTO_NUMBER = "pNumber";
    private static final String PHOTO_IMAGE  = "image";


    public DataBaseManagerHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALBUM_LIST_TABLE = "CREATE TABLE album (" +
                "aNumber INTEGER PRIMARY KEY AUTOINCREMENT," +
                ALBUM_NAME + " TEXT)";

        String CREATE_PHOTO_LIST_TABLE = "CREATE TABLE " +  TABLE_PHOTO +  " (" +
                ALBUM_NUMBER + " INTEGER," +
                PHOTO_NUMBER + " INTEGER," +
                PHOTO_IMAGE + " TEXT," +
                "PRIMARY KEY ("+ALBUM_NUMBER+","+PHOTO_NUMBER+")," +
                "FOREIGN KEY("+ALBUM_NUMBER+") REFERENCES "+TABLE_ALBUM+"("+ALBUM_NUMBER+") "+
                "ON DELETE CASCADE)";

        String CREATE_TAG_TABLE = "CREATE TABLE tag(" +
                "aNumber INTEGER, " +
                "pNumber INTEGER, " +
                "tNumber INTEGER, " +
                "hashTag TEXT, " +
                "PRIMARY KEY (aNumber, pNumber, tNumber), " +
                "FOREIGN KEY(aNumber) REFERENCES album(aNumber) on delete cascade, "+
                "FOREIGN KEY(aNumber, pNumber) REFERENCES photo(aNumber, pNumber) on delete cascade)";

        // create contacts table
        db.execSQL(CREATE_ALBUM_LIST_TABLE);
        db.execSQL(CREATE_PHOTO_LIST_TABLE);
        db.execSQL(CREATE_TAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {

    }

    // Check √.
    public void addAlbum(Album album){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(ALBUM_NAME, album.toString()); // get name

        // 3. insert
        db.insert(TABLE_ALBUM, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    // Check √.
    public void editAlbumName(String albumName, int albumNumber){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        Log.v("DATABASE", "albumName: " + albumName + "\nalbumNum: " + albumNumber);
        values.put(ALBUM_NAME, albumName); // get name

        db.update(TABLE_ALBUM, values, "aNumber = ?",
                new String[] { String.valueOf(albumNumber) });

        db.close();
    }

    // Check √.
    public void deleteAlbum(ArrayList<String> albumName){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete item from DB
        for (int i = 0; i < albumName.size(); i++){
            db.delete(TABLE_ALBUM,"aName = ?", new String[] { albumName.get(i) });
        }
        db.close();
    }

    // Check √.
    public ArrayList<String> getAlbums() {
        ArrayList<String> contactList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT "+ALBUM_NAME+" FROM " + TABLE_ALBUM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contactList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return contactList;
    }

    // Check √.
    public int getAlbumNumber(String name) {
        // Select All Query
        String selectQuery = "SELECT "+ALBUM_NUMBER+
                " FROM " + TABLE_ALBUM+
                " WHERE "+ALBUM_NAME+" =?";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {name},null);
        int number = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            number = cursor.getInt(0);
        }
        db.close();
        // return contact list
        return number;
    }



    public void addPhoto(int albumNumber,int photoNUmber,String image){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(ALBUM_NUMBER, albumNumber);
        values.put(PHOTO_NUMBER, photoNUmber);
        values.put(PHOTO_IMAGE, image); // get name

        // 3. insert
        db.insert(TABLE_PHOTO, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        // 4. close
        db.close();
    }

    public void deletePhoto(ArrayList<String> photos) {
        System.out.println("deletePhoto was clicked! ");
        for (int i = 0; i < photos.size(); i++){
            System.out.println("photo: "+i+"\n"+photos.get(i));
        }
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete item from DB
        for(int i = 0; i < photos.size(); i++){
            db.delete(TABLE_PHOTO,"image = ?", new String[] { photos.get(i) });
        }
        db.close();
    }

    public ArrayList<String> getPhotosFromAlbum(int number) {
        ArrayList<String> photos = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT p."+PHOTO_IMAGE+
                " FROM "+TABLE_ALBUM+" a, "+TABLE_PHOTO+" p "+
                "WHERE a."+ALBUM_NUMBER+"="+"p."+ALBUM_NUMBER+
                " AND a."+ALBUM_NUMBER+"="+number;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                photos.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return photos;
    }

}
