package com.example.quanli_thietbitruonghoc;

public class class_admin {
    private String taikhoan;
    private Integer sdt;
    private String matkhau;
    private byte[] avatar;

    public class_admin(String taikhoan, Integer sdt, String matkhau, byte[] avatar) {
        this.taikhoan = taikhoan;
        this.sdt = sdt;
        this.matkhau = matkhau;
        this.avatar = avatar;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public Integer getSdt() {
        return sdt;
    }

    public void setSdt(Integer sdt) {
        this.sdt = sdt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
