package com.project.nda.GetData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.model.LoaiTaiKhoan;

import java.util.ArrayList;

/**
 * Created by ndact on 31/08/2016.
 */
public class LoaiTaiKhoanGetData {

    SQLiteDatabase database = null;

    public void ListTaiKhoan(Context context, ArrayList<LoaiTaiKhoan> listDataLoaiTaiKhoan) {
        listDataLoaiTaiKhoan.clear();
        String mquery = "SELECT * FROM LoaiTaiKhoan";
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(mquery, null);
        while (cursor.moveToNext()) {
            com.project.nda.model.LoaiTaiKhoan loaiTaiKhoan = new com.project.nda.model.LoaiTaiKhoan();
            loaiTaiKhoan.setIdLoaiTaiKhoan(cursor.getInt(0));
            loaiTaiKhoan.setTaiKhoan(cursor.getString(1));
            listDataLoaiTaiKhoan.add(loaiTaiKhoan);
        }
        cursor.close();
    }
}
