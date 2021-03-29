package com.example.rabbit_house_2;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private String id_HD;
    private String idKH;
    private String id_Mon;
    private String SoLuongHD;

    public HoaDon(String id_HD, String idKH, String id_Mon, String soLuongHD) {
        this.id_HD = id_HD;
        this.idKH = idKH;
        this.id_Mon = id_Mon;
        SoLuongHD = soLuongHD;
    }

    public HoaDon(String idKH, String id_Mon, String soLuongHD) {
        this.idKH = idKH;
        this.id_Mon = id_Mon;
        SoLuongHD = soLuongHD;
    }

    public HoaDon(String idKH, String ten, String id_mon, String tenMon, String s, String toString) {
    }

    public String getId_HD() {
        return id_HD;
    }

    public void setId_HD(String id_HD) {
        this.id_HD = id_HD;
    }

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getId_Mon() {
        return id_Mon;
    }

    public void setId_Mon(String id_Mon) {
        this.id_Mon = id_Mon;
    }

    public String getSoLuongHD() {
        return SoLuongHD;
    }

    public void setSoLuongHD(String soLuongHD) {
        SoLuongHD = soLuongHD;
    }
}
