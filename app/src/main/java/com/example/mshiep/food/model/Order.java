package com.example.mshiep.food.model;

public class Order {
    public String maOrder;
    public String maKH;
    public String tenMon;
    public long giaMon;
    public int soLuong;
    public String soBan;
    public String ngayOrder;
    public String thangOrder;
    public String namOrder;
    public Order() {
    }

    public Order(String maOrder, String maKH, String tenMon, long giaMon, int soLuong, String soBan, String ngayOrder, String thangOrder, String namOrder) {
        this.maOrder = maOrder;
        this.maKH = maKH;
        this.tenMon = tenMon;
        this.giaMon = giaMon;
        this.soLuong = soLuong;
        this.soBan = soBan;
        this.ngayOrder = ngayOrder;
        this.thangOrder = thangOrder;
        this.namOrder = namOrder;
    }

    public String getMaOrder() {
        return maOrder;
    }

    public void setMaOrder(String maOrder) {
        this.maOrder = maOrder;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public long getGiaMon() {
        return giaMon;
    }

    public void setGiaMon(long giaMon) {
        this.giaMon = giaMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getSoBan() {
        return soBan;
    }

    public void setSoBan(String soBan) {
        this.soBan = soBan;
    }

    public String getNgayOrder() {
        return ngayOrder;
    }

    public void setNgayOrder(String ngayOrder) {
        this.ngayOrder = ngayOrder;
    }

    public String getThangOrder() {
        return thangOrder;
    }

    public void setThangOrder(String thangOrder) {
        this.thangOrder = thangOrder;
    }

    public String getNamOrder() {
        return namOrder;
    }

    public void setNamOrder(String namOrder) {
        this.namOrder = namOrder;
    }
}
