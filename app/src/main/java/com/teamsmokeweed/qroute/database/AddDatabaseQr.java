package com.teamsmokeweed.qroute.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.teamsmokeweed.qroute.genqr.DateQr;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class AddDatabaseQr {
    DateQr dateQr;
    SQLiteDatabase mDb;
    DatabaseGenQr mHelper;
    Context context;
    public AddDatabaseQr(DateQr dateQr, Context context){
        this.dateQr = dateQr;
        this.context = context;
    }
    public void addDb(){
        mHelper = new DatabaseGenQr(context);
        mDb = mHelper.getWritableDatabase();

        mDb.execSQL("INSERT into content_gen(lat, lng, placename, placetype, titles, des, web) VALUES(?, ?, ?, ?, ?, ?, ?);"
                , new Object[] {Float.valueOf(dateQr.getLat()),Float.valueOf(dateQr.getLng()),
                        dateQr.getPlaceName(), dateQr.getPlaceType(), dateQr.getTitles(),
                        dateQr.getDes(), dateQr.getWebPage()});
        mHelper.close();
        mDb.close();

    }

}
