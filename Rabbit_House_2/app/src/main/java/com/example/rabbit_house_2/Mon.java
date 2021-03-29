package com.example.rabbit_house_2;

import java.io.Serializable;

public class Mon implements Serializable {
    private String id_Mon;
    private String TenMon;
    private String Loai;
    private String Gia;

    public Mon(String id_Mon, String tenMon, String loai, String gia) {
        this.id_Mon = id_Mon;
        TenMon = tenMon;
        Loai = loai;
        Gia = gia;
    }

    public Mon(String tenMon, String loai, String gia) {
        TenMon = tenMon;
        Loai = loai;
        Gia = gia;
    }

    public String getId_Mon() {
        return id_Mon;
    }

    public void setId_Mon(String id_Mon) {
        this.id_Mon = id_Mon;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    @Override
    public String toString() {
        return TenMon;
    }
}
