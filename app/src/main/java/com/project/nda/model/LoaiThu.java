package com.project.nda.model;

import java.io.Serializable;

/**
 * Created by ndact on 30/08/2016.
 */
public class LoaiThu implements Serializable {
    private int idLoaiThu;
    private String TenLoaiThu;

    public LoaiThu() {
    }

    public LoaiThu(int idLoaiThu, String tenLoaiThu) {
        this.idLoaiThu = idLoaiThu;
        TenLoaiThu = tenLoaiThu;
    }

    public int getIdLoaiThu() {
        return idLoaiThu;
    }

    public void setIdLoaiThu(int idLoaiThu) {
        this.idLoaiThu = idLoaiThu;
    }

    public String getTenLoaiThu() {
        return TenLoaiThu;
    }

    public void setTenLoaiThu(String tenLoaiThu) {
        TenLoaiThu = tenLoaiThu;
    }

}
