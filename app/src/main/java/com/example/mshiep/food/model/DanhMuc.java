package com.example.mshiep.food.model;

import android.media.Image;

public class DanhMuc {
    String tenDanhMuc;
    int hinhAnh;

    public DanhMuc() {
    }

    public DanhMuc(String tenDanhMuc, int hinhAnh) {
        this.tenDanhMuc = tenDanhMuc;
        this.hinhAnh = hinhAnh;
    }

    public String getTenDanhMuc() {

        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}