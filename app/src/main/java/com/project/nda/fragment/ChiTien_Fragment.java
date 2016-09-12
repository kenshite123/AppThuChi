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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.nda.DuLieu.DuLieuLoaiTaiKhoan;
import com.project.nda.DuLieu.DuLieuMucChi;
import com.project.nda.DuLieu.DuLieuTaiKhoan;
import com.project.nda.Adapter.LoaiTaiKhoanAdapter;
import com.project.nda.Adapter.MucChiExpandListAdapter;
import com.project.nda.Model.LoaiChi;
import com.project.nda.Model.LoaiTaiKhoan;
import com.project.nda.Model.MucChi;
import com.project.nda.Support.DinhDangNgay;
import com.project.nda.Support.DinhDangNhapTienTe;
import com.project.nda.Support.DinhDangTienTe;
import com.project.nda.thuchicanhan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChiTien_Fragment extends Fragment {

    private View view;
    private Dialog dialogdsMucChi;
    private Dialog dialogTaiKhoan;

    CardView cvTaiKhoanC, cvMucChi, cvNgayChi;
    TextView txtMucChi, txtNgayChi, txtTaiKhoanChiC, txtTienMat, txtATM;
    EditText edtNhapTienChi, edtGhiChuChi;
    Button btnLuuChi;

    ArrayList<LoaiChi> dsLoaiChi = new ArrayList<>();
    HashMap<LoaiChi, List<MucChi>> dsMucChi = new HashMap<LoaiChi, List<MucChi>>();
    ArrayList<LoaiTaiKhoan> dsLoaiTaiKhoan = new ArrayList<>();

    DuLieuLoaiTaiKhoan duLieuLoaiTaiKhoan = new DuLieuLoaiTaiKhoan();
    DuLieuTaiKhoan duLieuTaiKhoan = new DuLieuTaiKhoan();
    DuLieuMucChi duLieuMucChi = new DuLieuMucChi();


    DinhDangNgay dinhDangNgay = new DinhDangNgay();

    DinhDangTienTe dinhDangTienTe = new DinhDangTienTe();

    Intent intent;
    String maND;

    Calendar cal;

    int idLoaiTaiKhoan, idTaiKhoan, idMucChi, money;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chitien, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
        duLieuMucChi.DanhSachMucChi(getContext(), dsLoaiChi, dsMucChi);
        duLieuLoaiTaiKhoan.DanhSachTaiKhoan(getContext(), dsLoaiTaiKhoan );
    }

    private void addControls() {
        edtNhapTienChi = (EditText) view.findViewById(R.id.edtNhapTienChi);
        edtNhapTienChi.setRawInputType(Configuration.KEYBOARD_12KEY);
        cvNgayChi = (CardView) view.findViewById(R.id.cvNgayChi);
        cvMucChi = (CardView) view.findViewById(R.id.cvMucChi);
        cvTaiKhoanC = (CardView) view.findViewById(R.id.cvTaiKhoanC);
        txtNgayChi = (TextView) view.findViewById(R.id.txtNgayChi);
        txtMucChi = (TextView) view.findViewById(R.id.txtMucChi);
        txtTaiKhoanChiC = (TextView) view.findViewById(R.id.txtTaiKhoanC);
        txtTienMat = (TextView) view.findViewById(R.id.txtTienMat);
        txtATM = (TextView) view.findViewById(R.id.txtATM);
        btnLuuChi= (Button) view.findViewById(R.id.btnLuuChi);
        edtGhiChuChi= (EditText) view.findViewById(R.id.edtGhiChuChi);
        intent=getActivity().getIntent();
        maND=intent.getStringExtra("MAND");
        LoadTaiKhoan();

        //Lấy ngày hiện tại
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        txtNgayChi.setText(currentDateandTime);
    }
    private void addEvents() {
        cvTaiKhoanC.setOnClickListener(new View.OnClickListener() {
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
                LoaiTaiKhoanAdapter taiKhoanAdapter = new LoaiTaiKhoanAdapter(getActivity(), R.layout.listview_item, dsLoaiTaiKhoan);
                lvTaiKhoan.setAdapter(taiKhoanAdapter);

                lvTaiKhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LoaiTaiKhoan loaiTaiKhoan = dsLoaiTaiKhoan.get(i);
                        txtTaiKhoanChiC.setText(loaiTaiKhoan.getTaiKhoan());
                        idLoaiTaiKhoan=loaiTaiKhoan.getIdLoaiTaiKhoan();
                        // lấy ra idTaiKhoan để thêm vào table Chi
                        // nếu idTaiKhoan != 0 => tồn tại 1 tài khoản với MaND & Loại tài khoản
                        // tức là user này đã nhập số tiền vào tài khoản. Ví dụ: user chinh - Ví : 100.000 đ => idTaiKhoan != 0
                        // nếu idTaiKhoan == 0 => chưa nhập số tiền. Ví dụ user chính - ATM chưa nhập số tiền => idTaiKhoan = 0
                        idTaiKhoan=duLieuTaiKhoan.LayIdTaiKhoan(getContext(), idLoaiTaiKhoan, maND); // lấy thành công
                        //Toast.makeText(getContext(), idTaiKhoan+"", Toast.LENGTH_SHORT).show();
                        dialogTaiKhoan.dismiss();
                    }
                });
            }
        });
        cvNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail

                        //Lưu vết lại biến ngày hoàn thành
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dinhDangNgay.DinhDangDatePicker(getContext(), txtNgayChi, dayOfMonth, monthOfYear, year);
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = txtNgayChi.getText() + "";
                String strArrtmp[] = s.split("/");
                int ngay = Integer.parseInt(strArrtmp[0]);
                int thang = Integer.parseInt(strArrtmp[1]) - 1;
                int nam = Integer.parseInt(strArrtmp[2]);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, thang, ngay);
                datePickerDialog.setTitle("Chọn ngày hoàn thành");
                datePickerDialog.show();
            }
        });
        cvMucChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogdsMucChi = new Dialog(getContext());
                dialogdsMucChi.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogdsMucChi.setContentView(R.layout.expandlistview_mucchi);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogdsMucChi.setCancelable(true);
                dialogdsMucChi.show();
                ExpandableListView expListView = (ExpandableListView) dialogdsMucChi.findViewById(R.id.lvExp);
                MucChiExpandListAdapter sadapter = new MucChiExpandListAdapter(getContext(), dsLoaiChi, dsMucChi);
                expListView.setAdapter(sadapter);
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        //Nothing here ever fires
                        MucChi mucChi = (MucChi) dsMucChi.get(dsLoaiChi.get(groupPosition)).get(childPosition);
                        txtMucChi.setText(mucChi.getMucChi());
                        // lấy ra idMucChi để insert vào table
                        idMucChi=mucChi.getIdMucChi(); // lấy thành công
                        //Toast.makeText(getContext(), idMucChi+"", Toast.LENGTH_SHORT).show();
                        dialogdsMucChi.dismiss();
                        return true;
                    }
                });
            }
        });
        edtNhapTienChi.setRawInputType(Configuration.KEYBOARD_12KEY);
        edtNhapTienChi.addTextChangedListener(new DinhDangNhapTienTe(edtNhapTienChi));
        btnLuuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                xuLyLuuChi();
            }
        });

    }

    //Lưu thông tin Mục chi
    private void xuLyLuuChi() {

        String moneyFomat = edtNhapTienChi.getText().toString().replace(",", "");
        if(moneyFomat.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Chưa nhập số tiền", Toast.LENGTH_SHORT).show();
            edtNhapTienChi.requestFocus();
            return;
        }

        // chưa tồn tại tài khoản này
        if(idTaiKhoan==0){
            Toast.makeText(getContext(), "Tài khoản này không có tiền. Vui lòng chọn lại !", Toast.LENGTH_SHORT).show();
            return;
        }

        /*String[] arrSoTien=soTien.split("[.]");// VD nhập $50,000.00 chuỗi trả về tiền = $50,000
        String tien=arrSoTien[0].replace("$","").replace(",", ""); // xong*/
        money=Integer.parseInt(moneyFomat);
        int moneyInDatabase=Integer.parseInt(duLieuTaiKhoan.LayDuLieuTaiKhoan(getContext(), idLoaiTaiKhoan, maND));

        /*Toast.makeText(getContext(),
                "money in edittext: " + money + "\nmoney in database: " + moneyInDatabase,
                Toast.LENGTH_SHORT).show();*/

        if(money<=0){
            Toast.makeText(getContext(), "Số tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            edtNhapTienChi.requestFocus();
            return;
        }

        // số tiền có trong tài khoản < số tiền nhập vào => nhập lại
        if(moneyInDatabase < money){
            Toast.makeText(getContext(), "Số tiền phải nhỏ hơn số tiền trong tài khoản", Toast.LENGTH_SHORT).show();
            //edtNhapTienChi.setText("");
            edtNhapTienChi.requestFocus();
            return;
        }

        if(txtTaiKhoanChiC.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getContext(), "Chưa chọn tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        if(txtMucChi.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getContext(), "Chưa chọn mục cần chi", Toast.LENGTH_SHORT).show();
            return;
        }
        String dateAfterChange = dinhDangNgay.DinhDangNgay(getContext(),txtNgayChi);
        if(duLieuMucChi.ThemChi(
                getContext(), idMucChi, idTaiKhoan, maND,
                dateAfterChange, money,
                edtGhiChuChi.getText().toString())==-1){
            Toast.makeText(getContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            // lấy ra money trong database theo 2 tham số idLoaiTaiKhoan & maND
            // sau khi thêm thì trừ ra => update lại
            int moneyAfterInsert= moneyInDatabase-money;

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