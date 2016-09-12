package com.project.nda.DuLieu;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.project.nda.Adapter.ChiAdapter;
import com.project.nda.Adapter.LayDuLieuTuAsset;
import com.project.nda.Adapter.ThuAdapter;
import com.project.nda.Model.Chi;
import com.project.nda.Model.MucChi;
import com.project.nda.Model.MucThu;
import com.project.nda.Model.Thu;
import com.project.nda.Support.DinhDangTienTe;

/**
 * Created by DELL on 9/5/2016.
 */
public class DuLieuThongKe {

    SQLiteDatabase database = null;
    DinhDangTienTe dinhdang = new DinhDangTienTe();

    public void DuLieu(Activity context, boolean loai, TextView txtChi, TextView txtThu, String maND, String... s){
        database=context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME,
                context.MODE_PRIVATE, null);

        if(loai==true){ // loại = true => search theo ngày => date truyền 1 tham số

            // CHI
            String sqlChi="SELECT SUM(SOTIENCHI) AS TONGTIENCHI FROM CHI WHERE NGAYCHI='" + s[0] + "' AND MAND='" + maND + "'";
            Log.d("hihi", sqlChi);
            int soTienChi=0;
            Cursor cursorChi=database.rawQuery(sqlChi, null);
            while (cursorChi.moveToNext()){
                soTienChi=cursorChi.getInt(0);
            }
            cursorChi.close();
            txtChi.setText(dinhdang.DinhDangTextView(context, soTienChi+""));

            // THU
            String sqlThu="SELECT SUM(SOTIENTHU) AS TONGTIENTHU FROM THU WHERE NGAYTHU='" + s[0] + "' AND MAND='" + maND + "'";
            int soTienThu=0;
            Cursor cursorThu=database.rawQuery(sqlThu, null);
            while (cursorThu.moveToNext()){
                soTienThu=cursorThu.getInt(0);
            }
            cursorThu.close();
            txtThu.setText(dinhdang.DinhDangTextView(context, soTienThu+""));
        }else{ // loại = false => search theo dạng từ ngày đến ngày => date truyền 2 tham số
            // s[0] = startDate, s[1] = endDate
            // CHI
            String sqlChi="SELECT SUM(SOTIENCHI) AS TONGTIENCHI FROM CHI WHERE (NGAYCHI BETWEEN '" + s[0]
                    + "' AND '" + s[1] + "') AND MAND='" + maND + "'";
            int soTienChi=0;
            Cursor cursorChi=database.rawQuery(sqlChi, null);
            while (cursorChi.moveToNext()){
                soTienChi=cursorChi.getInt(0);
            }
            cursorChi.close();
            //Toast.makeText(getContext(), startDate + "\n" + endDate + "\n" + soTienChi+"", Toast.LENGTH_SHORT).show();
            txtChi.setText(dinhdang.DinhDangTextView(context, soTienChi+""));

            // THU
            String sqlThu="SELECT SUM(SOTIENTHU) AS TONGTIENTHU FROM THU WHERE(NGAYTHU BETWEEN '" + s[0]
                    + "' AND '" + s[1] + "') AND MAND='" + maND + "'";
            int soTienThu=0;
            Cursor cursorThu=database.rawQuery(sqlThu, null);
            while (cursorThu.moveToNext()){
                soTienThu=cursorThu.getInt(0);
            }
            cursorThu.close();
            txtThu.setText(dinhdang.DinhDangTextView(context, soTienThu+""));
        }
    }
    public void DuLieuThongKeThu(Context context, ThuAdapter thuAdapter, String maND, int loai, String... date){
        thuAdapter.clear();
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        String sqlThu;
        if(loai==1){
            sqlThu="SELECT MUCTHU.MUCTHU, THU.NGAYTHU, THU.SOTIENTHU, THU.DIENGIAITHU, THU.IDTAIKHOAN " +
                    "FROM MUCTHU INNER JOIN THU ON MUCTHU.IDMUCTHU=THU.IDMUCTHU " +
                    "WHERE NGAYTHU='" + date[0] +"' AND MAND=" + maND;
        }else{
            sqlThu="SELECT MUCTHU.MUCTHU, THU.NGAYTHU, THU.SOTIENTHU, THU.DIENGIAITHU, THU.IDTAIKHOAN " +
                    "FROM MUCTHU INNER JOIN THU ON MUCTHU.IDMUCTHU=THU.IDMUCTHU " +
                    "WHERE (NGAYTHU BETWEEN '" + date[0] + "' AND '" + date[1] + "') AND MAND=" + maND;
        }
        Cursor cursor=database.rawQuery(sqlThu, null);
        while (cursor.moveToNext()){
            Thu thu=new Thu();
            thu.setIdMucThu(new MucThu(cursor.getString(0)));
            thu.setNgayThu(cursor.getString(1));
            thu.setSoTienThu(cursor.getInt(2));
            thu.setDienGiaiThu(cursor.getString(3));
            thu.setIdTaiKhoan(cursor.getInt(4));
            thuAdapter.add(thu);
        }
        thuAdapter.notifyDataSetChanged();
        cursor.close();
    }
    public void DuLieuThongKeChi(Context context, ChiAdapter chiAdapter, String maND, int loai, String... date){
        chiAdapter.clear();
        database = context.openOrCreateDatabase(LayDuLieuTuAsset.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        String sqlChi;
        if(loai==1) {
            sqlChi = "SELECT MUCCHI.MUCCHI, CHI.NGAYCHI, CHI.SOTIENCHI, CHI.DIENGIAICHI, CHI.IDTAIKHOAN " +
                    "FROM MUCCHI INNER JOIN CHI ON MUCCHI.IDMUCCHI=CHI.IDMUCCHI " +
                    "WHERE NGAYCHI='" + date[0] + "' AND MAND=" + maND;
        }else {
            sqlChi = "SELECT MUCCHI.MUCCHI, CHI.NGAYCHI, CHI.SOTIENCHI, CHI.DIENGIAICHI, CHI.IDTAIKHOAN " +
                    "FROM MUCCHI INNER JOIN CHI ON MUCCHI.IDMUCCHI=CHI.IDMUCCHI " +
                    "WHERE (NGAYCHI BETWEEN '" + date[0] + "' AND '" + date[1] + "') AND MAND=" + maND;
        }
        Cursor cursor = database.rawQuery(sqlChi, null);
        while (cursor.moveToNext()) {
            Chi chi = new Chi();
            chi.setIdMucChi(new MucChi(cursor.getString(0)));
            chi.setNgayChi(cursor.getString(1));
            chi.setSoTienChi(cursor.getInt(2));
            chi.setDienGiaiChi(cursor.getString(3));
            chi.setIdTaiKhoan(cursor.getInt(4));
            chiAdapter.add(chi);
        }
        cursor.close();
        chiAdapter.notifyDataSetChanged();
    }

}