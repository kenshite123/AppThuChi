package com.project.nda.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.nda.DuLieu.DuLieuLoaiTaiKhoan;
import com.project.nda.DuLieu.DuLieuMucThu;
import com.project.nda.DuLieu.DuLieuTaiKhoan;
import com.project.nda.Adapter.LoaiTaiKhoanAdapter;
import com.project.nda.Adapter.MucThuAdapter;
import com.project.nda.Model.LoaiTaiKhoan;
import com.project.nda.Model.MucThu;
import com.project.nda.Support.DinhDangNgay;
import com.project.nda.Support.DinhDangNhapTienTe;
import com.project.nda.Support.DinhDangTienTe;
import com.project.nda.thuchicanhan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThuTien_Fragment extends Fragment {

    private View view;
    private Dialog dialogMucThu;
    private Dialog dialogTaiKhoan;

    ArrayList<MucThu> dsMucThu = new ArrayList<>();
    DuLieuMucThu duLieuMucThu = new DuLieuMucThu();

    ArrayList<LoaiTaiKhoan> dsloaiTaiKhoan = new ArrayList<>();
    DuLieuLoaiTaiKhoan duLieuLoaiTaiKhoan = new DuLieuLoaiTaiKhoan();
    LoaiTaiKhoanAdapter loaiTaiKhoanAdapter;

    DuLieuTaiKhoan duLieuTaiKhoan = new DuLieuTaiKhoan();
    DinhDangTienTe dinhDangTienTe = new DinhDangTienTe();
    DinhDangNgay dinhDangNgay = new DinhDangNgay();

    CardView cvTaiKhoan, cvMucThu, cvNgayThu;
    TextView txtTaiKhoan, txtMucThu, txtNgayThu, txtTienMat, txtATM;
    EditText edtNhapTienNhan, edtGhiChuThu;
    Button btnLuuThu;

    Intent intent;
    String maND;

    Calendar cal;

    int idLoaiTaiKhoan, idTaiKhoan, idMucThu, money;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thutien, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
        duLieuMucThu.DanhSachMucThu(getContext(), dsMucThu);
        duLieuLoaiTaiKhoan.DanhSachTaiKhoan(getContext(), dsloaiTaiKhoan);
        LoadTaiKhoan();
    }

    private void addControls() {
        edtNhapTienNhan = (EditText) view.findViewById(R.id.edtNhapTienNhan);
        edtNhapTienNhan.setRawInputType(Configuration.KEYBOARD_12KEY);
        edtNhapTienNhan.addTextChangedListener(new DinhDangNhapTienTe(edtNhapTienNhan));
        cvTaiKhoan = (CardView) view.findViewById(R.id.cvTaiKhoan);
        cvNgayThu = (CardView) view.findViewById(R.id.cvNgayThu);
        cvMucThu = (CardView) view.findViewById(R.id.cvMucThu);
        txtTaiKhoan = (TextView) view.findViewById(R.id.txtTaiKhoan);
        txtNgayThu = (TextView) view.findViewById(R.id.txtNgayThu);
        txtMucThu = (TextView) view.findViewById(R.id.txtMucChi);
        edtGhiChuThu = (EditText) view.findViewById(R.id.edtGhiChuThu);
        txtTienMat = (TextView) view.findViewById(R.id.txtTienMat);
        txtATM = (TextView) view.findViewById(R.id.txtATM);
        btnLuuThu = (Button) view.findViewById(R.id.btnLuuThu);
        intent=getActivity().getIntent();
        maND=intent.getStringExtra("MAND");
        LoadTaiKhoan();

        //Lấy ngày hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        txtNgayThu.setText(currentDateandTime);
    }

    private void addEvents() {
        cvTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTaiKhoan = new Dialog(getActivity());
                dialogTaiKhoan.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogTaiKhoan.setContentView(R.layout.listview_hienthichtietbaocao);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogTaiKhoan.setCancelable(true);
                dialogTaiKhoan.show();
                ListView lvTaiKhoan = (ListView) dialogTaiKhoan.findViewById(R.id.lvData);
                loaiTaiKhoanAdapter = new LoaiTaiKhoanAdapter(getActivity(),R.layout.listview_item, dsloaiTaiKhoan);
                lvTaiKhoan.setAdapter(loaiTaiKhoanAdapter);
                lvTaiKhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LoaiTaiKhoan loaiTaiKhoan = dsloaiTaiKhoan.get(i);
                        txtTaiKhoan.setText(loaiTaiKhoan.getTaiKhoan());
                        idLoaiTaiKhoan = loaiTaiKhoan.getIdLoaiTaiKhoan();
                        idTaiKhoan = duLieuTaiKhoan.LayIdTaiKhoan(getContext(), idLoaiTaiKhoan, maND); // lấy thành công
                        dialogTaiKhoan.dismiss();
                    }
                });
            }
        });
        cvMucThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMucThu = new Dialog(getActivity());
                dialogMucThu.setTitle("Chọn Mục Thu");
                dialogMucThu.setContentView(R.layout.listview_hienthichtietbaocao);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogMucThu.setCancelable(true);
                dialogMucThu.show();
                ListView listView = (ListView) dialogMucThu.findViewById(R.id.lvData);
                MucThuAdapter listviewAdapter = new MucThuAdapter(getActivity(),R.layout.listview_item, dsMucThu);
                listView.setAdapter(listviewAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MucThu mucThu = dsMucThu.get(i);
                        txtMucThu.setText(mucThu.getMucThu());
                        idMucThu = mucThu.getIdMucThu();
                        dialogMucThu.dismiss();
                    }
                });

            }
        });
        cvNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail
                        txtNgayThu.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                        //Lưu vết lại biến ngày hoàn thành
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dinhDangNgay.DinhDangDatePicker(getContext(), txtNgayThu, dayOfMonth, monthOfYear, year);
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = txtNgayThu.getText() + "";
                String strArrtmp[] = s.split("/");
                int ngay = Integer.parseInt(strArrtmp[0]);
                int thang = Integer.parseInt(strArrtmp[1]) - 1;
                int nam = Integer.parseInt(strArrtmp[2]);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, thang, ngay);
                datePickerDialog.setTitle("Chọn ngày hoàn thành");
                datePickerDialog.show();
            }
        });
        btnLuuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLyLuuThu();
            }
        });
    }

    private void XuLyLuuThu() {

        String moneyFomat = edtNhapTienNhan.getText().toString().replace(",", "");
        if(moneyFomat.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Chưa nhập số tiền", Toast.LENGTH_SHORT).show();
            edtNhapTienNhan.requestFocus();
            return;
        }
        if(txtTaiKhoan.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getContext(), "Chưa chọn tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        if(txtMucThu.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getContext(), "Chưa chọn mục thu", Toast.LENGTH_SHORT).show();
            return;
        }
        money=Integer.parseInt(moneyFomat);
        int moneyInDatabase=Integer.parseInt(duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), idLoaiTaiKhoan, maND));
        if(moneyInDatabase + money > 999999999)
        {
            Toast.makeText(getContext(), "Tổng tiền trong tài khoản phải dưới 1 tỷ", Toast.LENGTH_SHORT).show();
            edtNhapTienNhan.requestFocus();
            return;
        }
        if(money<=0){
            Toast.makeText(getContext(), "Số tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            edtNhapTienNhan.requestFocus();
            return;
        }
        String dayAfterChange = dinhDangNgay.DinhDangNgay(getContext(), txtNgayThu);
        if(duLieuMucThu.ThemThu(
                getContext(), idMucThu, idTaiKhoan, maND,
                dayAfterChange, money,
                edtGhiChuThu.getText().toString())==-1)
        {
            Toast.makeText(getContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            // lấy ra money trong database theo 2 tham số idLoaiTaiKhoan & maND
            // sau khi thêm thì trừ ra => update lại
            int moneyAfterInsert = moneyInDatabase + money;
            if(duLieuTaiKhoan.CapNhatTaiKhoan(getContext(), idLoaiTaiKhoan, maND, moneyAfterInsert+"")==2){
                // update thành công
                //Toast.makeText(getContext(), "update thành công", Toast.LENGTH_SHORT).show();
                LoadTaiKhoan();
            }
        }

    }

    private void LoadTaiKhoan()
    {
        String getTienMat = duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), 1, maND);
        String getATM = duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), 2, maND);
        getTienMat = dinhDangTienTe.DinhDangTextView(getContext(),getTienMat);
        getATM = dinhDangTienTe.DinhDangTextView(getContext(),getATM);
        txtTienMat.setText(getTienMat);
        txtATM.setText(getATM);
    }
    @Override
    public void onResume() {
        super.onResume();
        LoadTaiKhoan();
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