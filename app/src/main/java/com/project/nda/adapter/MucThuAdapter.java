package com.project.nda.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.nda.model.MucThu;
import com.project.nda.thuchicanhan.R;

import java.util.List;

/**
 * Created by TranHoa on 8/30/2016.
 */
public class MucThuAdapter extends ArrayAdapter<MucThu> {

    Activity context;
    List<MucThu> listMucThu;
    int resource;


    public MucThuAdapter(Activity context, int resource, List<MucThu> listMucThu) {
        super(context, resource, listMucThu);
        this.context = context;
        this.listMucThu = listMucThu;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        TextView txtMucChi = (TextView) row.findViewById(R.id.txtTenMucThu);
        final MucThu mucThu = this.listMucThu.get(position);
        txtMucChi.setText(mucThu.getMucThu());

        return row;
    }
}