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
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.nda.DuLieu.DuLieuDangKy;
import com.project.nda.Support.EmailValidator;

public class RegisterActivity extends AppCompatActivity {

    EditText edtEmailRegister, edtPassRegister, edtConfirmPassRegister;
    Button btnRegister;
    ImageButton btnRegisterToLogin;

    EmailValidator emailValidator;
    DuLieuDangKy duLieuDangKy = new DuLieuDangKy();

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        final ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmailRegister = (EditText) findViewById(R.id.edtEmailRegister);
        edtPassRegister = (EditText) findViewById(R.id.edtPassRegister);
        edtConfirmPassRegister = (EditText) findViewById(R.id.edtConfrimPassRegister);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegisterToLogin = (ImageButton) findViewById(R.id.btnRegisterToLogin);
        createAlertBuilder();

    }

    private void createAlertBuilder() {
        builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Đăng ký thành công!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });
        builder.create();
    }

    private void addEvents() {
        btnRegisterToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailRegister = edtEmailRegister.getText().toString();
                String passRegister = edtPassRegister.getText().toString();
                String confirmPassRegister = edtConfirmPassRegister.getText().toString();

                emailValidator = new EmailValidator();
                if (!emailValidator.validate(emailRegister)) { // nhập sai định dạng
                    Toast.makeText(RegisterActivity.this, "Vui lòng kiểm tra lại email !", Toast.LENGTH_SHORT).show();
                }
                else if (emailRegister.equalsIgnoreCase("") ||
                          passRegister.equalsIgnoreCase("") ||
                            confirmPassRegister.equalsIgnoreCase("")) { // rỗng
                    Toast.makeText(RegisterActivity.this,
                            "Chưa nhập đủ thông tin ! Vui lòng kiểm tra lại !",
                            Toast.LENGTH_SHORT).show();
                }
                else if (!passRegister.equalsIgnoreCase(confirmPassRegister)) { // so sánh 2 mật khẩu
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại chưa trùng khớp !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    long r =  duLieuDangKy.DangKy(getApplicationContext(),emailRegister, passRegister);
                    if(r > 0)
                    {
                        builder.show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,
                                "Chưa nhập đủ thông tin ! Vui lòng kiểm tra lại !",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}