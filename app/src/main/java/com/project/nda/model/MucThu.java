package com.project.nda.model;

import java.io.Serializable;

/**
 * Created by ndact on 30/08/2016.
 */
public class MucThu implements Serializable{
    private int idMucThu;
    private String MucThu;

    public MucThu() {
    }

    public MucThu(int idMucThu, String mucThu) {
        this.idMucThu = idMucThu;
        MucThu = mucThu;
    }

    public int getIdMucThu() {
        return idMucThu;
    }

    public void setIdMucThu(int idMucThu) {
        this.idMucThu = idMucThu;
    }

    public String getMucThu() {
        return MucThu;
    }

    public void setMucThu(String mucThu) {
        MucThu = mucThu;
    }
}
