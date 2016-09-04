package com.project.nda.thuchicanhan;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.nda.function.MucChiGetData;
import com.project.nda.function.MucThuGetData;
import com.project.nda.function.TaiKhoanGetData;

public class ResetDataActivity extends AppCompatActivity {

    Button btnReset;
    AlertDialog.Builder builder;
    TaiKhoanGetData taiKhoanGetData = new  TaiKhoanGetData();
    MucThuGetData mucThuGetData = new MucThuGetData();
    MucChiGetData m,mucChiGetData = new MucChiGetData();

    Intent intent;
    String maND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_data);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();

    }

    private void addControls() {
        btnReset = (Button) findViewById(R.id.btnReset);
        intent = getIntent();
        maND = intent.getStringExtra("MAND");
    }

    private void addEvents() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ResetDataActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage(" Xác nhận lần nữa!");
                builder.setCancelable(true);
                builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taiKhoanGetData.ReSetDataTaiKhoan(getApplicationContext(),maND);
                        mucChiGetData.ReSetDataChi(getApplicationContext(),maND);
                        mucThuGetData.ReSetDataThu(getApplicationContext(),maND);
                        finish();
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
