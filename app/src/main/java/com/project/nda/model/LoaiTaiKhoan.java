package com.project.nda.model;

import java.io.Serializable;

/**
 * Created by ndact on 31/08/2016.
 */
public class LoaiTaiKhoan implements Serializable{
    private int idLoaiTaiKhoan;
    private String TaiKhoan;

    public LoaiTaiKhoan() {
    }

    public LoaiTaiKhoan(int idLoaiTaiKhoan, String taiKhoan) {
        this.idLoaiTaiKhoan = idLoaiTaiKhoan;
        TaiKhoan = taiKhoan;
    }

    public int getIdLoaiTaiKhoan() {
        return idLoaiTaiKhoan;
    }

    public void setIdLoaiTaiKhoan(int idLoaiTaiKhoan) {
        this.idLoaiTaiKhoan = idLoaiTaiKhoan;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }
}
