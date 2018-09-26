package com.example.mshiep.food.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mshiep.food.R;

import java.text.DecimalFormat;import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.adapter.OrderAdapter;
import com.example.mshiep.food.model.KhachHang;
import com.example.mshiep.food.model.Order;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    ListView lvGoiMon;
    TextView txtthongbaoOrder;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTuc;
    OrderAdapter orderAdapter;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        anhXa();
        setDrawerLayout();
        setClickNavigationView();
        checkData();
        eventUltil();
        catchOnItemListview();
        eventButton();
    }
    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutaa);
        mToggle = new ActionBarDrawerToggle(OrderActivity.this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(true);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setClickNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuHome:
                                Intent intent = new Intent(OrderActivity.this,HomeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.menuOrder:
                                Intent intentSearch = new Intent(OrderActivity.this, OrderActivity.class);
                                startActivity(intentSearch);
                                break;
                            case R.id.menuInfor:
                                Intent intentInfor = new Intent(OrderActivity.this, Information.class);
                                startActivity(intentInfor);
                                break;
                            case R.id.menuLogout:
                                Intent intentLog = new Intent(OrderActivity.this, DangNhapActivity.class);
                                startActivity(intentLog);
                                break;
                        }

                        return true;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private void eventButton() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.mangOrder.size()>0){
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    HomeActivity.mangOrder.clear();

                }else {
                    Check.showToast(getApplicationContext(),"Bạn Chưa gọi món");
                }
            }
        });

    }

    private void catchOnItemListview() {
        lvGoiMon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setTitle("Xác nhận hủy món");
                builder.setMessage("Bạn có muốn hủy món này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (HomeActivity.mangOrder.size()<=0){
                            txtthongbaoOrder.setVisibility(View.VISIBLE);
                        }else {
                            HomeActivity.mangOrder.remove(position);
                            orderAdapter.notifyDataSetChanged();
                            eventUltil();
                            if (HomeActivity.mangOrder.size()<=0){
                                txtthongbaoOrder.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbaoOrder.setVisibility(View.INVISIBLE);
                                orderAdapter.notifyDataSetChanged();
                                eventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderAdapter.notifyDataSetChanged();
                        eventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void eventUltil() {
        long tongtien = 0;
        for (int i =0; i<HomeActivity.mangOrder.size();i++){
            tongtien += HomeActivity.mangOrder.get(i).getGiaMon();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void checkData() {
        if (HomeActivity.mangOrder.size()<=0){
            orderAdapter.notifyDataSetChanged();
            txtthongbaoOrder.setVisibility(View.VISIBLE);
            lvGoiMon.setVisibility(View.INVISIBLE);
        }else {
            orderAdapter.notifyDataSetChanged();
            txtthongbaoOrder.setVisibility(View.INVISIBLE);
            lvGoiMon.setVisibility(View.VISIBLE);
        }
    }

    private void anhXa() {
        mData = FirebaseDatabase.getInstance().getReference("Order");
        lvGoiMon = findViewById(R.id.lvOrder);
        txtthongbaoOrder =findViewById(R.id.txtthongbaoOrder);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan = findViewById(R.id.btnthanhtoan);
        btnTiepTuc = findViewById(R.id.btntieptucOrder);
        orderAdapter = new OrderAdapter(OrderActivity.this,HomeActivity.mangOrder);
        lvGoiMon.setAdapter(orderAdapter);

    }}



