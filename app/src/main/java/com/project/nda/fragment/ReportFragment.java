package com.project.nda.fragment;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.project.nda.GetData.LoaiTaiKhoanGetData;
import com.project.nda.GetData.ThongKeGetData;
import com.project.nda.adapter.LoaiTaiKhoanAdapter;
import com.project.nda.model.LoaiTaiKhoan;
import com.project.nda.support.DateProcess;
import com.project.nda.support.FormatMoney;
import com.project.nda.thuchicanhan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ReportFragment extends Fragment {

    private View view;
    private Dialog dialogShowTaiKhoan;
    private Dialog dialogShowTuyChonXem;
    private Dialog dialogShowDate;

    CardView cvShowTaiKhoan, cvShowTuyChonXem;
    TextView txtTenTaiKhoanBaoCao, txtHomNay, txtThang, txtNam;
    TextView txtThuHomNay, txtThuThang, txtThuNam, txtChiHomNay, txtChiThang, txtChiNam ;

    LoaiTaiKhoanGetData loaiTaiKhoanGetData = new LoaiTaiKhoanGetData();
    ArrayList<LoaiTaiKhoan> listLoaiTaiKhoan = new ArrayList<>();

    String[] listTuyChonXem = {"Hiện tại", "Tháng", "Năm", "Tùy chọn ngày"};

    Calendar cal;
    Date dateFinish;

    Intent intent;
    String maND;

    int idTaiKhoan;

    //Lấy ngày hiện tại
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDateandTime = sdf.format(new Date());

    String startDate;
    String endDate;
    String startDateY;
    String endDateY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_report, container, false);
        ;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
        loaiTaiKhoanGetData.ListTaiKhoan(getContext(), listLoaiTaiKhoan);

    }

    private void addControls() {
        cvShowTaiKhoan = (CardView) view.findViewById(R.id.cvShowTaiKhoan);
        txtTenTaiKhoanBaoCao = (TextView) view.findViewById(R.id.txtTenTaiKhoanBaoCao);
        txtHomNay = (TextView) view.findViewById(R.id.txtHomNay);
        txtThuHomNay = (TextView) view.findViewById(R.id.txtThuHomnay);
        txtChiHomNay = (TextView) view.findViewById(R.id.txtChiHomnay);
        txtThang = (TextView) view.findViewById(R.id.txtThang);
        txtThuThang = (TextView) view.findViewById(R.id.txtThuThang);
        txtChiThang = (TextView) view.findViewById(R.id.txtChiThang);
        txtNam = (TextView) view.findViewById(R.id.txtNam);
        txtChiNam = (TextView) view.findViewById(R.id.txtChiNam);
        txtThuNam = (TextView) view.findViewById(R.id.txtThuNam);
        intent=getActivity().getIntent();
        maND=intent.getStringExtra("MAND");
    }

    private void addEvents() {
        cvShowTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowTaiKhoan = new Dialog(getActivity());
                dialogShowTaiKhoan.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogShowTaiKhoan.setContentView(R.layout.listview_showdata);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogShowTaiKhoan.setCancelable(true);
                dialogShowTaiKhoan.show();
                ListView listView = (ListView) dialogShowTaiKhoan.findViewById(R.id.lvData);
                LoaiTaiKhoanAdapter taiKhoanAdapter = new LoaiTaiKhoanAdapter(getActivity(), R.layout.listview_item, listLoaiTaiKhoan);
                listView.setAdapter(taiKhoanAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LoaiTaiKhoan loaiTaiKhoan = listLoaiTaiKhoan.get(i);
                        txtTenTaiKhoanBaoCao.setText(loaiTaiKhoan.getTaiKhoan());
                        idTaiKhoan = loaiTaiKhoan.getIdLoaiTaiKhoan();
                        dialogShowTaiKhoan.dismiss();
                        LoadReport();

                    }
                });
            }
        });
    }
    private void LoadReport()
    {

        FormatMoney formatMoney = new FormatMoney();
        //Show Thu Chi ngày hiện tại
        ThongKeGetData getDataHomNay = new ThongKeGetData(getContext(), currentDateandTime, currentDateandTime, maND, idTaiKhoan);
        String chiHomNay = getDataHomNay.getDataThongKeChi();
        String thuHomNay = getDataHomNay.getDataThongKeThu();

        txtHomNay.setText(currentDateandTime);
        txtChiHomNay.setText(formatMoney.FormatTexView(getContext(), chiHomNay));
        txtThuHomNay.setText(formatMoney.FormatTexView(getContext(), thuHomNay));

        //Show thu chi theo thang vd: 09-2016
        getCurrentMonth();
        ThongKeGetData getDataThang = new ThongKeGetData(getContext(), startDate, endDate, maND, idTaiKhoan);
        String chiThang = getDataThang.getDataThongKeChi();
        String thuThang = getDataThang.getDataThongKeThu();

        txtThang.setText(currentDateandTime.substring(3, 10 ));
        txtChiThang.setText(formatMoney.FormatTexView(getContext(), chiThang));
        txtThuThang.setText(formatMoney.FormatTexView(getContext(), thuThang));

        //Show Thu chi theo năm
        getCurrentYear();
        ThongKeGetData getDataNam = new ThongKeGetData(getContext(), startDateY, endDateY, maND, idTaiKhoan);
        String chiNam = getDataThang.getDataThongKeChi();
        String thuNam = getDataNam.getDataThongKeThu();

        txtNam.setText(currentDateandTime.substring(6, 10 ));
        txtChiNam.setText(formatMoney.FormatTexView(getContext(), chiNam));
        txtThuNam.setText(formatMoney.FormatTexView(getContext(), thuNam));

    }

    public void getCurrentMonth() {
        int month=Integer.parseInt(currentDateandTime.substring(3, 5)); // 9
        int year=Integer.parseInt(currentDateandTime.substring(6, 10));  // 2016
        if(month<10)
        {
            startDate =  "01" + "/0" + month + "/" + year;
            endDate =DateProcess.getLastDayOfMonth(month, year) + "/0" + month + "/" + year;
        }else
        {
            startDate = "01" + "/" + month + "/" + year;
            endDate =DateProcess.getLastDayOfMonth(month, year)  + "/" + month + "/" + year;
        }
        Log.d("nam", startDate +" "+ endDate);

    }
    public void getCurrentYear() {
        int month=Integer.parseInt(currentDateandTime.substring(3, 5));
        int year=Integer.parseInt(currentDateandTime.substring(6, 10));
            startDateY =  "01" + "/01/" + year;
            endDateY =DateProcess.getLastDayOfMonth(month, year) + "/12/" + year;
        Log.d("nam", startDateY +" "+ endDateY);
    }
}
