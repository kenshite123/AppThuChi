package com.project.nda.function;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;

/**
 * Created by ndact on 31/08/2016.
 */
public class LoginGetData {

    SQLiteDatabase database = null;
    public int CheckInfoLogin(Context context, String email, String pass)
    {
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);
        String query = "SELECT COUNT(*) FROM USER WHERE EMAIL='" + email + "' AND PASSWORD='" + pass + "'";
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext())
        {
            count = cursor.getInt(0);
        }
        return count;
    }
    public String GetMaND(Context context, String email, String pass ) {
        String sql = "SELECT MAND FROM USER WHERE EMAIL='" + email + "' AND PASSWORD='" + pass + "'";
        Cursor cursorMaND = database.rawQuery(sql, null);
        String maND = null;
        while (cursorMaND.moveToNext()) {
            maND = cursorMaND.getString(0);
        }
        return maND;
    }
}
