package com.example.quanli_thietbitruonghoc.Class;

public class class_thongke {
    private String matb;
    private String tentb;
    private String ngaymuon;
    private Integer soluong;
    private String ngaytra;

    public class_thongke(String matb, String tentb, String ngaymuon, Integer soluong, String ngaytra) {
        this.matb = matb;
        this.tentb = tentb;
        this.ngaymuon = ngaymuon;
        this.soluong = soluong;
        this.ngaytra = ngaytra;
    }

    public String getTentb() {
        return tentb;
    }

    public void setTentb(String tentb) {
        this.tentb = tentb;
    }

    public String getMatb() {
        return matb;
    }

    public void setMatb(String matb) {
        this.matb = matb;
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
