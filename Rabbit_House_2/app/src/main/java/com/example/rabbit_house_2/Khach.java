package com.example.rabbit_house_2;

import java.io.Serializable;

public class Khach implements Serializable {
    private String idKH;
    private String Ten;
    private String sdt;

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getTen() {
        return Ten;
    }

    public Khach(String ten, String sdt) {
        Ten = ten;
        this.sdt = sdt;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public Khach(String idKH, String ten, String sdt) {
        this.idKH = idKH;
        Ten = ten;
        this.sdt = sdt;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return this.Ten;
    }
}
