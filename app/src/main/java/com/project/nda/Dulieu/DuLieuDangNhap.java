package com.project.nda.DuLieu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.Adapter.LayDuLieuTuAsset;

/**
 * Created by ndact on 31/08/2016.
 */
public class DuLieuDangNhap {

    SQLiteDatabase database = null;
    public int KiemTraThongTinDangNhap(Context context, String email, String pass)
    {
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,android.content.Context.MODE_PRIVATE, null);
        String query = "SELECT COUNT(*) FROM USER WHERE EMAIL='" + email + "' AND PASSWORD='" + pass + "'";
        Cursor cursor = database.rawQuery(query, null);
        int dem = 0;
        while (cursor.moveToNext())
        {
            dem = cursor.getInt(0);
        }
        return dem;
    }
    public String LayMaND(Context context, String email, String pass ) {
        String sql = "SELECT MAND FROM USER WHERE EMAIL='" + email + "' AND PASSWORD='" + pass + "'";
        Cursor cursorMaND = database.rawQuery(sql, null);
        String maND = null;
        while (cursorMaND.moveToNext()) {
            maND = cursorMaND.getString(0);
        }
        cursorMaND.close();
        return maND;

    }

}
