package com.project.nda.function;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;

/**
 * Created by ndact on 03/09/2016.
 */
public class UserGetData {
    SQLiteDatabase database = null;
    public String GetPassWord(Context context, String maND)
    {
        String password="";
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);

        String sql = "SELECT PASSWORD FROM USER WHERE MAND='" + maND +"'";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            password = cursor.getString(0);
        }
        cursor.close();
        return password;
    }
    public int UpdatePassword(Context context,String maND, String password)
    {
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);
        ContentValues update = new ContentValues();
        update.put("Password", password);
        int r = database.update("USER", update, "MAND=?", new String[]{maND});
        return r;
    }
}
