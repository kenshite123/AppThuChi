package com.project.nda.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.nda.DuLieu.DuLieuLoaiTaiKhoan;
import com.project.nda.DuLieu.DuLieuTaiKhoan;
import com.project.nda.Model.Thu;
import com.project.nda.Support.DinhDangTienTe;
import com.project.nda.thuchicanhan.R;

/**
 * Created by DELL on 9/7/2016.
 */
public class ThuAdapter extends ArrayAdapter<Thu> {

    Activity context;
    int resource;
    public ThuAdapter(Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    TextView txtMucThu, txtNgayThu, txtSoTienThu, txtGhiChuThu, txtLoaiTaiKhoan;
    DuLieuLoaiTaiKhoan duLieuLoaiTaiKhoan = new DuLieuLoaiTaiKhoan();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= this.context.getLayoutInflater().inflate(this.resource, null);

        txtMucThu = (TextView) view.findViewById(R.id.txtMucChi);
        txtNgayThu = (TextView) view.findViewById(R.id.txtNgayThu);
        txtSoTienThu = (TextView) view.findViewById(R.id.txtSoTienThu);
        txtGhiChuThu = (TextView) view.findViewById(R.id.txtGhiChuThu);
        txtLoaiTaiKhoan = (TextView) view.findViewById(R.id.txtLoaiTaiKhoan);

        Thu thu=this.getItem(position);
        DinhDangTienTe dinhDangTienTe=new DinhDangTienTe();

        txtMucThu.setText(thu.getIdMucThu().getMucThu());
        txtNgayThu.setText(thu.getNgayThu());
        txtSoTienThu.setText(dinhDangTienTe.DinhDangTextView(context, thu.getSoTienThu()+""));
        txtGhiChuThu.setText(thu.getDienGiaiThu());
        DuLieuTaiKhoan duLieuTaiKhoan = new DuLieuTaiKhoan();
        int idLoaiTaiKhoan = duLieuTaiKhoan.LayidLoaiTaiKhoan(getContext(), thu.getIdTaiKhoan());
        txtLoaiTaiKhoan.setText(duLieuLoaiTaiKhoan.LayTenLoaiTaiKhoan(getContext(), idLoaiTaiKhoan)+":");
        return view;
    }
}
