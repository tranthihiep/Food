package com.example.mshiep.food.model;

import java.io.Serializable;

public class MonAn implements Serializable{
    private String mMaMonAn;
    private String mTenDanhMuc;
    private String mTenMonAn;
    private float mGia;
    private String mMoTa;
    private String mLinkHinhAnh;
    public MonAn(){}

    public MonAn(String mMaMonAn, String mTenDanhMuc, String mTenMonAn, float mGia, String mMoTa, String mLinkHinhAnh) {

        this.mMaMonAn = mMaMonAn;
        this.mTenDanhMuc = mTenDanhMuc;
        this.mTenMonAn = mTenMonAn;
        this.mGia = mGia;
        this.mMoTa = mMoTa;
        this.mLinkHinhAnh = mLinkHinhAnh;

    }

    public String getmMaMonAn() {
        return mMaMonAn;
    }

    public void setmMaMonAn(String mMaMonAn) {
        this.mMaMonAn = mMaMonAn;
    }

    public String getmTenDanhMuc() {
        return mTenDanhMuc;
    }

    public void setmTenDanhMuc(String mTenDanhMuc) {
        this.mTenDanhMuc = mTenDanhMuc;
    }

    public String getmTenMonAn() {
        return mTenMonAn;
    }

    public void setmTenMonAn(String mTenMonAn) {
        this.mTenMonAn = mTenMonAn;
    }

    public float getmGia() {
        return mGia;
    }

    public void setmGia(float mGia) {
        this.mGia = mGia;
    }

    public String getmMoTa() {
        return mMoTa;
    }

    public void setmMoTa(String mMoTa) {
        this.mMoTa = mMoTa;
    }

    public String getmLinkHinhAnh() {
        return mLinkHinhAnh;
    }

    public void setmLinkHinhAnh(String mHinhAnh) {
        this.mLinkHinhAnh = mHinhAnh;
    }

}