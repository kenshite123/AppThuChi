package com.project.nda.thuchicanhan;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.nda.GetData.UserGetData;

public class ChangePasswordActivity extends AppCompatActivity {


    EditText edtPassOld, edtPassNew, edtPassNewConfirm;
    Button btnChangePass;
    AlertDialog.Builder builder;

    Intent intent;
    String maND;

    UserGetData userGetData = new UserGetData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        adEvents();
    }

    private void addControls() {
        edtPassOld = (EditText) findViewById(R.id.edtPassOld);
        edtPassNew = (EditText) findViewById(R.id.edtPassNew);
        edtPassNewConfirm = (EditText) findViewById(R.id.edtPassNewConfirm);
        btnChangePass = (Button) findViewById(R.id.btnChangePass);
    }

    private void adEvents() {
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLyDoiMatKhau();
            }
        });
    }

    private void XuLyDoiMatKhau() {
        String passOld = edtPassOld.getText().toString();
        String passNew = edtPassNew.getText().toString();
        String passConfirm = edtPassNewConfirm.getText().toString();

        intent = getIntent();
        maND = intent.getStringExtra("MAND");
        String password = userGetData.GetPassWord(getApplicationContext(), maND);
        if (passOld.equalsIgnoreCase("")) {
            Toast.makeText(ChangePasswordActivity.this,
                    "Chưa nhập mật khẩu cũ!",
                    Toast.LENGTH_SHORT).show();

        }
        else if(!password.equalsIgnoreCase(passOld))
        {
            Toast.makeText(ChangePasswordActivity.this,
                    "Mật khẩu cũ không đúng!",
                    Toast.LENGTH_SHORT).show();
        }
        else if (passNew.equalsIgnoreCase("")) {
            Toast.makeText(ChangePasswordActivity.this,
                    "Chưa nhập mật khẩu mới!",
                    Toast.LENGTH_SHORT).show();
        }
        else  if(passNew.length() < 6)
        {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải lớn hơn 6 ký tự!", Toast.LENGTH_SHORT).show();
        }
        else if (passConfirm.equalsIgnoreCase("")) {
            Toast.makeText(ChangePasswordActivity.this,
                    "Chưa nhập lại khẩu mới!",
                    Toast.LENGTH_SHORT).show();
        }

        else if (!passNew.equalsIgnoreCase(passConfirm)) { // so sánh 2 mật khẩu
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu nhập lại chưa trùng khớp !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int r = userGetData.UpdatePassword(getApplicationContext(), maND, passNew);
            if(r > 0)
            {
                builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Cập nhật mật khẩu thành công!");
                builder.setCancelable(true);
                builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create();
                builder.show();
            }
            else
            {
                Toast.makeText(ChangePasswordActivity.this,
                        "Cập nhật thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}