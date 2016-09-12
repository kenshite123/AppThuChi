package com.project.nda.DuLieu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.Adapter.LayDuLieuTuAsset;
import com.project.nda.Model.LoaiTaiKhoan;

import java.util.ArrayList;

/**
 * Created by ndact on 31/08/2016.
 */
public class DuLieuLoaiTaiKhoan {

    SQLiteDatabase database = null;

    public void DanhSachTaiKhoan(Context context, ArrayList<LoaiTaiKhoan> dsLoaiTaiKhoan) {
        dsLoaiTaiKhoan.clear();
        String mquery = "SELECT * FROM LoaiTaiKhoan";
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(mquery, null);
        while (cursor.moveToNext()) {
            com.project.nda.Model.LoaiTaiKhoan loaiTaiKhoan = new com.project.nda.Model.LoaiTaiKhoan();
            loaiTaiKhoan.setIdLoaiTaiKhoan(cursor.getInt(0));
            loaiTaiKhoan.setTaiKhoan(cursor.getString(1));
            dsLoaiTaiKhoan.add(loaiTaiKhoan);
        }
        cursor.close();
    }
    public String LayTenLoaiTaiKhoan(Context context, int idLoaiTaiKhoan) {
        String TenLoaiTaiKhoan = null;
        String mquery = "SELECT TAIKHOAN FROM LOAITAIKHOAN WHERE IDLOAITAIKHOAN =" + idLoaiTaiKhoan;
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(mquery, null);
        while (cursor.moveToNext()) {
            TenLoaiTaiKhoan = cursor.getString(0);
        }
        cursor.close();
        return TenLoaiTaiKhoan;
    }


}
