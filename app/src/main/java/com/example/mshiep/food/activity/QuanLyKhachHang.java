package com.example.mshiep.food.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mshiep.food.R;
import com.example.mshiep.food.adapter.MonAnAdapter;
import com.example.mshiep.food.adapter.QuanLyKhachHangAdapter;
import com.example.mshiep.food.model.KhachHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyKhachHang extends AppCompatActivity{

    ArrayList<KhachHang> khachHangArrayList;
    QuanLyKhachHangAdapter quanLyKhachHangAdapter;
    DatabaseReference mData;
    ListView listViewQLKH;
    EditText edtSearch;
    ImageButton  btndel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khach_hang);
        anhXa();
        mData = FirebaseDatabase.getInstance().getReference("KhachHang");
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allDelete();
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                quanLyKhachHangAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    protected void onStart() {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ma : dataSnapshot.getChildren()){
                    KhachHang monAn= ma.getValue(KhachHang.class);
                    khachHangArrayList.add(monAn);
                    quanLyKhachHangAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
 void allDelete(){
     for(int i=listViewQLKH.getChildCount()-1;i>=0;i--)
     {
         //lấy ra dòng thứ i trong ListView
         //Dòng thứ i sẽ có 3 phần tử: ImageView, TextView, Checkbox
         View v=listViewQLKH.getChildAt(i);
         //Ta chỉ lấy CheckBox ra kiểm tra
         CheckBox chk=(CheckBox) v.findViewById(R.id.cbx);
         if(chk.isChecked())
         {
             //xóa phần tử thứ i ra khỏi danh sách
             mData.child(khachHangArrayList.get(i).getMaKH()).removeValue();
             khachHangArrayList.remove(i);
             chk.setChecked(!chk.isChecked());
         }
         quanLyKhachHangAdapter.notifyDataSetChanged();
     }
    }



    private void anhXa() {
        listViewQLKH =findViewById(R.id.recyclewQLKH);
        khachHangArrayList = new ArrayList<>();
        quanLyKhachHangAdapter = new QuanLyKhachHangAdapter(khachHangArrayList,getApplicationContext());
        listViewQLKH.setAdapter(quanLyKhachHangAdapter);
        btndel=findViewById(R.id.btnalldelete);
        edtSearch = findViewById(R.id.edtSeach);
    }
}
