package com.project.nda.thuchicanhan;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RestorePassActivity extends AppCompatActivity {

    EditText edtEmailRestore;
    Button btnRestore;
    ImageButton btnRestoreToLogin;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoiphucmatkhau);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmailRestore = (EditText) findViewById(R.id.edtEmailRestore);
        btnRestore = (Button) findViewById(R.id.btnRestore);
        btnRestoreToLogin = (ImageButton) findViewById(R.id.btnRestoreToLogin);

        // Tạo ALert Dialog Thông báo đặt lại mật khẩu
    }

    private void createAlertBuiler() {
        builder = new AlertDialog.Builder(RestorePassActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Yêu cầu đặt lại mật khẩu của bạn đã thực hiện, vui lòng kiểm tra email của bạn");
        builder.setCancelable(false);
        builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create();
    }

    private void addEvents() {
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });
        btnRestoreToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
