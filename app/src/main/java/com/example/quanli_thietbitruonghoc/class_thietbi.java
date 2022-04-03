package com.example.quanli_thietbitruonghoc;

public class class_thietbi {
    private String matb;
    private String tentb;
    private String xuatxu;
    private String maloai;

    public class_thietbi(String matb, String tentb, String xuatxu, String maloai) {
        this.matb = matb;
        this.tentb = tentb;
        this.xuatxu = xuatxu;
        this.maloai = maloai;
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

    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }
}
