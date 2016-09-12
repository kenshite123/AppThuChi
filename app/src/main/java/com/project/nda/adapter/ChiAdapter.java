package com.project.nda.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.nda.DuLieu.DuLieuLoaiTaiKhoan;
import com.project.nda.DuLieu.DuLieuTaiKhoan;
import com.project.nda.Model.Chi;
import com.project.nda.Support.DinhDangTienTe;
import com.project.nda.thuchicanhan.R;

/**
 * Created by DELL on 9/7/2016.
 */
public class ChiAdapter extends ArrayAdapter<Chi> {

    Activity context;
    int resource;

    public ChiAdapter(Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    TextView txtMucChi, txtNgayChi, txtSoTienChi, txtGhiChuChi, txtLoaiTaiKhoan;
    DuLieuLoaiTaiKhoan duLieuLoaiTaiKhoan = new DuLieuLoaiTaiKhoan();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view= this.context.getLayoutInflater().inflate(this.resource, null);

        txtMucChi= (TextView) view.findViewById(R.id.txtMucChi);
        txtNgayChi= (TextView) view.findViewById(R.id.txtNgayThu);
        txtSoTienChi= (TextView) view.findViewById(R.id.txtSoTienThu);
        txtGhiChuChi= (TextView) view.findViewById(R.id.txtGhiChuChi);
        txtLoaiTaiKhoan =(TextView) view.findViewById(R.id.txtLoaiTaiKhoan);

        Chi chi = this.getItem(position);
        DinhDangTienTe dinhDangTienTe = new DinhDangTienTe(); // Định dạng tiền tệ vd: 500.000.000

        txtMucChi.setText(chi.getIdMucChi().getMucChi());
        txtNgayChi.setText(chi.getNgayChi());
        txtSoTienChi.setText(dinhDangTienTe.DinhDangTextView(context, chi.getSoTienChi()+""));
        txtGhiChuChi.setText(chi.getDienGiaiChi());
        DuLieuTaiKhoan duLieuTaiKhoan = new DuLieuTaiKhoan();
        int idLoaiTaiKhoan = duLieuTaiKhoan.LayidLoaiTaiKhoan(getContext(), chi.getIdTaiKhoan());
        txtLoaiTaiKhoan.setText(duLieuLoaiTaiKhoan.LayTenLoaiTaiKhoan(getContext(), idLoaiTaiKhoan)+":");
        return view;
    }
}
