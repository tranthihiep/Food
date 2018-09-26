package com.example.mshiep.food.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mshiep.food.R;
import com.example.mshiep.food.model.MonAn;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuanLyMonAnAdapter extends BaseAdapter {
        ArrayList<MonAn> monAnArrayList;
        Context context;

        public QuanLyMonAnAdapter(ArrayList<MonAn> danhMucArrayList, Context context) {
            this.monAnArrayList = danhMucArrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return monAnArrayList.size() ;
        }

        @Override
        public Object getItem(int position) {
            return monAnArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public class ViewHolder {
            public TextView txtId, txtGia, txtMota,txtTenMonAn,txtLoai;
            public ImageView imgHinhAnh;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder =  new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_quan_ly_mon_an,null);
                viewHolder.txtId = convertView.findViewById(R.id.txtMaSanPham);
                viewHolder.txtTenMonAn =convertView.findViewById(R.id.txtTenMonAn);
                viewHolder.txtGia =convertView.findViewById(R.id.txtGia);
                viewHolder.txtLoai =convertView.findViewById(R.id.txtLoai);
                viewHolder.txtMota =convertView.findViewById(R.id.txtMoTa);
                viewHolder.imgHinhAnh = convertView.findViewById(R.id.imgHinhAnh);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MonAn monAn = (MonAn) getItem(position);
            viewHolder.txtTenMonAn.setText(monAn.getmTenMonAn());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            viewHolder.txtGia.setText("Giá : " + decimalFormat.format(monAn.getmGia())+"Đ");
            viewHolder.txtMota.setMaxLines(2);
            viewHolder.txtMota.setEllipsize(TextUtils.TruncateAt.END);
            viewHolder.txtMota.setText(monAn.getmMoTa());
            viewHolder.txtId.setText("Mã: " + monAn.getmMaMonAn());
            viewHolder.txtLoai.setText("Loại: " + monAn.getmTenDanhMuc());
            Picasso.get().load(monAn.getmLinkHinhAnh()).into(viewHolder.imgHinhAnh);
            return convertView;


        }
    void hh(){

    }
    }

