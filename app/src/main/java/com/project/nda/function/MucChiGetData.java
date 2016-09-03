package com.project.nda.function;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.model.LoaiChi;
import com.project.nda.model.MucChi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ndact on 31/08/2016.
 */
public class MucChiGetData {

    SQLiteDatabase database = null;
    public void ListMucChi(Context context, ArrayList<LoaiChi> listDataLoaiChi,HashMap<LoaiChi, List<MucChi>> listDataMucChi)
    {
        listDataLoaiChi.clear();
        listDataMucChi.clear();
        String query = "SELECT * FROM LOAICHI";
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            LoaiChi loaiChi = new LoaiChi();
            loaiChi.setIdLoaiChi(cursor.getInt(0));
            loaiChi.setTenLoaiChi(cursor.getString(1));
            listDataLoaiChi.add(loaiChi);
        }
        //Lấy danh sách mục chi theo idLoaiChi
        for (int i = 0; i < listDataLoaiChi.size(); i++) {
            ArrayList<MucChi> arr = new ArrayList<>();
            String mquery = "SELECT * FROM MUCCHI WHERE idLoaiChi=" + listDataLoaiChi.get(i).getIdLoaiChi();
            database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
            cursor = database.rawQuery(mquery, null);
            while (cursor.moveToNext()) {
                MucChi mucChi = new MucChi();
                mucChi.setIdMucChi(cursor.getInt(0));
                mucChi.setIdLoaiChi(cursor.getInt(1));
                mucChi.setMucChi(cursor.getString(2));
                arr.add(mucChi);
                listDataMucChi.put(listDataLoaiChi.get(i), arr);
            }
            cursor.close();
        }
    }
    public long insertChi(Context context, int idMucChi, int idTaiKhoan,
                          String MaND, String NgayChi, int SoTienChi, String DienGiaiChi){
        database=context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,
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

}
