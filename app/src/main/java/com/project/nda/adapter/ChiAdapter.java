package com.project.nda.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.nda.model.Chi;
import com.project.nda.support.FormatMoney;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= this.context.getLayoutInflater().inflate(this.resource, null);

        TextView txtMucChiDetails= (TextView) view.findViewById(R.id.txtMucThuDetails);
        TextView txtNgayChiDetails= (TextView) view.findViewById(R.id.txtNgayChiDetails);
        TextView txtSoTienChiDetails= (TextView) view.findViewById(R.id.txtSoTienChiDetails);
        TextView txtGhiChuChiDetails= (TextView) view.findViewById(R.id.txtGhiChuChiDetails);

        Chi chi=this.getItem(position);
        FormatMoney formatMoney=new FormatMoney();

        txtMucChiDetails.setText(chi.getIdMucChi().getMucChi());
        txtNgayChiDetails.setText(chi.getNgayChi());
        txtSoTienChiDetails.setText(formatMoney.FormatTextView(context, chi.getSoTienChi()+""));
        txtGhiChuChiDetails.setText(chi.getDienGiaiChi());

        return view;
    }
}
