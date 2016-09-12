package com.project.nda.thuchicanhan;

import android.app.ActionBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.project.nda.DuLieu.DuLieuThongKe;
import com.project.nda.Adapter.ChiAdapter;
import com.project.nda.Adapter.MergeAdapter;
import com.project.nda.Adapter.ThuAdapter;
import com.project.nda.Support.DateProcess;

public class ShowDetailReportActivity extends AppCompatActivity {

    ListView lvData;
    ChiAdapter chiAdapter;
    ThuAdapter thuAdapter;

    SQLiteDatabase database= null;
    String maND;

    DuLieuThongKe duLieuThongKe = new DuLieuThongKe();

    MergeAdapter mergeAdapter=new MergeAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaochitiet);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        lvData= (ListView) findViewById(R.id.lvData);
        chiAdapter=new ChiAdapter(this, R.layout.item_chitiet_chi);
        thuAdapter=new ThuAdapter(this, R.layout.item_chitiet_thu);

        mergeAdapter.addAdapter(chiAdapter);
        mergeAdapter.addAdapter(thuAdapter);

        lvData.setAdapter(mergeAdapter);

        Intent intent=getIntent();
        int loai=intent.getIntExtra("LOAI", 1);
        maND=intent.getStringExtra("MAND");


        switch (loai){
            case 1:{
                String s= intent.getStringExtra("NGAY");
                String[] arr=s.split("/");
                String date=arr[2] + "-" + arr[1] + "-" + arr[0];
                duLieuThongKe.DuLieuThongKeChi(getApplicationContext(), chiAdapter, maND, loai, date);
                duLieuThongKe.DuLieuThongKeThu(getApplicationContext(),thuAdapter, maND, loai, date);
                break;
            }
            case 2:{
                String month= intent.getStringExtra("THANG");
                String[] arr=month.split("/");
                String startDate=arr[1] + "-" + arr[0] + "-01";
                String endDate=arr[1] + "-" + arr[0] + "-" +
                        DateProcess.getLastDayOfMonth(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                //Toast.makeText(ShowDetailReportActivity.this, startDate + "\n" + endDate, Toast.LENGTH_SHORT).show();
                duLieuThongKe.DuLieuThongKeChi(getApplicationContext(), chiAdapter, maND, loai, startDate, endDate);
                duLieuThongKe.DuLieuThongKeThu(getApplicationContext(),thuAdapter, maND, loai, startDate, endDate);

                break;
            }
            case 3:{
                String year= intent.getStringExtra("NAM");
                String startDate=year + "-01-01";
                String endDate=year + "-12-31";
                //Toast.makeText(ShowDetailReportActivity.this, startDate + "\n" + endDate, Toast.LENGTH_SHORT).show();
                duLieuThongKe.DuLieuThongKeChi(getApplicationContext(), chiAdapter, maND, loai, startDate, endDate);
                duLieuThongKe.DuLieuThongKeThu(getApplicationContext(),thuAdapter, maND, loai, startDate, endDate);
                break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}