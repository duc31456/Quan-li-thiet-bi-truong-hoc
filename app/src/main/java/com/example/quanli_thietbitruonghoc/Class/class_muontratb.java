package com.example.quanli_thietbitruonghoc.Class;

public class class_muontratb {
    private String matb;
    private String maphong;
    private String nguoimuon;
    private String sdtnguoimuon;
    private String ngaymuon;
    private Integer soluong;
    private String ngaytra;

    public class_muontratb(String matb, String maphong, String nguoimuon, String sdtnguoimuon, String ngaymuon, Integer soluong, String ngaytra) {
        this.matb = matb;
        this.maphong = maphong;
        this.nguoimuon = nguoimuon;
        this.sdtnguoimuon = sdtnguoimuon;
        this.ngaymuon = ngaymuon;
        this.soluong = soluong;
        this.ngaytra = ngaytra;
    }

    public String getNguoimuon() {
        return nguoimuon;
    }

    public void setNguoimuon(String nguoimuon) {
        this.nguoimuon = nguoimuon;
    }

    public String getSdtnguoimuon() {
        return sdtnguoimuon;
    }

    public void setSdtnguoimuon(String sdtnguoimuon) {
        this.sdtnguoimuon = sdtnguoimuon;
    }

    public String getMatb() {
        return matb;
    }

    public void setMatb(String matb) {
        this.matb = matb;
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }
}
