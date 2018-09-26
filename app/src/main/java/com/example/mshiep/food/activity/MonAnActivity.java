package com.example.mshiep.food.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mshiep.food.R;
import com.example.mshiep.food.adapter.MonAnAdapter;
import com.example.mshiep.food.adapter.QuanLyMonAnAdapter;
import com.example.mshiep.food.model.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MonAnActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    Toolbar toolbarChiTiet;
    ListView lvMonAn;
    ArrayList<MonAn> monAnArrayList;
    MonAnAdapter quanLyMonAnAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an);
        anhXa();
        actionToolBar();
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("haha");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                monAnArrayList.clear();
                if (dataSnapshot.exists()){
                    for ( DataSnapshot snapshot : dataSnapshot.getChildren()){
                        MonAn monAn = snapshot.getValue(MonAn.class);
                        monAnArrayList.add(monAn);
                    }
                    quanLyMonAnAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query =  FirebaseDatabase.getInstance().getReference("MonAn").orderByChild("mTenDanhMuc").equalTo(value1);
        query.addListenerForSingleValueEvent(valueEventListener);

        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(MonAnActivity.this,ChiTietMonAnActivity.class);
                intent1.putExtra("thongtinmonan",  monAnArrayList.get(position));
                startActivity(intent1);
            }
        });

    }
    private void actionToolBar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void anhXa() {
        toolbarChiTiet = findViewById(R.id.toolbarchitiet);
        lvMonAn = (ListView) findViewById(R.id.lvMonAn);
        monAnArrayList = new ArrayList<>();
        quanLyMonAnAdapter = new MonAnAdapter(monAnArrayList,getApplicationContext());
        lvMonAn.setAdapter(quanLyMonAnAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}
