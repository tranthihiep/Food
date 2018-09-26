package com.example.mshiep.food.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.mshiep.food.R;
import com.example.mshiep.food.adapter.HomeAdapter;
import com.example.mshiep.food.model.DanhMuc;
import com.example.mshiep.food.model.MonAn;
import com.example.mshiep.food.model.Order;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    RecyclerView recyclerViewManHinhCHinh;
    ArrayList<DanhMuc> danhMucArrayList;
    HomeAdapter homeAdapter;
    public static ArrayList<Order> mangOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anhXa();
        setDrawerLayout();
        setClickNavigationView();
        recyclerViewManHinhCHinh.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewManHinhCHinh ,
                        new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        switch (position){
                            case 0:
                                 Intent intent1 = new Intent(HomeActivity.this, MonAnActivity.class);
                                 intent1.putExtra("haha", "Tráng miệng");
                                 startActivity(intent1);
                                 break;
                            case 1:
                                Intent intent2 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent2.putExtra("haha", "Gỏi nộm");
                                startActivity(intent2);
                                break;
                            case 2:
                                Intent intent3 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent3.putExtra("haha", "Món hấp");
                                startActivity(intent3);
                                break;
                            case 3:
                                Intent intent4 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent4.putExtra("haha", "Món nướng");
                                startActivity(intent4);
                                break;
                            case 4:
                                Intent intent5 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent5.putExtra("haha", "Món chiên, xào");
                                startActivity(intent5);
                                break;
                            case 5:
                                Intent intent6 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent6.putExtra("haha", "Cơm, cháo, mỳ");
                                startActivity(intent6);
                                break;
                            case 6:
                                Intent intent7 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent7.putExtra("haha", "Rau, bún");
                                startActivity(intent7);
                                break;
                            case 7:
                                Intent intent8 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent8.putExtra("haha", "Lẩu");
                                startActivity(intent8);
                                break;
                            case 8:
                                Intent intent9 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent9.putExtra("haha", "Đồ uống");
                                startActivity(intent9);
                                break;
                            case 9:
                                Intent intent10 = new Intent(HomeActivity.this, MonAnActivity.class);
                                intent10.putExtra("haha", "Khác...");
                                startActivity(intent10);
                                break;
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutaa);
        mToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, R.string.Open, R.string.Close);
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
                                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.menuOrder:
                                Intent intentSearch = new Intent(HomeActivity.this, OrderActivity.class);
                                startActivity(intentSearch);
                                break;
                            case R.id.menuInfor:
                                Intent intentInfor = new Intent(HomeActivity.this, Information.class);
                                startActivity(intentInfor);
                                break;
                            case R.id.menuLogout:
                                Intent intentLog = new Intent(HomeActivity.this, DangNhapActivity.class);
                                startActivity(intentLog);
                                break;
                        }

                        return true;
                    }
                });
    }
    private void anhXa(){
        recyclerViewManHinhCHinh = (RecyclerView) findViewById(R.id.recyclerView);
        danhMucArrayList = new ArrayList<>();
        danhMucArrayList.add(new DanhMuc("Tráng miệng",R.drawable.trangmieng));
        danhMucArrayList.add(new DanhMuc("Gỏi nộm",R.drawable.goitron));
        danhMucArrayList.add(new DanhMuc("Món hấp",R.drawable.monhap));
        danhMucArrayList.add(new DanhMuc("Món nướng",R.drawable.monnuong));
        danhMucArrayList.add(new DanhMuc("Món chiên, xào",R.drawable.chienxao));
        danhMucArrayList.add(new DanhMuc("Cơm, cháo, mỳ",R.drawable.commy));
        danhMucArrayList.add(new DanhMuc("Rau, bún",R.drawable.raaubun));
        danhMucArrayList.add(new DanhMuc("Lẩu",R.drawable.lau));
        danhMucArrayList.add(new DanhMuc("Đồ uống",R.drawable.douong));
        danhMucArrayList.add(new DanhMuc("Khác...",R.drawable.khac));

        homeAdapter = new HomeAdapter(getApplicationContext(),danhMucArrayList);
        recyclerViewManHinhCHinh.setHasFixedSize(true);
        recyclerViewManHinhCHinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerViewManHinhCHinh.setAdapter(homeAdapter);
        if (mangOrder != null){

        }else {
            mangOrder =  new ArrayList<>();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
