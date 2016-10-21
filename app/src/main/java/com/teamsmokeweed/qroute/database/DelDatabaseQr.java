package com.teamsmokeweed.qroute.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.teamsmokeweed.qroute.genqr.DateQr;

/**
 * Created by jongzazaal on 21/10/2559.
 */

public class DelDatabaseQr {
    DateQr dateQr;
    SQLiteDatabase mDb;
    DatabaseGenQr mHelper;
    Context context;
    public DelDatabaseQr(DateQr dateQr, Context context){
        this.dateQr = dateQr;
        this.context = context;
    }
    public void DelDb(){
        mHelper = new DatabaseGenQr(context);
        mDb = mHelper.getWritableDatabase();


        mDb.execSQL("DELETE FROM content_gen WHERE id = ?;"
                , new Object[] {dateQr.getId()}
        );
        mHelper.close();
        mDb.close();

    }
}
