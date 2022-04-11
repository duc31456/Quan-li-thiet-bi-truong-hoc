package com.example.quanli_thietbitruonghoc.Class;

public class class_baoloithietbi {
    private String matb;
    private String tentb;
    private String tinhtrang;

    public class_baoloithietbi(String matb, String tentb, String tinhtrang) {
        this.matb = matb;
        this.tentb = tentb;
        this.tinhtrang = tinhtrang;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

    public String getMatb() {
        return matb;
    }

    public void setMatb(String matb) {
        this.matb = matb;
    }

    public String getTentb() {
        return tentb;
    }

    public void setTentb(String tentb) {
        this.tentb = tentb;
    }


}
