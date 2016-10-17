package com.teamsmokeweed.qroute.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class ViewDatabaseQr {

    ArrayList<String> titles = new ArrayList<String>();
    ArrayList<String> placeName = new ArrayList<String>();
    ArrayList<String> placeType = new ArrayList<String>();
    ArrayList<String> des = new ArrayList<String>();
    ArrayList<String> webPage = new ArrayList<String>();


    ArrayList<Float> lat = new ArrayList<Float>();
    ArrayList<Float> lng = new ArrayList<Float>();

    DatabaseGenQr mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;

    Context context;

    public ViewDatabaseQr(Context context){
        this.context = context;
    }

    public ArrayList<String> getDes() {
        return des;
    }

    public void setDes(ArrayList<String> des) {
        this.des = des;
    }

    public ArrayList<Float> getLat() {
        return lat;
    }

    public void setLat(ArrayList<Float> lat) {
        this.lat = lat;
    }

    public ArrayList<Float> getLng() {
        return lng;
    }

    public void setLng(ArrayList<Float> lng) {
        this.lng = lng;
    }

    public ArrayList<String> getPlaceName() {
        return placeName;
    }

    public void setPlaceName(ArrayList<String> placeName) {
        this.placeName = placeName;
    }

    public ArrayList<String> getPlaceType() {
        return placeType;
    }

    public void setPlaceType(ArrayList<String> placeType) {
        this.placeType = placeType;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getWebPage() {
        return webPage;
    }

    public void setWebPage(ArrayList<String> webPage) {
        this.webPage = webPage;
    }

    public void queryGen(){
        mHelper = new DatabaseGenQr(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseGenQr.TABLE_NAME, null);

        //ArrayList<String> arr_list = new ArrayList<String>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast() ){
//            arr_list.add("id : " + mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_id))
//                    + "\t\t" + mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_lat))
//                    + "\nDes : " + mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_des)));
//            Log.d("aaaaaaaaaaaaa", mCursor.getFloat(mCursor.getColumnIndex(DatabaseGenQr.Col_lat))+"" );
            this.lat.add(mCursor.getFloat(mCursor.getColumnIndex(DatabaseGenQr.Col_lat)));
            this.lng.add(mCursor.getFloat(mCursor.getColumnIndex(DatabaseGenQr.Col_lng)));
            this.placeName.add(mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_placename)));
            this.placeType.add(mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_placetype)));
            this.titles.add(mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_titles)));
            this.des.add(mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_des)));
            this.webPage.add(mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_web)));
            mCursor.moveToNext();
        }

    }
}
