package com.project.nda.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.nda.model.Thu;
import com.project.nda.support.FormatMoney;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= this.context.getLayoutInflater().inflate(this.resource, null);

        TextView txtMucThuDetails= (TextView) view.findViewById(R.id.txtMucThuDetails);
        TextView txtNgayThuDetails= (TextView) view.findViewById(R.id.txtNgayThuDetails);
        TextView txtSoTienThuDetails= (TextView) view.findViewById(R.id.txtSoTienThuDetails);
        TextView txtGhiChuThuDetails= (TextView) view.findViewById(R.id.txtGhiChuThuDetails);

        Thu thu=this.getItem(position);
        FormatMoney formatMoney=new FormatMoney();

        txtMucThuDetails.setText(thu.getIdMucThu().getMucThu());
        txtNgayThuDetails.setText(thu.getNgayThu());
        txtSoTienThuDetails.setText(formatMoney.FormatTextView(context, thu.getSoTienThu()+""));
        txtGhiChuThuDetails.setText(thu.getDienGiaiThu());

        return view;
    }
}
