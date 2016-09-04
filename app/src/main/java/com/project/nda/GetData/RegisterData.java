package com.project.nda.GetData;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.nda.adapter.GetDataFromAssetsAdapter;

/**
 * Created by ndact on 31/08/2016.
 */
public class RegisterData {

    SQLiteDatabase database = null;
    public long Register(Context context, String email, String pass)
    {
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);
        ContentValues row = new ContentValues();
        row.put("Email", email);
        row.put("Password", pass);
        long r = database.insert("User", null, row);
        Log.d("l",database.insert("User", null, row)+"");
        return r;
    }
}
