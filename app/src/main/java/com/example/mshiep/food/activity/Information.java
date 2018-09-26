package com.example.mshiep.food.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class Information extends AppCompatActivity {

    HTextView hTextView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        setDrawerLayout();
        setClickNavigationView();
        hTextView = (HTextView) findViewById(R.id.text);
        Check.typeFaceHTV(Information.this,hTextView);

        // be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText("Nhà hàng ABC xin chào quý khách. Chúc quý khách ngon miệng. Địa chỉ: 111 Lê Lợi Đà Nẵng");
    }
    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutaa);
        mToggle = new ActionBarDrawerToggle(Information.this, mDrawerLayout, R.string.Open, R.string.Close);
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
                                Intent intent = new Intent(Information.this,HomeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.menuOrder:
                                Intent intentSearch = new Intent(Information.this, OrderActivity.class);
                                startActivity(intentSearch);
                                break;
                            case R.id.menuInfor:
                                Intent intentInfor = new Intent(Information.this, Information.class);
                                startActivity(intentInfor);
                                break;
                            case R.id.menuLogout:
                                Intent intentLog = new Intent(Information.this, DangNhapActivity.class);
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
}
