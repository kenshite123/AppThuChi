package com.project.nda.DuLieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.Adapter.LayDuLieuTuAsset;
import com.project.nda.Model.LoaiChi;
import com.project.nda.Model.MucChi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ndact on 31/08/2016.
 */
public class DuLieuMucChi {

    SQLiteDatabase database = null;

    public void DanhSachMucChi(Context context, ArrayList<LoaiChi> dsLoaiChi,HashMap<LoaiChi, List<MucChi>> dsDataMucChi)
    {
        dsLoaiChi.clear();
        dsDataMucChi.clear();
        String query = "SELECT * FROM LOAICHI";
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            LoaiChi loaiChi = new LoaiChi();
            loaiChi.setIdLoaiChi(cursor.getInt(0));
            loaiChi.setTenLoaiChi(cursor.getString(1));
            dsLoaiChi.add(loaiChi);
        }
        //Lấy danh sách mục chi theo idLoaiChi
        for (int i = 0; i < dsLoaiChi.size(); i++) {
            ArrayList<MucChi> arr = new ArrayList<>();
            String mquery = "SELECT * FROM MUCCHI WHERE idLoaiChi=" + dsLoaiChi.get(i).getIdLoaiChi();
            database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
            cursor = database.rawQuery(mquery, null);
            while (cursor.moveToNext()) {
                MucChi mucChi = new MucChi();
                mucChi.setIdMucChi(cursor.getInt(0));
                mucChi.setIdLoaiChi(cursor.getInt(1));
                mucChi.setMucChi(cursor.getString(2));
                arr.add(mucChi);
                dsDataMucChi.put(dsLoaiChi.get(i), arr);
            }
            cursor.close();
        }
    }
    public long ThemChi(Context context, int idMucChi, int idTaiKhoan,
                          String MaND, String NgayChi, int SoTienChi, String DienGiaiChi){
        database=context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,
                Context.MODE_PRIVATE,
                null);
        ContentValues insertNewValue=new ContentValues();
        insertNewValue.put("idMucChi", idMucChi);
        insertNewValue.put("idTaiKhoan", idTaiKhoan);
        insertNewValue.put("MaND", MaND);
        insertNewValue.put("NgayChi", NgayChi);
        insertNewValue.put("SoTienChi", SoTienChi);
        insertNewValue.put("DienGiaiChi", DienGiaiChi);
        long kq=database.insert("Chi", null, insertNewValue);
        return kq;
    }
    public void LamMoiDulieuChi(Context context, String maND) {
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        database.delete("Chi","MAND=?", new String[]{maND});
    }

}
