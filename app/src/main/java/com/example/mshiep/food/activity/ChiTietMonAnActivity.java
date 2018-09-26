package com.example.mshiep.food.activity;

import android.content.Intent;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.adapter.CommentAdapter;
import com.example.mshiep.food.adapter.HomeAdapter;
import com.example.mshiep.food.adapter.QuanLyMonAnAdapter;
import com.example.mshiep.food.model.DanhGia;
import com.example.mshiep.food.model.MonAn;
import com.example.mshiep.food.model.Order;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChiTietMonAnActivity extends AppCompatActivity implements RatingDialogListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinnerSL,spinnerBan;
    Button btnthem;
    ImageButton btnCmt;
    String id = "" ;
    String tenchitiet = "";
    float gia = 0;
    String mota = "";
    String idloaimon = "";
    DatabaseReference mData;
    DatabaseReference mRate;
    Date date;
    SimpleDateFormat sdf,sdfMonth,sdfYear;
    RecyclerView recyclerView;
    RatingBar ratingBar;
    ArrayList<DanhGia> monAnArrayList;
    CommentAdapter quanLyMonAnAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        anhXa();
        setDrawerLayout();
        setClickNavigationView();
        getInformation();
        catchEventSpinner();
        eventButton();
        getRatingFood();
        haha();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private void setDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutaa);
        mToggle = new ActionBarDrawerToggle(ChiTietMonAnActivity.this, mDrawerLayout, R.string.Open, R.string.Close);
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
                                Intent intent = new Intent(ChiTietMonAnActivity.this,HomeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.menuOrder:
                                Intent intentSearch = new Intent(ChiTietMonAnActivity.this, OrderActivity.class);
                                startActivity(intentSearch);
                                break;
                            case R.id.menuInfor:
                                Intent intentInfor = new Intent(ChiTietMonAnActivity.this, Information.class);
                                startActivity(intentInfor);
                                break;
                            case R.id.menuLogout:
                                Intent intentLog = new Intent(ChiTietMonAnActivity.this, DangNhapActivity.class);
                                startActivity(intentLog);
                                break;
                        }

                        return true;
                    }
                });
    }
   private void eventButton() {
       btnthem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String id = mData.push().getKey();
               if (HomeActivity.mangOrder.size()>0){
                   int sl = Integer.parseInt(spinnerSL.getSelectedItem().toString());
                   boolean exists = false;
                   for (int i = 0; i<HomeActivity.mangOrder.size();i++){
                       if (HomeActivity.mangOrder.get(i).getMaOrder() == id){
                           HomeActivity.mangOrder.get(i).setSoLuong(HomeActivity.mangOrder.get(i).getSoLuong() + sl);
                           if (HomeActivity.mangOrder.get(i).getSoLuong()>=10){
                               HomeActivity.mangOrder.get(i).setSoLuong(10);
                           }
                           HomeActivity.mangOrder.get(i).setGiaMon((long) (gia*HomeActivity.mangOrder.get(i).getSoLuong()));
                           exists = true;
                       }
                   }
                   if (exists == false){
                       int soLuong = Integer.parseInt(spinnerSL.getSelectedItem().toString());
                       String sb = spinnerBan.getSelectedItem().toString();
                       long giaMoi = (long) (soLuong * gia);
                       HomeActivity.mangOrder.add(new Order(id,DangNhapActivity.maKhachHang,tenchitiet,giaMoi,
                               soLuong,sb, sdf.format(date),sdfMonth.format(date),sdfYear.format(date)));
                       Order kh = new Order(id,DangNhapActivity.maKhachHang,tenchitiet,giaMoi,soLuong,sb,sdf.format(date),sdfMonth.format(date),sdfYear.format(date));
                       mData.child(id).setValue(kh, new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                               if (databaseError == null) {
                                   Toast.makeText(ChiTietMonAnActivity.this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(ChiTietMonAnActivity.this, "Lỗi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                               }

                           }
                       });
                   }
               }else {
                   int soLuong = Integer.parseInt(spinnerSL.getSelectedItem().toString());
                   long giaMoi = (long) (soLuong * gia);
                   String sb = spinnerBan.getSelectedItem().toString();
                   HomeActivity.mangOrder.add(new Order(id,DangNhapActivity.maKhachHang,tenchitiet,giaMoi,soLuong,sb,
                           sdf.format(date),sdfMonth.format(date),sdfYear.format(date)));

                   Order kh = new Order(id,DangNhapActivity.maKhachHang,tenchitiet,giaMoi,soLuong,sb,sdf.format(date),sdfMonth.format(date),sdfYear.format(date));
                   mData.child(id).setValue(kh, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                           if (databaseError == null) {
                               Toast.makeText(ChiTietMonAnActivity.this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                           } else {
                               Toast.makeText(ChiTietMonAnActivity.this, "Lỗi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                           }

                       }
                   });
               }
               Intent intent = new Intent(ChiTietMonAnActivity.this,OrderActivity.class);
               startActivity(intent);
           }

       });
       btnCmt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showRatingDialog();
           }
       });
    }
    private void catchEventSpinner() {
        Integer [] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter =
                new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        String [] soBan = new String[]{"Bàn 01", "Bàn 02","Bàn 03","Bàn 04","Bàn 05","Bàn 06","Bàn 07","Bàn 08","Bàn 09","Bàn 10",};
        ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,soBan);
        spinnerSL.setAdapter(arrayAdapter);
        spinnerBan.setAdapter(arrayAdapter1);

    }
    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very bad","Not good", "Quite ok", "Very good", "Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this foods")
                .setDescription("Please select some stars and give your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(ChiTietMonAnActivity.this)
                .show();
    }
    @Override
    public void onPositiveButtonClicked(int value, String comments) {
        String id2 = mRate.push().getKey();
        DanhGia ma = new DanhGia(id2,id,value,comments,DangNhapActivity.tenKhachHang);
        mRate.child(id2).setValue(ma, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    getRatingFood();
                    haha();
                    Toast.makeText(ChiTietMonAnActivity.this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChiTietMonAnActivity.this, "Lỗi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {
    }
    private void getRatingFood() {
        Query foodRating = mRate.orderByChild("maMonAn").equalTo(id);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count = 0, sum = 0 ;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    DanhGia item = postSnapshot.getValue(DanhGia.class);
                    sum += item.getRate();
                    count ++;
                }
                if (count != 0){
                    float average = sum /count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getInformation() {
        MonAn monAn = (MonAn) getIntent().getSerializableExtra("thongtinmonan");
        id = monAn.getmMaMonAn();
        idloaimon = monAn.getmTenDanhMuc();
        tenchitiet = monAn.getmTenMonAn();
        gia = monAn.getmGia();
        mota = monAn.getmMoTa();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText(decimalFormat.format(gia)+" Đ");
        txtten.setText(tenchitiet);
        txtmota.setText(mota);
        Picasso.get().load(monAn.getmLinkHinhAnh()).into(imgchitiet);
    }
    private void anhXa() {
         date = new Date();
        String strDateFormat = "dd/MM/yyyy";
        String month = "MM/yyyy";
        String year = "yyyy";
        sdf = new SimpleDateFormat(strDateFormat);
        sdfMonth = new SimpleDateFormat(month);
        sdfYear = new SimpleDateFormat(year);
        mRate  =FirebaseDatabase.getInstance().getReference("DanhGia");
        mData = FirebaseDatabase.getInstance().getReference("Order");
        imgchitiet = findViewById(R.id.imgchitietmonan);
        txtten = findViewById(R.id.txttenchitiet);
        Check.typeFaceTV(this,txtten);
        txtgia = findViewById(R.id.txtgiachitiet);
        Check.typeFaceTV(this,txtgia);
        txtmota = findViewById(R.id.txtmotachitiet);
        spinnerSL = findViewById(R.id.spinnerSoLuong);
        spinnerBan = findViewById(R.id.spinerSoBan);
        btnthem = findViewById(R.id.btnthem);
        btnCmt = findViewById(R.id.btnCmt);
        ratingBar = findViewById(R.id.rating_bar);
        recyclerView = findViewById(R.id.recycer_comment);
        monAnArrayList = new ArrayList<>();
        quanLyMonAnAdapter = new CommentAdapter(getApplicationContext(),monAnArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(quanLyMonAnAdapter);
      ;
    }

    @Override
    public void onNeutralButtonClicked() {

    }
    private  void haha(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                monAnArrayList.clear();
                if (dataSnapshot.exists()){
                    for ( DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DanhGia monAn = snapshot.getValue(DanhGia.class);
                        monAnArrayList.add(monAn);
                    }
                    quanLyMonAnAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query =  FirebaseDatabase.getInstance().getReference("DanhGia").orderByChild("maMonAn").equalTo(id);
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}
