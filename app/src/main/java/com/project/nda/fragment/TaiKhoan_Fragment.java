package com.project.nda.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.nda.DuLieu.DuLieuTaiKhoan;
import com.project.nda.Support.DinhDangNhapTienTe;
import com.project.nda.Support.DinhDangTienTe;
import com.project.nda.thuchicanhan.R;

public class TaiKhoan_Fragment extends Fragment {

    private View view;
    TextView txtTienMat, txtATM;
    ImageButton btnTienMat, btnATM;
    String maND;
    Intent intent;

    DinhDangTienTe dinhDangTienTe = new DinhDangTienTe();
    DuLieuTaiKhoan duLieuTaiKhoan = new DuLieuTaiKhoan();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_taikhoan, container, false);
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

        //Lấy mã người dùng khi đã đăng nhập
        intent=getActivity().getIntent();
        maND=intent.getStringExtra("MAND");
        LoadTaiKhoan();

    }

    private void LoadTaiKhoan() {
        String getTienMat = duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), 1, maND);
        String getATM = duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), 2, maND);
        getTienMat = dinhDangTienTe.DinhDangTextView(getContext(),getTienMat);
        getATM = dinhDangTienTe.DinhDangTextView(getContext(),getATM);
        txtTienMat.setText(getTienMat);
        txtATM.setText(getATM);
    }

    //Nhập hoặc cập nhật tiền trong các tài khoản
    private void insertOrUpdateMoney(final TextView txt, final int idLoaiTaiKhoan){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.nhaptien);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.setTitle("Nhập vào số tiền: ");

        final EditText edtMoney= (EditText) dialog.findViewById(R.id.edtMoney);
        edtMoney.setRawInputType(Configuration.KEYBOARD_12KEY);
        edtMoney.addTextChangedListener(new DinhDangNhapTienTe(edtMoney));
        edtMoney.setText(duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), idLoaiTaiKhoan, maND));

        Button btnAgree = (Button) dialog.findViewById(R.id.btnAgree);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moneyFomat = edtMoney.getText().toString().replace(",", "");
                if(moneyFomat.equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Chưa nhập số tiền", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(moneyFomat)<0){
                    Toast.makeText(getContext(), "Số tiền nhập vào phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                // kiểm tra tài khoản theo user nếu tài khoản chưa tồn tại thì insert
                // nếu tài khoản tồn tại thì update
                int result = duLieuTaiKhoan.CapNhatTaiKhoan(getContext(),idLoaiTaiKhoan, maND, moneyFomat);
                if(result == 1)
                {
                    Toast.makeText(getActivity(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                }
                else if(result == 2)
                {
                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                else  if(result == 3)
                {
                    Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_SHORT).show();

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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            try{
                LoadTaiKhoan();

            }catch (Exception e)
            {

            }
        }
    }
}
