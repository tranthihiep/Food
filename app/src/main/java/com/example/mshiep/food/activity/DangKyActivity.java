package com.example.mshiep.food.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mshiep.food.R;
import com.example.mshiep.food.adapter.QuanLyKhachHangAdapter;
import com.example.mshiep.food.model.KhachHang;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class DangKyActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnDky,btnTroVe;
    DatabaseReference mData;
    EditText edtTenKH,edtSDT, edtNgaySinh,edtMk;
    String tenKH,sdt,ngaysinh,mk,gt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anhXa();
        mData = FirebaseDatabase.getInstance().getReference("KhachHang");
        eventInputDateBirthDay();
        btnDky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdt = String.valueOf((edtSDT.getText().toString()));
                tenKH = edtTenKH.getText().toString();
                ngaysinh = edtNgaySinh.getText().toString().trim();
                mk = edtMk.getText().toString().trim();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                gt = radioButton.getText().toString().trim();
                if (TextUtils.isEmpty(sdt)&& TextUtils.isEmpty(tenKH)&&TextUtils.isEmpty(mk))
                {
                    Toast.makeText(DangKyActivity.this, "Xin mời nhập lại dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    String id = mData.push().getKey();
                    KhachHang kh = new KhachHang(id,tenKH,ngaysinh,gt,sdt,mk);
                    mData.child(id).setValue(kh, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                edtMk.setText("");
                                edtNgaySinh.setText("");
                                edtTenKH.setText("");
                                edtSDT.setText("");

                            } else {
                                Toast.makeText(DangKyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void eventInputDateBirthDay() {
        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edtNgaySinh.setText(current);
                    edtNgaySinh.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        };
        edtNgaySinh.addTextChangedListener(tw);
    }
    private void anhXa() {

        btnDky =findViewById(R.id.btnDangKy);
        btnTroVe = findViewById(R.id.btnTroVe);
        edtTenKH = findViewById(R.id.edtTenKH);
        edtSDT =findViewById(R.id.edtSDT);
        edtNgaySinh =findViewById(R.id.edtNgaySinh);
        edtMk = findViewById(R.id.edtMK);
        radioGroup =findViewById(R.id.rggioitinhdki);
    }
}
