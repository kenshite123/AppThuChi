package com.project.nda.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
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

import com.project.nda.adapter.LoaiTaiKhoanAdapter;
import com.project.nda.adapter.MucThuAdapter;
import com.project.nda.function.LoaiTaiKhoanGetData;
import com.project.nda.function.MucThuGetData;
import com.project.nda.function.TaiKhoanGetData;
import com.project.nda.model.LoaiTaiKhoan;
import com.project.nda.model.MucThu;
import com.project.nda.thuchicanhan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecieveMoneyFragment extends Fragment {

    private View view;
    private Dialog dialogListMucThu;
    private Dialog dialogTaiKhoanC;

    ArrayList<MucThu> listDataMucThu = new ArrayList<>();
    MucThuGetData getDataMucThu = new MucThuGetData();

    ArrayList<LoaiTaiKhoan> listloaiTaiKhoan = new ArrayList<>();
    LoaiTaiKhoanGetData loaiTaiKhoanGetData = new LoaiTaiKhoanGetData();
    LoaiTaiKhoanAdapter taiKhoanAdapter;

    TaiKhoanGetData getDataTaiKhoan = new TaiKhoanGetData();


    CardView cvTaiKhoan, cvMucThu, cvNgayThu;
    TextView txtTaiKhoan, txtMucThu, txtNgayThu, txtTienMat, txtATM;
    EditText edtNhapTienNhan, edtGhiChuThu;
    Button btnLuuThu;

    Intent intent;
    String maND;
    SQLiteDatabase database = null;

    Calendar cal;
    Date dateFinish;

    int idLoaiTaiKhoan, idTaiKhoan, idMucThu, money;
    double moneyFormat;
    String format;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recievemoney, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
        getDataMucThu.ListMucThu(getContext(), listDataMucThu);
        loaiTaiKhoanGetData.ListTaiKhoan(getContext(), listloaiTaiKhoan);
        LoadTaiKhoan();
    }

    private void addControls() {
        edtNhapTienNhan = (EditText) view.findViewById(R.id.edtNhapTienNhan);
        edtNhapTienNhan.setRawInputType(Configuration.KEYBOARD_12KEY);
        cvTaiKhoan = (CardView) view.findViewById(R.id.cvTaiKhoan);
        cvNgayThu = (CardView) view.findViewById(R.id.cvNgayThu);
        cvMucThu = (CardView) view.findViewById(R.id.cvMucThu);
        txtTaiKhoan = (TextView) view.findViewById(R.id.txtTaiKhoan);
        txtNgayThu = (TextView) view.findViewById(R.id.txtNgayThu);
        txtMucThu = (TextView) view.findViewById(R.id.txtMucThu);
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
                dialogTaiKhoanC = new Dialog(getActivity());
                dialogTaiKhoanC.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogTaiKhoanC.setContentView(R.layout.listview_showdata);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogTaiKhoanC.setCancelable(true);
                dialogTaiKhoanC.show();
                ListView lvTaiKhoan = (ListView) dialogTaiKhoanC.findViewById(R.id.lvData);
                taiKhoanAdapter = new LoaiTaiKhoanAdapter(getActivity(),R.layout.listview_item, listloaiTaiKhoan);
                lvTaiKhoan.setAdapter(taiKhoanAdapter);
                lvTaiKhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LoaiTaiKhoan loaiTaiKhoan = listloaiTaiKhoan.get(i);
                        txtTaiKhoan.setText(loaiTaiKhoan.getTaiKhoan());
                        idLoaiTaiKhoan=loaiTaiKhoan.getIdLoaiTaiKhoan();
                        idTaiKhoan=getDataTaiKhoan.getIdTaiKhoan(getContext(), idLoaiTaiKhoan, maND); // lấy thành công
                        dialogTaiKhoanC.dismiss();
                    }
                });
            }
        });
        cvMucThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListMucThu = new Dialog(getActivity());
                dialogListMucThu.setTitle("Chọn Mục Thu");
                dialogListMucThu.setContentView(R.layout.listview_showdata);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogListMucThu.setCancelable(true);
                dialogListMucThu.show();
                ListView listView = (ListView) dialogListMucThu.findViewById(R.id.lvData);
                MucThuAdapter listviewAdapter = new MucThuAdapter(getActivity(),R.layout.listview_item, listDataMucThu);
                listView.setAdapter(listviewAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MucThu mucThu = listDataMucThu.get(i);
                        txtMucThu.setText(mucThu.getMucThu());
                        dialogListMucThu.dismiss();
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
                        dateFinish = cal.getTime();
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
        String soTien=edtNhapTienNhan.getText().toString();
        if(soTien.equalsIgnoreCase("")) {
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
        money=Integer.parseInt(soTien);
        if(money<=0){
            Toast.makeText(getContext(), "Số tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
            edtNhapTienNhan.requestFocus();
            return;
        }
        if(getDataMucThu.insertThu(
                getContext(), idMucThu, idTaiKhoan, maND,
                txtNgayThu.getText().toString(), money,
                edtGhiChuThu.getText().toString())==-1)
        {
            Toast.makeText(getContext(), "Insert thất bại", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Insert thành công", Toast.LENGTH_SHORT).show();
            // lấy ra money trong database theo 2 tham số idLoaiTaiKhoan & maND
            // sau khi thêm thì trừ ra => update lại
            int moneyInDatabase=Integer.parseInt(getDataTaiKhoan.getMoney(getContext(), idLoaiTaiKhoan, maND));
            int moneyAfterInsert= moneyInDatabase+money;

            if(getDataTaiKhoan.UpdateAccount(getContext(), idLoaiTaiKhoan, maND, moneyAfterInsert+"")==2){
                // update thành công
                //Toast.makeText(getContext(), "update thành công", Toast.LENGTH_SHORT).show();
                LoadTaiKhoan();
            }
        }

    }

    private void LoadTaiKhoan()
    {
        txtTienMat.setText(getDataTaiKhoan.getMoney(getContext(), 1, maND));
        txtATM.setText(getDataTaiKhoan.getMoney(getContext(), 2, maND));
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            LoadTaiKhoan();
        }
    }
}