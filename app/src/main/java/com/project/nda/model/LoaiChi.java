package com.project.nda.Model;

import java.io.Serializable;

/**
 * Created by DELL on 8/22/2016.
 */
public class  LoaiChi implements Serializable{
    private int idLoaiChi;
    private String TenLoaiChi;

    public int getIdLoaiChi() {
        return idLoaiChi;
    }

    public void setIdLoaiChi(int idLoaiChi) {
        this.idLoaiChi = idLoaiChi;
    }

    public String getTenLoaiChi() {
        return TenLoaiChi;
    }

    public void setTenLoaiChi(String tenLoaiChi) {
        TenLoaiChi = tenLoaiChi;
    }

    @Override
    public String toString() {
        return TenLoaiChi;
    }
}
