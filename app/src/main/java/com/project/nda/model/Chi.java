package com.project.nda.model;

import java.io.Serializable;

/**
 * Created by DELL on 9/7/2016.
 */
public class Chi implements Serializable {
    private int idChi;
    private MucChi idMucChi;
    private int idTaiKhoan;
    private String NgayChi;
    private int SoTienChi;
    private String DienGiaiChi;

    public int getIdChi() {
        return idChi;
    }

    public void setIdChi(int idChi) {
        this.idChi = idChi;
    }

    public MucChi getIdMucChi() {
        return idMucChi;
    }

    public void setIdMucChi(MucChi idMucChi) {
        this.idMucChi = idMucChi;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getNgayChi() {
        return NgayChi;
    }

    public void setNgayChi(String ngayChi) {
        NgayChi = ngayChi;
    }

    public int getSoTienChi() {
        return SoTienChi;
    }

    public void setSoTienChi(int soTienChi) {
        SoTienChi = soTienChi;
    }

    public String getDienGiaiChi() {
        return DienGiaiChi;
    }

    public void setDienGiaiChi(String dienGiaiChi) {
        DienGiaiChi = dienGiaiChi;
    }
}
