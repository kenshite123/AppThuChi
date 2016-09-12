package com.project.nda.Model;

import java.io.Serializable;

/**
 * Created by DELL on 8/22/2016.
 */
public class MucChi implements Serializable {
    private int idMucChi;
    private int idLoaiChi;
    private String MucChi;

    public MucChi() {
    }

    public MucChi(String mucChi) {
        MucChi = mucChi;
    }

    public int getIdMucChi() {
        return idMucChi;
    }

    public void setIdMucChi(int idMucChi) {
        this.idMucChi = idMucChi;
    }

    public int getIdLoaiChi() {
        return idLoaiChi;
    }

    public void setIdLoaiChi(int idLoaiChi) {
        this.idLoaiChi = idLoaiChi;
    }

    public String getMucChi() {
        return MucChi;
    }

    public void setMucChi(String mucChi) {
        MucChi = mucChi;
    }

    @Override
    public String toString() {
        return MucChi;
    }
}
