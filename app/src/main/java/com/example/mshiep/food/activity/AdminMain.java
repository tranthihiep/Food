package com.example.mshiep.food.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mshiep.food.R;

public class AdminMain extends AppCompatActivity {

    Button btbQuanLyMonAn;
    Button btnQuanLyKhachHang,btnQuanLyNhanVien,btnQuanLyHoaDon,btnThongKe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        anhXa();
        btbQuanLyMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMain.this,QuanLyMonAn.class);
                startActivity(intent);
            }
        });
        btnQuanLyKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMain.this,QuanLyKhachHang.class);
                startActivity(intent);
            }
        });
        btnQuanLyHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMain.this,QuanLyHoaDon.class);
                startActivity(intent);
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMain.this,QuanLyThongKe.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        btbQuanLyMonAn =findViewById(R.id.btnQuanLyMonAn);
        btnQuanLyKhachHang = findViewById(R.id.btnQuanLyKhachHang);
        btnQuanLyNhanVien = findViewById(R.id.btnQuanLyNhanVien);
        btnQuanLyHoaDon =findViewById(R.id.btnQuanLyHoaDon);
        btnThongKe =findViewById(R.id.btnThongKe);
    }
}