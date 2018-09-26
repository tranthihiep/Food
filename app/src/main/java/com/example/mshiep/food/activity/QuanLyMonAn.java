package com.example.mshiep.food.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mshiep.food.adapter.QuanLyMonAnAdapter;
import com.example.mshiep.food.model.MonAn;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mshiep.food.R;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class QuanLyMonAn extends AppCompatActivity {
    int vitri = -1;
    ArrayList<MonAn> monAnArrayList;
    //ArrayList<MonAn> lsClass = new ArrayList<>();
    QuanLyMonAnAdapter quanLyMonAnAdapter;
    Button btnAdd,btnEdit,btnDelete;
    DatabaseReference mData;
    EditText edtTenMonAn,edtGia,edtMoTa;
    TextView edtID;
    Spinner spinnerLoai;
    String tenmonan,tenloai,mota;
    ImageView imgHinh;
    float gia;
    ListView lvQLMA;
    int REQUEST_CODE_CAMERA = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReferenceFromUrl("gs://goimon-ee1a2.appspot.com");
    // Create a storage reference from our app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_mon_an);
        anhXa();
        mData = FirebaseDatabase.getInstance().getReference("MonAn");

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id = Integer.parseInt(etdId.getText().toString());
                gia = Float.parseFloat(edtGia.getText().toString());
                tenmonan = edtTenMonAn.getText().toString();
                tenloai = spinnerLoai.getSelectedItem().toString();
                mota = edtMoTa.getText().toString();
        if (TextUtils.isEmpty(tenmonan)&& TextUtils.isEmpty(tenloai)&&TextUtils.isEmpty(mota))
                {
                    Toast.makeText(QuanLyMonAn.this, "Xin mời nhập lại dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar calendar = Calendar.getInstance();
                    final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                    // Get the data from an ImageView as bytes
                    imgHinh.setDrawingCacheEnabled(true);
                    imgHinh.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgHinh.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();
                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(QuanLyMonAn.this, "Lỗi ảnh", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Toast.makeText(QuanLyMonAn.this, "Lưu hinh ảnh thành công", Toast.LENGTH_SHORT).show();
                            //Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoStringLink = uri.toString();
                                    String id = mData.push().getKey();
                                    MonAn ma = new MonAn(id,tenloai,tenmonan,gia,mota,photoStringLink);
                                    mData.child(id).setValue(ma, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                                Toast.makeText(QuanLyMonAn.this, "Lưu dữ liệu thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(QuanLyMonAn.this, "Lỗi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });


                        }
                    });
                }
            }
        });

        lvQLMA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //monAnArrayList = new ArrayList<>();
                MonAn monAn = (MonAn) monAnArrayList.get(i);
                edtGia.setText("" + monAn.getmGia());
                edtID.setText(monAn.getmMaMonAn());
                edtMoTa.setText(monAn.getmMoTa());
                edtTenMonAn.setText(monAn.getmTenMonAn());
                Picasso.get().load(monAn.getmLinkHinhAnh()).into(imgHinh);
                vitri = i;
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                // Get the data from an ImageView as bytes
                imgHinh.setDrawingCacheEnabled(true);
                imgHinh.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgHinh.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(QuanLyMonAn.this, "Lỗi ảnh", Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Toast.makeText(QuanLyMonAn.this, "Lưu hinh ảnh thành công", Toast.LENGTH_SHORT).show();
                        //Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoStringLink = uri.toString();
                                MonAn monan = new MonAn(edtID.getText().toString(),spinnerLoai.getSelectedItem().toString(),edtTenMonAn.getText().toString(),Float.parseFloat(edtGia.getText().toString()),edtMoTa.getText().toString(),photoStringLink);
                                mData.child(edtID.getText().toString().trim()).setValue(monan);
                                monAnArrayList.set(vitri, monan);
                                quanLyMonAnAdapter.notifyDataSetChanged();
                            }
                        });


                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtID.setText("");
                edtTenMonAn.setText("");
                edtGia.setText("");
                edtMoTa.setText("");
                imgHinh.setImageResource(R.drawable.image);
                spinnerLoai.setSelection(0);
                mData.child(edtID.getText().toString().trim()).removeValue();
                monAnArrayList.remove(vitri);
                quanLyMonAnAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    protected void onStart() {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ma : dataSnapshot.getChildren()){
                    MonAn monAn= ma.getValue(MonAn.class);
                    monAnArrayList.add(monAn);
                    quanLyMonAnAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void anhXa() {
        edtID = findViewById(R.id.edtID);
        imgHinh = findViewById(R.id.imgHinhAnh);
        lvQLMA =findViewById(R.id.lvQuanLyMonAn);
        edtGia = findViewById(R.id.edtGia);
        edtMoTa =findViewById(R.id.edtMoTa);
        edtTenMonAn = findViewById(R.id.edtTenMonAn);
        spinnerLoai=findViewById(R.id.spinnerLoai);
        btnAdd =findViewById(R.id.btnadd);
        btnDelete = findViewById(R.id.btndelete);
        btnEdit = findViewById(R.id.btnedit);
        monAnArrayList = new ArrayList<>();
        quanLyMonAnAdapter = new QuanLyMonAnAdapter(monAnArrayList,getApplicationContext());
        lvQLMA.setAdapter(quanLyMonAnAdapter);
    }
}
