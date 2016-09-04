package com.project.nda.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.nda.adapter.LoaiTaiKhoanAdapter;
import com.project.nda.GetData.LoaiTaiKhoanGetData;
import com.project.nda.model.LoaiTaiKhoan;
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
    TextView txtTenTaiKhoanBaoCao, txtTenTuyChonXem;

    TextView txtStartDate;
    TextView txtEndDate;

    LoaiTaiKhoanGetData loaiTaiKhoanGetData = new LoaiTaiKhoanGetData();
    ArrayList<LoaiTaiKhoan> listLoaiTaiKhoan = new ArrayList<>();

    String[] listTuyChonXem = {"Hiện tại", "Tháng", "Năm", "Tùy chọn ngày"};

    Calendar cal;
    Date dateFinish;

    //Lấy ngày hiện tại
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDateandTime = sdf.format(new Date());

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
        cvShowTuyChonXem = (CardView) view.findViewById(R.id.cvShowTuyChonXem);
        txtTenTaiKhoanBaoCao = (TextView) view.findViewById(R.id.txtTenTaiKhoanBaoCao);
        txtTenTuyChonXem = (TextView) view.findViewById(R.id.txtTenTuyChonXem);
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
                        dialogShowTaiKhoan.dismiss();
                    }
                });
            }
        });
        cvShowTuyChonXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowTuyChonXem = new Dialog(getActivity());
                dialogShowTuyChonXem.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogShowTuyChonXem.setContentView(R.layout.listview_showdata);
                getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                dialogShowTuyChonXem.setCancelable(true);
                dialogShowTuyChonXem.show();
                ListView lvTuyChonXem = (ListView) dialogShowTuyChonXem.findViewById(R.id.lvData);
                ArrayAdapter rAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listTuyChonXem);
                lvTuyChonXem.setAdapter(rAdapter);
                lvTuyChonXem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        //Tới đây nếu chọn mục "Tùy chọn xem sẽ show dialog nữa để chọn ngày"
                        if (listTuyChonXem[i] == "Tùy chọn ngày") {

                            dialogShowDate = new Dialog(getActivity());
                            dialogShowDate.setContentView(R.layout.select_date);
                            dialogShowDate.setTitle("Chọn khoảng thời gian");
                            dialogShowDate.setCancelable(true);
                            dialogShowDate.show();
                            FrameLayout btnStartDate = (FrameLayout) dialogShowDate.findViewById(R.id.btnStartDate);
                            FrameLayout btnEndDate = (FrameLayout) dialogShowDate.findViewById(R.id.btnEndDate);

                            txtStartDate= (TextView) dialogShowDate.findViewById(R.id.txtStartDate);
                            txtEndDate = (TextView) dialogShowDate.findViewById(R.id.txtEndDate);

                            txtStartDate.setText(currentDateandTime);
                            txtEndDate.setText(currentDateandTime);

                            final Button btnAgree = (Button) dialogShowDate.findViewById(R.id.btnAgree);

                            btnStartDate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                            //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail
                                            txtStartDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                                            //Lưu vết lại biến ngày hoàn thành
                                            cal = Calendar.getInstance();
                                            cal.set(year, monthOfYear, dayOfMonth);
                                            dateFinish = cal.getTime();
                                        }
                                    };
                                    //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                                    //sẽ giống với trên TextView khi mở nó lên
                                    String s = txtStartDate.getText() + "";
                                    String strArrtmp[] = s.split("/");
                                    int ngay = Integer.parseInt(strArrtmp[0]);
                                    int thang = Integer.parseInt(strArrtmp[1]) - 1;
                                    int nam = Integer.parseInt(strArrtmp[2]);
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, thang, ngay);
                                    datePickerDialog.setTitle("Chọn ngày hoàn thành");
                                    datePickerDialog.show();
                                }
                            });
                            btnEndDate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                            //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Detail
                                            txtEndDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                                            //Lưu vết lại biến ngày hoàn thành
                                            cal = Calendar.getInstance();
                                            cal.set(year, monthOfYear, dayOfMonth);
                                            dateFinish = cal.getTime();
                                        }
                                    };
                                    String s = txtEndDate.getText() + "";
                                    String strArrtmp[] = s.split("/");
                                    int ngay = Integer.parseInt(strArrtmp[0]);
                                    int thang = Integer.parseInt(strArrtmp[1]) - 1;
                                    int nam = Integer.parseInt(strArrtmp[2]);
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), callback, nam, thang, ngay);
                                    datePickerDialog.setTitle("Chọn ngày hoàn thành");
                                    datePickerDialog.show();
                                }
                            });
                            btnAgree.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    txtTenTuyChonXem.setText(txtStartDate.getText()+ " - " + txtEndDate.getText()+"");
                                    dialogShowDate.dismiss();
                                    dialogShowTuyChonXem.dismiss();
                                }
                            });
                        }
                        else
                        {
                            txtTenTuyChonXem.setText(listTuyChonXem[i]);
                            dialogShowTuyChonXem.dismiss();

                        }
                    }
                });
            }
        });
    }
}
