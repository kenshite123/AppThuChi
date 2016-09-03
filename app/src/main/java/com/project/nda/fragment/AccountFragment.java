package com.project.nda.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.nda.function.TaiKhoanGetData;
import com.project.nda.thuchicanhan.R;

public class AccountFragment extends Fragment {

    private View view;
    TextView txtTienMat, txtATM;
    ImageButton btnTienMat, btnATM;
    SQLiteDatabase database;
    String maND;
    Intent intent;

    TaiKhoanGetData taiKhoanGetData = new TaiKhoanGetData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtTienMat= (TextView) view.findViewById(R.id.txtTienMat);
        txtATM= (TextView) view.findViewById(R.id.txtATM);
        btnTienMat= (ImageButton) view.findViewById(R.id.btnTienMat);
        btnATM= (ImageButton) view.findViewById(R.id.btnATM);
        intent=getActivity().getIntent();
        maND=intent.getStringExtra("MAND");
        LoadTaiKhoan();

    }
    private void LoadTaiKhoan() {
        txtTienMat.setText(taiKhoanGetData.getMoney(getContext(), 1, maND));
        txtATM.setText(taiKhoanGetData.getMoney(getContext(), 2, maND));
    }

    private void insertOrUpdateMoney(final TextView txt, final int idLoaiTaiKhoan){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.insertmoney);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setTitle("Nhập vào số tiền: ");

        final EditText edtMoney= (EditText) dialog.findViewById(R.id.edtMoney);
        edtMoney.setRawInputType(Configuration.KEYBOARD_12KEY);
        edtMoney.setText(taiKhoanGetData.getMoney(getContext(), idLoaiTaiKhoan, maND));

        Button btnAgree= (Button) dialog.findViewById(R.id.btnAgree);

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soTien = edtMoney.getText().toString();
                if(soTien.equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Chưa nhập số tiền", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(soTien)<0){
                    Toast.makeText(getContext(), "Số tiền nhập vào phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("loi", soTien);
                // kiểm tra tài khoản theo user nếu tài khoản chưa tồn tại thì insert
                // nếu tài khoản tồn tại thì update
                int result = taiKhoanGetData.UpdateAccount(getContext(),idLoaiTaiKhoan, maND, soTien);
                if(result == 1)
                {
                    Toast.makeText(getActivity(), "Update thất bại", Toast.LENGTH_SHORT).show();

                }
                else if(result == 2)
                {
                    Toast.makeText(getActivity(), "Update thành công", Toast.LENGTH_SHORT).show();
                }
                else  if(result == 3)
                {
                    Toast.makeText(getActivity(), "Insert thất bại", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Insert thành công", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
                LoadTaiKhoan();
            }
        });
        dialog.show();

    }


    private void addEvents() {
        btnTienMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chỗ này idLoaiTaiKhoan = 1 ==> xem trong database sẽ rõ
                insertOrUpdateMoney(txtTienMat, 1);
            }
        });
        btnATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chỗ này idLoaiTaiKhoan = 2 ==> xem trong database sẽ rõ
                insertOrUpdateMoney(txtATM, 2);
            }
        });
    }
}
