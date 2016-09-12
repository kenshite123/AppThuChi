package com.project.nda.Fragment;

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

import com.project.nda.DuLieu.DuLieuLoaiTaiKhoan;
import com.project.nda.DuLieu.DuLieuThongKe;
import com.project.nda.Adapter.LoaiTaiKhoanAdapter;
import com.project.nda.Model.LoaiTaiKhoan;
import com.project.nda.Support.DateProcess;
import com.project.nda.Support.DinhDangNgay;
import com.project.nda.Support.DinhDangTienTe;
import com.project.nda.thuchicanhan.R;
import com.project.nda.thuchicanhan.ShowDetailReportActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BaoCao_Fragment extends Fragment {

    private View view;
    private Dialog dialogHienThiTaiKhoan;

    CardView cvShowTaiKhoan, cvXemTheoNgay, cvXemTheoThang, cvXemTheoNam;
    TextView txtTenTaiKhoanBaoCao, txtHomNay, txtThang, txtNam;
    TextView txtThuHomNay, txtThuThang, txtThuNam, txtChiHomNay, txtChiThang, txtChiNam ;

    DuLieuLoaiTaiKhoan duLieuLoaiTaiKhoan = new DuLieuLoaiTaiKhoan();
    ArrayList<LoaiTaiKhoan> dsLoaiTaiKhoan = new ArrayList<>();

    String[] dsTuyChonXem = {"Hiện tại", "Tháng", "Năm", "Tùy chọn ngày"};

    Calendar cal;
    SQLiteDatabase database;

    Intent intent;
    String maND;

    int idTaiKhoan = 1;

    //Lấy ngày hiện tại
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDateandTime = sdf.format(new Date());
    DinhDangNgay dinhDangNgay = new DinhDangNgay();
    DinhDangTienTe formatMoney = new DinhDangTienTe();
    DuLieuThongKe duLieuThongKe = new DuLieuThongKe();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_thongke, container, false);
        ;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls();
        addEvents();
        duLieuLoaiTaiKhoan.DanhSachTaiKhoan(getContext(), dsLoaiTaiKhoan);

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
                dialogHienThiTaiKhoan = new Dialog(getActivity());
                dialogHienThiTaiKhoan.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogHienThiTaiKhoan.setContentView(R.layout.listview_hienthichtietbaocao);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogHienThiTaiKhoan.setCancelable(true);
                dialogHienThiTaiKhoan.show();
                ListView listView = (ListView) dialogHienThiTaiKhoan.findViewById(R.id.lvData);
                LoaiTaiKhoanAdapter taiKhoanAdapter = new LoaiTaiKhoanAdapter(getActivity(), R.layout.listview_item, dsLoaiTaiKhoan);
                listView.setAdapter(taiKhoanAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LoaiTaiKhoan loaiTaiKhoan = dsLoaiTaiKhoan.get(i);
                        txtTenTaiKhoanBaoCao.setText(loaiTaiKhoan.getTaiKhoan());
                        idTaiKhoan = loaiTaiKhoan.getIdLoaiTaiKhoan();
                        dialogHienThiTaiKhoan.dismiss();
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
                        dinhDangNgay.DinhDangDatePicker(getContext(), txtHomNay, dayOfMonth, monthOfYear, year);
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
        duLieuThongKe.DuLieu(getActivity(), false, txtChiNam, txtThuNam, maND, startDate, endDate);
    }

    private void LoadReportByMonth(String s) {
        String[] arr=s.split("/");
        // định dạng theo yyyy-MM-dd
        String startDate= arr[1] + "-" +arr[0] + "-" + "01";
        String endDate=arr[1] + "-" + arr[0] + "-" +
                DateProcess.getLastDayOfMonth(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
        duLieuThongKe.DuLieu(getActivity(), false, txtChiThang, txtThuThang, maND, startDate, endDate);
    }

    private void LoadReportByDay(String s) {
        String[] arr=s.split("/");
        String date=arr[2] + "-" + arr[1] + "-" + arr[0];
        duLieuThongKe.DuLieu(getActivity(), true, txtChiHomNay, txtThuHomNay, maND, date);
    }

    private void LoadReport() {
        LoadReportByDay(txtHomNay.getText().toString());
        LoadReportByMonth(txtThang.getText().toString());
        LoadReportByYear(txtNam.getText().toString());
    }
}
