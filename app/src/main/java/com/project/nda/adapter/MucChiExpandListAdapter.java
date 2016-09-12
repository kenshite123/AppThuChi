package com.project.nda.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.project.nda.Model.LoaiChi;
import com.project.nda.Model.MucChi;
import com.project.nda.thuchicanhan.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ndact on 28/08/2016.
 */
public class MucChiExpandListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<LoaiChi> dsDuLieuCha;
    private HashMap<LoaiChi, List<MucChi>> dsDuLieuCon;

    public MucChiExpandListAdapter(Context context, List<LoaiChi> dsDuLieuCha,
                                   HashMap<LoaiChi, List<MucChi>> dsDuLieuCon) {
        this._context = context;
        this.dsDuLieuCha = dsDuLieuCha;
        this.dsDuLieuCon = dsDuLieuCon;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.dsDuLieuCha.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.dsDuLieuCha.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        LoaiChi loaiChi = (LoaiChi) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandlistview_group, null);
        }

        TextView lbldsCha = (TextView) convertView
                .findViewById(R.id.lbdsCha);
        lbldsCha.setTypeface(null, Typeface.BOLD);
        lbldsCha.setText(loaiChi.getTenLoaiChi());

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
            return this.dsDuLieuCon.get(this.dsDuLieuCha.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return dsDuLieuCon.get(this.dsDuLieuCha.get(groupPosition)).size();

    }
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        MucChi mucChi = (MucChi) getChild(groupPosition, childPosition);
        final String conText =mucChi.getMucChi();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandlistview_item, null);
        }

        TextView txtdsCon = (TextView) convertView
                .findViewById(R.id.lbldsCon);

        txtdsCon.setText(conText);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}