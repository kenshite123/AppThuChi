package com.project.nda.model;

import java.io.Serializable;

/**
 * Created by DELL on 9/5/2016.
 */
public class ThongKeChi implements Serializable {

    private String NgayThangNam;
    private int TongTienChi;

    public ThongKeChi(int maND, int idTaiKhoan, String ngayThangNam, int tongTienChi) {
        NgayThangNam = ngayThangNam;
        TongTienChi = tongTienChi;
    }

    public ThongKeChi() {
    }


    public String getNgayThangNam() {
        return NgayThangNam;
    }

    public void setNgayThangNam(String ngayThangNam) {
        NgayThangNam = ngayThangNam;
    }

    public int getTongTienChi() {
        return TongTienChi;
    }

    public void setTongTienChi(int tongTienChi) {
        TongTienChi = tongTienChi;
    }
}
