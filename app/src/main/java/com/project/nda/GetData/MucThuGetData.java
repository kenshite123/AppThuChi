package com.project.nda.GetData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.model.MucThu;

import java.util.ArrayList;

/**
 * Created by ndact on 31/08/2016.
 */
public class MucThuGetData {

    SQLiteDatabase database = null;
    public void ListMucThu(Context context, ArrayList<MucThu> listDataMucThu)
    {
        listDataMucThu.clear();
        String mquery = "SELECT * FROM MUCTHU";
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery(mquery, null);
        while (cursor.moveToNext()) {
            MucThu mucThu = new MucThu();
            mucThu.setIdMucThu(cursor.getInt(0));
            mucThu.setMucThu(cursor.getString(1));
            listDataMucThu.add(mucThu);
        }
        cursor.close();

    }
    public long insertThu(Context context, int idMucThu, int idTaiKhoan,
                          String MaND, String NgayThu, int SoTienThu, String DienGiaiThu) {
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,
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
    public void ReSetDataThu(Context context, String maND) {
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        database.delete("THU","MAND=?", new String[]{maND});
    }

}
