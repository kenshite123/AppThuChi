package com.project.nda.DuLieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.nda.Adapter.LayDuLieuTuAsset;

/**
 * Created by ndact on 31/08/2016.
 */
public class DuLieuDangKy {

    SQLiteDatabase database = null;
    public long DangKy(Context context, String email, String pass)
    {
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);
        ContentValues row = new ContentValues();
        row.put("Email", email);
        row.put("Password", pass);
        long r = database.insert("User", null, row);
        Log.d("l",database.insert("User", null, row)+"");
        return r;
    }
    public int KiemTraTonTaiNguoiDung(Context context, String Email)
    {
       int dem = 0;
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);

        String sql = "SELECT MAND FROM USER WHERE EMAIL='" + Email +"'";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            dem = cursor.getInt(0);
        }
        cursor.close();
        return dem;
    }
}
