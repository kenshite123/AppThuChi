package com.project.nda.GetData;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.support.FormatMoney;

/**
 * Created by DELL on 9/5/2016.
 */
public class ThongKeGetData {

    SQLiteDatabase database = null;
    FormatMoney formatMoney=new FormatMoney();

    public void LoadData(Activity context, boolean loai, TextView txtChi, TextView txtThu, String maND, String... s){
        database=context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,
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
            txtChi.setText(formatMoney.FormatTextView(context, soTienChi+""));

            // THU
            String sqlThu="SELECT SUM(SOTIENTHU) AS TONGTIENTHU FROM THU WHERE NGAYTHU='" + s[0] + "' AND MAND='" + maND + "'";
            int soTienThu=0;
            Cursor cursorThu=database.rawQuery(sqlThu, null);
            while (cursorThu.moveToNext()){
                soTienThu=cursorThu.getInt(0);
            }
            cursorThu.close();
            txtThu.setText(formatMoney.FormatTextView(context, soTienThu+""));
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
            txtChi.setText(formatMoney.FormatTextView(context, soTienChi+""));

            // THU
            String sqlThu="SELECT SUM(SOTIENTHU) AS TONGTIENTHU FROM THU WHERE(NGAYTHU BETWEEN '" + s[0]
                    + "' AND '" + s[1] + "') AND MAND='" + maND + "'";
            int soTienThu=0;
            Cursor cursorThu=database.rawQuery(sqlThu, null);
            while (cursorThu.moveToNext()){
                soTienThu=cursorThu.getInt(0);
            }
            cursorThu.close();
            txtThu.setText(formatMoney.FormatTextView(context, soTienThu+""));
        }
    }
}