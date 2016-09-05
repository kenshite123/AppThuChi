package com.project.nda.GetData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.model.ThongKeChi;
import com.project.nda.model.ThongKeThu;

/**
 * Created by DELL on 9/5/2016.
 */
public class ThongKeGetData {

    SQLiteDatabase database = null;

    public ThongKeChi getDataThongKeChi(Context context, String value, String MaND, int idTaiKhoan) {
        ThongKeChi thongkeChi = new ThongKeChi();

        database = context.openOrCreateDatabase(
                GetDataFromAssetsAdapter.DATABASE_NAME,
                context.MODE_PRIVATE, null);
        String sql = "SELECT NGAYCHI, SUM(SOTIENCHI) AS TONGTIENCHI FROM CHI " +
                "WHERE NGAYCHI='" + value + "' " +
                "AND MAND=" + MaND +
                " AND IDTAIKHOAN=" + idTaiKhoan +
                " GROUP BY NGAYCHI";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            thongkeChi.setNgayThangNam(cursor.getString(0));
            thongkeChi.setTongTienChi(Integer.parseInt(cursor.getString(1)));
        }
        cursor.close();
        return thongkeChi;

    }
    public ThongKeThu getDataThongKeThu(Context context, String value, String MaND, int idTaiKhoan) {
        ThongKeThu thongKeThu = new ThongKeThu();

        database = context.openOrCreateDatabase(
                GetDataFromAssetsAdapter.DATABASE_NAME,
                context.MODE_PRIVATE, null);
        String sql = "SELECT NGAYTHU, SUM(SOTIENTHU) AS TONGTIENTHU FROM THU " +
                "WHERE NGAYTHU='" + value + "' " +
                "AND MAND=" + MaND +
                " AND IDTAIKHOAN=" + idTaiKhoan +
                " GROUP BY NGAYTHU";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            thongKeThu.setNgayThangNam(cursor.getString(0));
            thongKeThu.setTongTienThu(Integer.parseInt(cursor.getString(1)));
        }
        cursor.close();
        return thongKeThu;

    }
}