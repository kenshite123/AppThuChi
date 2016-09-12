package com.project.nda.thuchicanhan;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.nda.DuLieu.DuLieuMucChi;
import com.project.nda.DuLieu.DuLieuMucThu;
import com.project.nda.DuLieu.DuLieuTaiKhoan;

public class ResetDataActivity extends AppCompatActivity {

    Button btnReset;
    AlertDialog.Builder builder;
    DuLieuTaiKhoan duLieuTaiKhoan = new DuLieuTaiKhoan();
    DuLieuMucThu duLieuMucThu = new DuLieuMucThu();
    DuLieuMucChi duLieuMucChi = new DuLieuMucChi();

    Intent intent;
    String maND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lammoidulieu);

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
                        duLieuTaiKhoan.LamMoiDulieuTaiKhoan(getApplicationContext(),maND);
                        duLieuMucChi.LamMoiDulieuChi(getApplicationContext(),maND);
                        duLieuMucThu.LamMoiDuLieuThu(getApplicationContext(),maND);
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
