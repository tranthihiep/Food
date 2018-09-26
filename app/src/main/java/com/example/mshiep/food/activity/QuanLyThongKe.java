package com.example.mshiep.food.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mshiep.food.R;
import com.example.mshiep.food.adapter.MonAnAdapter;
import com.example.mshiep.food.adapter.QuanLyThongKeAdapter;
import com.example.mshiep.food.model.MonAn;
import com.example.mshiep.food.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLyThongKe extends AppCompatActivity

{
    Button btnThongKeThang, btnThongKeNgay, btnThongKeQuy;
    ListView lvTK;
    TextView txtTT;
    String ntn = "";
    float gia = 0;
    ArrayList<Order> monAnArrayList;
    QuanLyThongKeAdapter quanLyMonAnAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thong_ke);
        anhXa();
        btnThongKeNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final Calendar calendar = Calendar.getInstance();
                int nam = calendar.get(Calendar.YEAR);
                int thang = calendar.get(Calendar.MONTH);
                int ngay = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(QuanLyThongKe.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(i,i1,i2);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            monAnArrayList.clear();
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Order monAn = snapshot.getValue(Order.class);
                                    monAnArrayList.add(monAn);
                                    gia = gia + (monAn.getSoLuong()*monAn.getGiaMon());
                                }
                                txtTT.setText("Tong: " + gia);
                                quanLyMonAnAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    Query query = FirebaseDatabase.getInstance().getReference("Order").orderByChild("ngayOrder").equalTo(simpleDateFormat.format(calendar.getTime()));
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
            },nam,thang,ngay);
            datePickerDialog.show();
            }

        });
        btnThongKeThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLogin();
            }

        });
    }
    private void DialogLogin(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_spinner);
        dialog.setCanceledOnTouchOutside(false);

        //ánh xạ
        final Spinner spinnerThang = dialog.findViewById(R.id.spinnerThang);
        final Spinner spinnerNam = dialog.findViewById(R.id.spinnerNam);
        Button btndongy = (Button) dialog.findViewById(R.id.btndongy);
        Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
        String [] soThang = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(QuanLyThongKe.this,android.R.layout.simple_spinner_dropdown_item,soThang);
        spinnerThang.setAdapter(arrayAdapter);

        String [] nam = new String[]{"2017","2018","2019","2020"};
        ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<String>(QuanLyThongKe.this,android.R.layout.simple_spinner_dropdown_item,nam);
        spinnerNam.setAdapter(arrayAdapter1);
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ntn = spinnerThang.getSelectedItem().toString()+"/"+spinnerNam.getSelectedItem().toString();
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        monAnArrayList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Order monAn = snapshot.getValue(Order.class);
                                monAnArrayList.add(monAn);
                                gia = gia + (monAn.getSoLuong()*monAn.getGiaMon());
                            }
                            txtTT.setText("Tong: " + gia);
                            quanLyMonAnAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                Query query = FirebaseDatabase.getInstance().getReference("Order").orderByChild("thangOrder").equalTo(ntn);
                query.addListenerForSingleValueEvent(valueEventListener);

                dialog.dismiss();

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void anhXa() {
        txtTT = findViewById(R.id.txtTT);
        btnThongKeNgay = findViewById(R.id.btnTKTNgay);
        btnThongKeQuy = findViewById(R.id.btnTKTQuy);
        btnThongKeThang = findViewById(R.id.btnTKTThang);
        lvTK = findViewById(R.id.lvTK);
        monAnArrayList = new ArrayList<>();
        quanLyMonAnAdapter = new QuanLyThongKeAdapter(monAnArrayList, getApplicationContext());
        lvTK.setAdapter(quanLyMonAnAdapter);
    }
}