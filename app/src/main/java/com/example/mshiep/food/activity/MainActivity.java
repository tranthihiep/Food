package com.example.mshiep.food.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mshiep.food.R;

public class MainActivity extends AppCompatActivity {
    Button btnDangKy,btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(MainActivity.this, DangNhapActivity.class);
                startActivity(intentSignUp);

            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(MainActivity.this,DangKyActivity.class);
                startActivity(intentSignIn);

            }
        });
    }
    void anhXa(){
        btnDangKy =findViewById(R.id.btnSignIn);
        btnDangNhap = findViewById(R.id.btnSignUp);

    }

}
