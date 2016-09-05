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
import android.util.Log;
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

import com.project.nda.GetData.LoaiTaiKhoanGetData;
import com.project.nda.GetData.MucChiGetData;
import com.project.nda.GetData.TaiKhoanGetData;
import com.project.nda.adapter.LoaiTaiKhoanAdapter;
import com.project.nda.adapter.MucChiExpandListAdapter;
import com.project.nda.model.LoaiChi;
import com.project.nda.model.LoaiTaiKhoan;
import com.project.nda.model.MucChi;
import com.project.nda.support.FormatMoney;
import com.project.nda.support.MoneyText;
import com.project.nda.thuchicanhan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SpendMoneyFragment extends Fragment {

    private View view;
    private Dialog dialogListMucChi;
    private Dialog dialogTaiKhoanC;

    CardView cvTaiKhoanC, cvMucChi, cvNgayChi;
    TextView txtMucChi, txtNgayChi, txtTaiKhoanChiC, txtTienMat, txtATM;
    EditText edtNhapTienChi, edtGhiChuChi;
    Button btnLuuChi;

    ArrayList<LoaiChi> listDataLoaiChi = new ArrayList<>();
    HashMap<LoaiChi, List<MucChi>> listDataMucChi = new HashMap<LoaiChi, List<MucChi>>();
    ArrayList<LoaiTaiKhoan> listDataLoaiTaiKhoanC = new ArrayList<>();

    LoaiTaiKhoanGetData getDataLTK = new LoaiTaiKhoanGetData();
    TaiKhoanGetData getDataTaiKhoan = new TaiKhoanGetData();
    MucChiGetData getDataMucChi = new MucChiGetData();

    FormatMoney fmoney = new FormatMoney();

    Intent intent;
    String maND;
    SQLiteDatabase database = null;

    Calendar cal;
    Date dateFinish;

    int idLoaiTaiKhoan, idTaiKhoan, idMucChi, money;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_spendmoney, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
        getDataMucChi.ListMucChi(getContext(),listDataLoaiChi, listDataMucChi);
        getDataLTK.ListTaiKhoan(getContext(),listDataLoaiTaiKhoanC );
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
                dialogTaiKhoanC = new Dialog(getActivity());
                dialogTaiKhoanC.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogTaiKhoanC.setContentView(R.layout.listview_showdata);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogTaiKhoanC.setCancelable(true);
                dialogTaiKhoanC.show();
                ListView lvTaiKhoan = (ListView) dialogTaiKhoanC.findViewById(R.id.lvData);
                LoaiTaiKhoanAdapter taiKhoanAdapter = new LoaiTaiKhoanAdapter(getActivity(), R.layout.listview_item, listDataLoaiTaiKhoanC);
                lvTaiKhoan.setAdapter(taiKhoanAdapter);

                lvTaiKhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LoaiTaiKhoan loaiTaiKhoan = listDataLoaiTaiKhoanC.get(i);
                        txtTaiKhoanChiC.setText(loaiTaiKhoan.getTaiKhoan());
                        idLoaiTaiKhoan=loaiTaiKhoan.getIdLoaiTaiKhoan();
                        // lấy ra idTaiKhoan để thêm vào table Chi
                        // nếu idTaiKhoan != 0 => tồn tại 1 tài khoản với MaND & Loại tài khoản
                        // tức là user này đã nhập số tiền vào tài khoản. Ví dụ: user chinh - Ví : 100.000 đ => idTaiKhoan != 0
                        // nếu idTaiKhoan == 0 => chưa nhập số tiền. Ví dụ user chính - ATM chưa nhập số tiền => idTaiKhoan = 0
                        idTaiKhoan=getDataTaiKhoan.getIdTaiKhoan(getContext(), idLoaiTaiKhoan, maND); // lấy thành công
                        //Toast.makeText(getContext(), idTaiKhoan+"", Toast.LENGTH_SHORT).show();
                        dialogTaiKhoanC.dismiss();
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
                        txtNgayChi.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                        //Lưu vết lại biến ngày hoàn thành
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        dateFinish = cal.getTime();
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
                dialogListMucChi = new Dialog(getContext());
                dialogListMucChi.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogListMucChi.setContentView(R.layout.expandlistview_mucchi);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogListMucChi.setCancelable(true);
                dialogListMucChi.show();
                ExpandableListView expListView = (ExpandableListView) dialogListMucChi.findViewById(R.id.lvExp);
                MucChiExpandListAdapter sadapter = new MucChiExpandListAdapter(getContext(), listDataLoaiChi, listDataMucChi);
                expListView.setAdapter(sadapter);
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        //Nothing here ever fires
                        MucChi mucChi = (MucChi) listDataMucChi.get(listDataLoaiChi.get(groupPosition)).get(childPosition);
                        txtMucChi.setText(mucChi.getMucChi());
                        // lấy ra idMucChi để insert vào table
                        idMucChi=mucChi.getIdMucChi(); // lấy thành công
                        //Toast.makeText(getContext(), idMucChi+"", Toast.LENGTH_SHORT).show();
                        dialogListMucChi.dismiss();
                        return true;
                    }
                });
            }
        });
        edtNhapTienChi.setRawInputType(Configuration.KEYBOARD_12KEY);
        edtNhapTienChi.addTextChangedListener(new MoneyText(edtNhapTienChi));
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

        Log.d("hehe", idLoaiTaiKhoan+"");
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
        int moneyInDatabase=Integer.parseInt(getDataTaiKhoan.getMoney(getContext(), idLoaiTaiKhoan, maND));

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

        if(getDataMucChi.insertChi(
                getContext(), idMucChi, idTaiKhoan, maND,
                txtNgayChi.getText().toString(), money,
                edtGhiChuChi.getText().toString())==-1){
            Toast.makeText(getContext(), "Insert thất bại", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Insert thành công", Toast.LENGTH_SHORT).show();
            // lấy ra money trong database theo 2 tham số idLoaiTaiKhoan & maND
            // sau khi thêm thì trừ ra => update lại
            int moneyAfterInsert= moneyInDatabase-money;

            if(getDataTaiKhoan.UpdateAccount(getContext(), idLoaiTaiKhoan, maND, moneyAfterInsert+"")==2){
                // update thành công
                //Toast.makeText(getContext(), "update thành công", Toast.LENGTH_SHORT).show();
                LoadTaiKhoan();
            }
        }
    }

    private void LoadTaiKhoan()
    {
        String getTienMat = getDataTaiKhoan.getMoney(getContext(), 1, maND);
        String getATM = getDataTaiKhoan.getMoney(getContext(), 2, maND);
        getTienMat = fmoney.FormatTexView(getContext(),getTienMat);
        getATM = fmoney.FormatTexView(getContext(),getATM);

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