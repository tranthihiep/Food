package com.example.mshiep.food.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mshiep.food.R;
import com.example.mshiep.food.model.KhachHang;
import com.example.mshiep.food.Unit.Check;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangNhapActivity extends AppCompatActivity {

    EditText edtphone,edtpass;
    Button btnDN;
    DatabaseReference mData;
    public static String maKhachHang = "";
    public static String tenKhachHang = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        mData = FirebaseDatabase.getInstance().getReference("KhachHang");
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(DangNhapActivity.this);
                mDialog.setMessage("Xin vui lòng đợi ...");
                mDialog.show();
                mData.orderByChild("sdt").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDialog.dismiss();
                        for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                            KhachHang user = snapshot.getValue(KhachHang.class);
                            if ((edtphone.getText().toString().equals(user.getSDT())) && edtpass.getText().toString().equals(user.getMK())
                                    ){
                                 maKhachHang = user.getMaKH();
                                tenKhachHang = user.getTenKH();
                                Intent intent = new Intent(DangNhapActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }else if((edtphone.getText().toString().equals("admin") && edtpass.getText().toString().equals("admin"))){
                                Intent intent = new Intent(DangNhapActivity.this, AdminMain.class);
                                startActivity(intent);
                            }else
                                Toast.makeText(DangNhapActivity.this, "Số điện thoại hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
    void anhXa(){
        btnDN=findViewById(R.id.btnDN);
        edtphone = findViewById(R.id.edtPhone);
        edtpass = findViewById(R.id.edtPassword);
        Check.typeFaceED(this,edtpass);
        Check.typeFaceED(this,edtphone);
    }

}
