package com.teamsmokeweed.qroute.database;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamsmokeweed.qroute.R;

import java.util.ArrayList;

/**
 * Created by jongzazaal on 17/10/2559.
 */

public class ViewGenQr extends AppCompatActivity {
    DatabaseGenQr mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;
    ListView listStudent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gen_qr);

        mHelper = new DatabaseGenQr(this);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseGenQr.TABLE_NAME, null);

        ArrayList<String> arr_list = new ArrayList<String>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast() ){
            arr_list.add("id : " + mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_id))
                    + "\t\t" + mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_lat))
                    + "\nDes : " + mCursor.getString(mCursor.getColumnIndex(DatabaseGenQr.Col_des)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_listview, arr_list);

        listStudent = (ListView)findViewById(R.id.listStudent);
        listStudent.setAdapter(adapterDir);
    }

    public void onStop() {
        super.onStop();
        mHelper.close();
        mDb.close();
    }
}
