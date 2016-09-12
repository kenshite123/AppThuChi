package com.project.nda.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.nda.Model.LoaiTaiKhoan;
import com.project.nda.thuchicanhan.R;

import java.util.List;

/**
 * Created by ndact on 31/08/2016.
 */
public class LoaiTaiKhoanAdapter extends ArrayAdapter<LoaiTaiKhoan> {
    Activity context;
    List<LoaiTaiKhoan> dsLoaiTaiKhoan;
    int resource;


    public LoaiTaiKhoanAdapter(Activity context, int resource, List<LoaiTaiKhoan> dsLoaiTaiKhoan) {
        super(context, resource, dsLoaiTaiKhoan);
        this.context = context;
        this.dsLoaiTaiKhoan = dsLoaiTaiKhoan;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        TextView txtMucChi = (TextView) row.findViewById(R.id.txtTenMucThu);
        final LoaiTaiKhoan loaiTaiKhoan = this.dsLoaiTaiKhoan.get(position);
        txtMucChi.setText(loaiTaiKhoan.getTaiKhoan());

        return row;
    }
}
