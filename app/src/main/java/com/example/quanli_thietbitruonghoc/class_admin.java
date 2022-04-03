package com.example.quanli_thietbitruonghoc;

public class class_admin {
    private Integer id;
    private String taikhoan;
    private String sdt;
    private String matkhau;
    private byte[] avatar;

    public class_admin(Integer id, String taikhoan, String sdt, String matkhau, byte[] avatar) {
        this.id = id;
        this.taikhoan = taikhoan;
        this.sdt = sdt;
        this.matkhau = matkhau;
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
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
