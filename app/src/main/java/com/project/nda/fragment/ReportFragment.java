package com.project.nda.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.project.nda.GetData.LoaiTaiKhoanGetData;
import com.project.nda.GetData.ThongKeGetData;
import com.project.nda.adapter.LoaiTaiKhoanAdapter;
import com.project.nda.model.LoaiTaiKhoan;
import com.project.nda.support.DateProcess;
import com.project.nda.support.FormatDateTime;
import com.project.nda.support.FormatMoney;
import com.project.nda.thuchicanhan.R;
import com.project.nda.thuchicanhan.ShowDetailReportActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ReportFragment extends Fragment {

    private View view;
    private Dialog dialogShowTaiKhoan;
    private Dialog dialogShowTuyChonXem;
    private Dialog dialogShowDate;

    CardView cvShowTaiKhoan, cvXemTheoNgay, cvXemTheoThang, cvXemTheoNam;
    TextView txtTenTaiKhoanBaoCao, txtHomNay, txtThang, txtNam;
    TextView txtThuHomNay, txtThuThang, txtThuNam, txtChiHomNay, txtChiThang, txtChiNam ;

    LoaiTaiKhoanGetData loaiTaiKhoanGetData = new LoaiTaiKhoanGetData();
    ArrayList<LoaiTaiKhoan> listLoaiTaiKhoan = new ArrayList<>();

    String[] listTuyChonXem = {"Hiện tại", "Tháng", "Năm", "Tùy chọn ngày"};

    Calendar cal;
    Date dateFinish;
    SQLiteDatabase database;

    Intent intent;
    String maND;

    int idTaiKhoan = 1;

    //Lấy ngày hiện tại
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDateandTime = sdf.format(new Date());
    FormatDateTime formatDateTime = new FormatDateTime();
    FormatMoney formatMoney = new FormatMoney();
    ThongKeGetData thongKeGetData=new ThongKeGetData();

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
        cvXemTheoNgay = (CardView) view.findViewById(R.id.cvXemTheoNgay);
        cvXemTheoThang = (CardView) view.findViewById(R.id.cvXemTheoThang);
        cvXemTheoNam = (CardView) view.findViewById(R.id.cvXemTheoNam);


        txtTenTaiKhoanBaoCao = (TextView) view.findViewById(R.id.txtTenTaiKhoanBaoCao);
        txtTenTaiKhoanBaoCao.setText("Ví");
        txtHomNay = (TextView) view.findViewById(R.id.txtHomNay);
        txtHomNay.setText(currentDateandTime);
        txtThuHomNay = (TextView) view.findViewById(R.id.txtThuHomnay);
        txtChiHomNay = (TextView) view.findViewById(R.id.txtChiHomnay);
        txtThang = (TextView) view.findViewById(R.id.txtThang);
        txtThang.setText(currentDateandTime.substring(3, 10 ));
        txtThuThang = (TextView) view.findViewById(R.id.txtThuThang);
        txtChiThang = (TextView) view.findViewById(R.id.txtChiThang);
        txtNam = (TextView) view.findViewById(R.id.txtNam);
        txtNam.setText(currentDateandTime.substring(6, 10 ));
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
        txtHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail
                        //Lưu vết lại biến ngày hoàn thành
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        formatDateTime.FormatDatePicker(getContext(), txtHomNay, dayOfMonth, monthOfYear, year);
                        LoadReportByDay(txtHomNay.getText().toString());
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = txtHomNay.getText() + "";
                String strArrtmp[] = s.split("/");
                int ngay = Integer.parseInt(strArrtmp[0]);
                int thang = Integer.parseInt(strArrtmp[1]) - 1;
                int nam = Integer.parseInt(strArrtmp[2]);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, thang, ngay);
                datePickerDialog.setTitle("Chọn ngày hoàn thành");
                datePickerDialog.show();

            }
        });
        txtThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail
                        //Lưu vết lại biến ngày hoàn thành
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear+1<10){
                            txtThang.setText("0"+ (monthOfYear+1) + "/" + year);
                            //Toast.makeText(getContext(), "0"+ (monthOfYear+1) + "/" + year, Toast.LENGTH_SHORT).show();
                        }else{
                            txtThang.setText((monthOfYear+1) + "/" + year);
                            //Toast.makeText(getContext(), (monthOfYear+1) + "/" + year, Toast.LENGTH_SHORT).show();
                        }
                        /*
                        FormatDateTime formatDateTime = new FormatDateTime();
                        formatDateTime.FormatDatePicker(getContext(), txtThang, 0, monthOfYear, year);
                        */
                        //LoadReport();
                        LoadReportByMonth(txtThang.getText().toString());
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = txtThang.getText() + "";
                String strArrtmp[] = s.split("/");
                int thang = Integer.parseInt(strArrtmp[0]) - 1;
                int nam = Integer.parseInt(strArrtmp[1]);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, thang, 0);
                ((ViewGroup) datePickerDialog.getDatePicker()).
                        findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.setTitle("Chọn ngày hoàn thành");
                datePickerDialog.show();
            }
        });
        txtNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail
                        //Lưu vết lại biến ngày hoàn thành
                        cal = Calendar.getInstance();
                        cal.set(year, monthOfYear, dayOfMonth);
                        txtNam.setText(year+"");
                        /*
                        FormatDateTime formatDateTime = new FormatDateTime();
                        formatDateTime.FormatDatePicker(getContext(), txtThang, 0, monthOfYear, year);
                        */
                        //LoadReport();
                        LoadReportByYear(txtNam.getText().toString());
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = txtNam.getText() + "";
                String strArrtmp[] = s.split("/");
                int nam = Integer.parseInt(strArrtmp[0]);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, 0, 0);
                ((ViewGroup) datePickerDialog.getDatePicker()).
                        findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                ((ViewGroup) datePickerDialog.getDatePicker()).
                        findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.setTitle("Chọn ngày hoàn thành");
                datePickerDialog.show();
            }
        });
        cvXemTheoNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ShowDetailReportActivity.class);
                intent.putExtra("LOAI", 1);
                intent.putExtra("NGAY", txtHomNay.getText().toString());
                intent.putExtra("MAND", maND);
                startActivity(intent);
            }
        });
        cvXemTheoThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), ShowDetailReportActivity.class);
                intent.putExtra("LOAI", 2);
                intent.putExtra("THANG", txtThang.getText().toString());
                intent.putExtra("MAND", maND);
                startActivity(intent);
            }
        });
        cvXemTheoNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), ShowDetailReportActivity.class);
                intent.putExtra("LOAI", 3);
                intent.putExtra("NAM", txtNam.getText().toString());
                intent.putExtra("MAND", maND);
                startActivity(intent);
            }
        });
        LoadReport();

    }

    private void LoadReportByYear(String s) {
        // year-01-01 vd: 2016-01-01
        String startDate= s + "-01-01";
        String endDate=s+"-12-31";
        thongKeGetData.LoadData(getActivity(), false, txtChiNam, txtThuNam, maND, startDate, endDate);
    }

    private void LoadReportByMonth(String s) {
        String[] arr=s.split("/");
        // định dạng theo yyyy-MM-dd
        String startDate= arr[1] + "-" +arr[0] + "-" + "01";
        String endDate=arr[1] + "-" + arr[0] + "-" +
                DateProcess.getLastDayOfMonth(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
        thongKeGetData.LoadData(getActivity(), false, txtChiThang, txtThuThang, maND, startDate, endDate);
    }

    private void LoadReportByDay(String s) {
        String[] arr=s.split("/");
        String date=arr[2] + "-" + arr[1] + "-" + arr[0];
        thongKeGetData.LoadData(getActivity(), true, txtChiHomNay, txtThuHomNay, maND, date);
    }

    private void LoadReport() {
        LoadReportByDay(txtHomNay.getText().toString());
        LoadReportByMonth(txtThang.getText().toString());
        LoadReportByYear(txtNam.getText().toString());
    }
}
