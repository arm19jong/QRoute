package com.teamsmokeweed.qroute.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class DatabaseGenQr extends SQLiteOpenHelper {
    private static final String DB_NAME = "content";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "content_gen";

    public static final String Col_id = "id";
    public static final String Col_lat = "lat";
    public static final String Col_lng = "lng";
    public static final String Col_placename = "placename";
    public static final String Col_placetype = "placetype";
    public static final String Col_titles = "titles";
    public static final String Col_des = "des";
    public static final String Col_web = "web";


    public DatabaseGenQr(Context context) {super(context, TABLE_NAME, null, DB_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE content_gen(\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tlat FLOAT,\n" +
                "\tlng FLOAT,\n" +
                "\tplacename TEXT,\n" +
                "\tplacetype TEXT,\n" +
                "\ttitles TEXT,\n" +
                "\tdes TEXT,\n" +
                "\tweb TEXT\n" +
                ");");

        db.execSQL("INSERT into content_gen(lat, lng, placename, placetype, titles, des, web) VALUES(?, ?, ?, ?, ?, ?, ?);"
        , new Object[] {Float.valueOf(11),Float.valueOf(22),"pN", "pTy", "ti", "des", "web"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
