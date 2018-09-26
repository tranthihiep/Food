package com.example.mshiep.food.model;

public class  KhachHang {
    private String MaKH;
    private String TenKH;
    private String NgaySinh;
    private String GioiTinh;
    private String SDT;
    private String MK;

    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String ngaySinh, String gioiTinh, String SDT, String MK) {
        MaKH = maKH;
        TenKH = tenKH;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        this.SDT = SDT;
        this.MK = MK;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public  String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public   String getMK() {
        return MK;
    }

    public void setMK(String MK) {
        this.MK = MK;
    }
}
