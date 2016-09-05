package com.project.nda.model;

import java.io.Serializable;

/**
 * Created by ndact on 05/09/2016.
 */
public class ThongKeThu implements Serializable {

    private String NgayThangNam;
    private int TongTienThu;

    public ThongKeThu() {
    }

    public ThongKeThu(int maND, int idTaiKhoan, String ngayThangNam, int tongTienThu) {

        NgayThangNam = ngayThangNam;
        TongTienThu = tongTienThu;
    }



    public String getNgayThangNam() {
        return NgayThangNam;
    }

    public void setNgayThangNam(String ngayThangNam) {
        NgayThangNam = ngayThangNam;
    }

    public int getTongTienThu() {
        return TongTienThu;
    }

    public void setTongTienThu(int tongTienThu) {
        TongTienThu = tongTienThu;
    }
}
