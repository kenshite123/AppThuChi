package com.project.nda.thuchicanhan;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.project.nda.adapter.ChiAdapter;
import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.adapter.ThuAdapter;
import com.project.nda.model.Chi;
import com.project.nda.model.MucChi;
import com.project.nda.model.MucThu;
import com.project.nda.model.Thu;
import com.project.nda.support.DateProcess;
import com.project.nda.support.MergeAdapter;

public class ShowDetailReportActivity extends AppCompatActivity {

    ListView lvData;
    ChiAdapter chiAdapter;
    ThuAdapter thuAdapter;

    SQLiteDatabase database;
    String maND;

    MergeAdapter mergeAdapter=new MergeAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_report);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        lvData= (ListView) findViewById(R.id.lvData);
        chiAdapter=new ChiAdapter(this, R.layout.item_chi_details);
        thuAdapter=new ThuAdapter(this, R.layout.item_thu_details);

        mergeAdapter.addAdapter(chiAdapter);
        mergeAdapter.addAdapter(thuAdapter);

        lvData.setAdapter(mergeAdapter);

        Intent intent=getIntent();
        int loai=intent.getIntExtra("LOAI", 1);
        maND=intent.getStringExtra("MAND");
        //Toast.makeText(this, maND, Toast.LENGTH_SHORT).show();
        switch (loai){
            case 1:{
                String s= intent.getStringExtra("NGAY");
                String[] arr=s.split("/");
                String date=arr[2] + "-" + arr[1] + "-" + arr[0];
                //Toast.makeText(ShowDetailReportActivity.this, "Loại: " + loai + "\nNgày: " + date, Toast.LENGTH_SHORT).show();
                LoadDataChi(loai, date);
                LoadDataThu(loai, date);
                break;
            }
            case 2:{
                String month= intent.getStringExtra("THANG");
                String[] arr=month.split("/");
                String startDate=arr[1] + "-" + arr[0] + "-01";
                String endDate=arr[1] + "-" + arr[0] + "-" +
                        DateProcess.getLastDayOfMonth(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                //Toast.makeText(ShowDetailReportActivity.this, startDate + "\n" + endDate, Toast.LENGTH_SHORT).show();
                LoadDataChi(loai, startDate, endDate);
                LoadDataThu(loai, startDate, endDate);
                break;
            }
            case 3:{
                String year= intent.getStringExtra("NAM");
                String startDate=year + "-01-01";
                String endDate=year + "-12-31";
                //Toast.makeText(ShowDetailReportActivity.this, startDate + "\n" + endDate, Toast.LENGTH_SHORT).show();
                LoadDataChi(loai, startDate, endDate);
                LoadDataThu(loai, startDate, endDate);
                break;
            }
        }
    }

    public void LoadDataChi(int loai, String... date){
        chiAdapter.clear();
        database = openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME, MODE_PRIVATE, null);
        String sqlChi;
        if(loai==1) {
            sqlChi = "SELECT MUCCHI.MUCCHI, CHI.NGAYCHI, CHI.SOTIENCHI, CHI.DIENGIAICHI " +
                    "FROM MUCCHI INNER JOIN CHI ON MUCCHI.IDMUCCHI=CHI.IDMUCCHI " +
                    "WHERE NGAYCHI='" + date[0] + "' AND MAND=" + maND;
        }else {
            sqlChi = "SELECT MUCCHI.MUCCHI, CHI.NGAYCHI, CHI.SOTIENCHI, CHI.DIENGIAICHI " +
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
            chiAdapter.add(chi);
        }
        cursor.close();
        chiAdapter.notifyDataSetChanged();
    }

    public void LoadDataThu(int loai, String... date){
        thuAdapter.clear();
        database=openOrCreateDatabase(GetDataFromAssetsAdapter.DATABASE_NAME, MODE_PRIVATE, null);
        String sqlThu;
        if(loai==1){
            sqlThu="SELECT MUCTHU.MUCTHU, THU.NGAYTHU, THU.SOTIENTHU, THU.DIENGIAITHU " +
                    "FROM MUCTHU INNER JOIN THU ON MUCTHU.IDMUCTHU=THU.IDMUCTHU " +
                    "WHERE NGAYTHU='" + date[0] +"' AND MAND=" + maND;
        }else{
            sqlThu="SELECT MUCTHU.MUCTHU, THU.NGAYTHU, THU.SOTIENTHU, THU.DIENGIAITHU " +
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
            thuAdapter.add(thu);
        }
        thuAdapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}