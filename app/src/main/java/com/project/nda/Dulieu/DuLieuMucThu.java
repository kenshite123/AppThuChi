package com.project.nda.DuLieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.Adapter.LayDuLieuTuAsset;
import com.project.nda.Model.MucThu;

import java.util.ArrayList;

/**
 * Created by ndact on 31/08/2016.
 */
public class DuLieuMucThu {

    SQLiteDatabase database = null;
    public void DanhSachMucThu(Context context, ArrayList<MucThu> dsMucThu)
    {
        dsMucThu.clear();
        String mquery = "SELECT * FROM MUCTHU";
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(mquery, null);
        while (cursor.moveToNext()) {
            MucThu mucThu = new MucThu();
            mucThu.setIdMucThu(cursor.getInt(0));
            mucThu.setMucThu(cursor.getString(1));
            dsMucThu.add(mucThu);
        }
        cursor.close();

    }
    public long ThemThu(Context context, int idMucThu, int idTaiKhoan,
                          String MaND, String NgayThu, int SoTienThu, String DienGiaiThu) {
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,
                Context.MODE_PRIVATE,
                null);
        ContentValues insertNewValue = new ContentValues();
        insertNewValue.put("idMucThu", idMucThu);
        insertNewValue.put("idTaiKhoan", idTaiKhoan);
        insertNewValue.put("MaND", MaND);
        insertNewValue.put("NgayThu", NgayThu);
        insertNewValue.put("SoTienThu", SoTienThu);
        insertNewValue.put("DienGiaiThu", DienGiaiThu);
        long kq = database.insert("Thu", null, insertNewValue);
        return kq;
    }
    public void LamMoiDuLieuThu(Context context, String maND) {
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        database.delete("THU","MAND=?", new String[]{maND});
    }
}
