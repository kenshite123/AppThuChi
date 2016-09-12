package com.project.nda.Model;

import java.io.Serializable;

/**
 * Created by DELL on 9/7/2016.
 */
public class Thu implements Serializable {
    private int idThu;
    private MucThu idMucThu;
    private int idTaiKhoan;
    private String NgayThu;
    private int SoTienThu;
    private String DienGiaiThu;

    public int getIdThu() {
        return idThu;
    }

    public void setIdThu(int idThu) {
        this.idThu = idThu;
    }

    public MucThu getIdMucThu() {
        return idMucThu;
    }

    public void setIdMucThu(MucThu idMucThu) {
        this.idMucThu = idMucThu;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(String ngayThu) {
        NgayThu = ngayThu;
    }

    public int getSoTienThu() {
        return SoTienThu;
    }

    public void setSoTienThu(int soTienThu) {
        SoTienThu = soTienThu;
    }

    public String getDienGiaiThu() {
        return DienGiaiThu;
    }

    public void setDienGiaiThu(String dienGiaiThu) {
        DienGiaiThu = dienGiaiThu;
    }
}
