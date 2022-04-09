package com.example.quanli_thietbitruonghoc.Class;

public class class_phonghoc {
    private String maphong;
    private String tenphong;
    private Integer sotang;

    public class_phonghoc(String maphong, String tenphong, Integer sotang) {
        this.maphong = maphong;
        this.tenphong = tenphong;
        this.sotang = sotang;
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public Integer getSotang() {
        return sotang;
    }

    public void setSotang(Integer sotang) {
        this.sotang = sotang;
    }
}
