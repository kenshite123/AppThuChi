package com.project.nda.thuchicanhan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.nda.adapter.GetDataFromAssetsAdapter;
import com.project.nda.GetData.LoginGetData;
import com.project.nda.support.EmailValidator;

public class MainActivity extends AppCompatActivity {


    EditText edtEmail;
    EditText edtPass;
    Button btnLogin;
    ImageButton btnToFoget;
    ImageButton btnToRegister;
    CheckBox checkBox;

    GetDataFromAssetsAdapter dataAdapter = new GetDataFromAssetsAdapter();

    SQLiteDatabase database = null ;
    LoginGetData loginGetData = new LoginGetData();
    EmailValidator emailValidator   = new EmailValidator();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAdapter.coppyDatabaseFromAssetsToSystem(MainActivity.this);
        addControls(); //Thao tác trên các controls
        addEvents(); //Xử lý các sự kiện của control

    }

    //Thao tác các controls
    private void addControls() {
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnToFoget = (ImageButton) findViewById(R.id.btnToFogetPass);
        btnToRegister = (ImageButton) findViewById(R.id.btnToRegister);
        checkBox = (CheckBox) findViewById(R.id.chkSave);
    }

    //Xử lý sự kiện các controls
    private void addEvents() {
        //Sự kiện đi đến Actitvity Đăng ký người dùng
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        // Sự kiện đi đến Activity Quên mật khẩu
        btnToFoget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestorePassActivity.class);
                startActivity(intent);
            }
        });
        //sự kiện đăng nhập vào tài khoản
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLogin();
            }
        });
    }


   private void xuLyLogin() {
       String email = edtEmail.getText().toString();
       String pass = edtPass.getText().toString();
       if (email.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
           Toast.makeText(MainActivity.this, "Chưa nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
       } else if (!emailValidator.validate(email)) {
           Toast.makeText(MainActivity.this, "Email không hợp lệ !", Toast.LENGTH_SHORT).show();
       }
       int count = loginGetData.CheckInfoLogin(getApplicationContext(), email, pass);
       if (count == 0) { // ko tồn tại user này
           Toast.makeText(MainActivity.this, "Sai thông tin đăng nhâp!", Toast.LENGTH_SHORT).show();
       } else {
           String maND = loginGetData.GetMaND(getApplication(),email, pass);
           Intent intent = new Intent(MainActivity.this, ManageActivity.class);
           intent.putExtra("MAND", maND);
           startActivity(intent);
       }
   }
    public void savingPreferences(){
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre=getSharedPreferences
                ("login", MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        String email=edtEmail.getText().toString();
        String pass=edtPass.getText().toString();
        boolean check=checkBox.isChecked();
        if(!check){
            editor.clear();
        }else{
            //lưu vào editor
            editor.putString("email", email);
            editor.putString("password", pass);
            editor.putBoolean("checked", check);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }

    public void restoringPreferences(){
        SharedPreferences pre=getSharedPreferences
                ("login",MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk=pre.getBoolean("checked", false);
        // nếu true thì nhảy vào màn hình khác
        if(bchk){
            Intent intent=new Intent(MainActivity.this, ManageActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
    }

}