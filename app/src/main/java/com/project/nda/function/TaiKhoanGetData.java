package com.project.nda.function;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.nda.adapter.GetDataFromAssetsAdapter;

/**
 * Created by ndact on 31/08/2016.
 */
public class TaiKhoanGetData {
    SQLiteDatabase database =  null;

    public String getMoney(Context context, int idLoaiTaiKhoan, String maND){
        String money = "";
        database=context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,
                Context.MODE_PRIVATE,
                null);
        String sql="SELECT SOTIEN FROM TAIKHOAN WHERE IDLOAITAIKHOAN=" + idLoaiTaiKhoan + " AND MAND=" + maND;
        Cursor cursor=database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            money = cursor.getString(0);
        }
        cursor.close();
        return money;

    }

    public int getIdTaiKhoan(Context context, int idLoaiTaiKhoan, String maND){
        int idTaiKhoan = 0;
        database=context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME,
                Context.MODE_PRIVATE,
                null);
        String sql="SELECT idTaiKhoan FROM TAIKHOAN WHERE IDLOAITAIKHOAN=" + idLoaiTaiKhoan + " AND MAND=" + maND;
        Cursor cursor=database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            idTaiKhoan = cursor.getInt(0);
        }
        cursor.close();
        return idTaiKhoan;
    }

    public int UpdateAccount(Context context, int idLoaiTaiKhoan, String maND, String soTien)
    {
        int flag = 0;
        database = context.openOrCreateDatabase(
                GetDataFromAssetsAdapter.DATABASE_NAME,
                Context.MODE_PRIVATE,
                null);
        // ở đây tiền mặt là ví ==> idLoaiTaiKhoan=1 ( xem trong database )

        int count = 0;
        String query = "SELECT COUNT(*) FROM TAIKHOAN WHERE idLoaiTaiKhoan="
                + idLoaiTaiKhoan + " AND MaND='" + maND + "'";
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        if (count > 0) { // tồn tại tài khoản theo MaND ==> update lại số tiền
            String sql = "SELECT idTaiKhoan FROM TAIKHOAN WHERE idLoaiTaiKhoan="
                    + idLoaiTaiKhoan + " AND MaND='" + maND + "'";
            Cursor cursorTK = database.rawQuery(sql, null);
            String idTaiKhoan = null;
            while (cursorTK.moveToNext()) {
                idTaiKhoan = cursorTK.getString(0);
            }
            cursorTK.close();

            ContentValues update = new ContentValues();
            update.put("SoTien", soTien);
            //database.execSQL();

            int u = database.update("TAIKHOAN", update, "idTaiKhoan=?", new String[]{idTaiKhoan});
            if (u == 0)
            {
                flag = 1; //Update thất bại
            }
            else
            {
                flag = 2; //Update thành công
            }
        }
        else
        {
            // chưa tồn tại tài khoản ==> insert số tiền
            ContentValues insert = new ContentValues();
            insert.put("idLoaiTaiKhoan", idLoaiTaiKhoan);
            insert.put("MaND", maND);
            insert.put("SoTien", Integer.parseInt(soTien));
            long i = database.insert("TaiKhoan", null, insert);
            if(i==-1)
            {
                flag = 3; //Insert thất bại
            }
            else
            {
                flag = 4; //Insert thành công
            }
        }
        return flag;
    }
    public void ReSetDataTaiKhoan(Context context, String maND) {
        database = context.openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME, android.content.Context.MODE_PRIVATE, null);
        database.delete("TAIKHOAN","MAND=?", new String[]{maND});
    }

}
