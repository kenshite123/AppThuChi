package com.project.nda.GetData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;

/**
 * Created by DELL on 9/5/2016.
 */
public class ThongKeGetData {

    SQLiteDatabase database = null;
    Context context;
    String startDate;
    String endDate;
    String MaND;
    int idTaiKhoan;
    public ThongKeGetData(Context context, String startDate, String endDate, String MaND, int idTaiKhoan)
    {
      this.context = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.MaND = MaND;
        this.idTaiKhoan = idTaiKhoan;

    }

    public String getDataThongKeChi() {

        database = context.openOrCreateDatabase(
                GetDataFromAssetsAdapter.DATABASE_NAME,
                context.MODE_PRIVATE, null);
        String sql = "SELECT SUM(SOTIENCHI) AS TONGTIENCHI FROM CHI " +
                "WHERE NGAYCHI >= '" + startDate + "' AND NGAYCHI <= '" + endDate+  "' " +
                "AND MAND=" + MaND +
                " AND IDTAIKHOAN=" + idTaiKhoan ;
        Cursor cursor = database.rawQuery(sql, null);
        String thongkeChi = "";
        while (cursor.moveToNext())
        {
            thongkeChi = cursor.getString(0);
        }
        cursor.close();
        return thongkeChi;
    }
    public String getDataThongKeThu() {

        database = context.openOrCreateDatabase(
                GetDataFromAssetsAdapter.DATABASE_NAME,
                context.MODE_PRIVATE, null);
        String sql = "SELECT SUM(SOTIENTHU) AS TONGTIENTHU FROM THU " +
                "WHERE NGAYTHU >= '" + startDate + "' AND NGAYTHU <= '" + endDate+  "' " +
                "AND MAND=" + MaND +
                " AND IDTAIKHOAN=" + idTaiKhoan;
        Cursor cursor = database.rawQuery(sql, null);
        String thongkeThu = "";
        while (cursor.moveToNext())
        {
            thongkeThu = cursor.getString(0);
        }
        cursor.close();
        return thongkeThu;

    }
}