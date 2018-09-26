package com.example.mshiep.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mshiep.food.R;
import com.example.mshiep.food.model.KhachHang;
import com.example.mshiep.food.model.Order;

import java.util.ArrayList;

public class QuanLyThongKeAdapter extends BaseAdapter {
    ArrayList<Order> monAnArrayList;
    Context context;
    private LayoutInflater inflater;
   // private QuanLyThongKeAdapter.ValueFilter valueFilter;
    private ArrayList<Order> mStringFilterList;

    public QuanLyThongKeAdapter(ArrayList<Order> danhMucArrayList, Context context) {
        this.monAnArrayList = danhMucArrayList;
        this.context = context;
        mStringFilterList =  monAnArrayList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public static class ViewHolder {
        public TextView txtMaOrder, txtMaKHOrder, txtTenMonOrder,txtNgayOrder,txtThanHTien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuanLyThongKeAdapter.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new QuanLyThongKeAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_quan_ly_thong_ke,null);
            viewHolder.txtMaOrder = convertView.findViewById(R.id.txtMaOrder);
            viewHolder.txtMaKHOrder =convertView.findViewById(R.id.txtMaKhachHangOrder);
            viewHolder.txtTenMonOrder =convertView.findViewById(R.id.txtTenMonOrder);
            viewHolder.txtNgayOrder =convertView.findViewById(R.id.txtNgayOrder);
            viewHolder.txtThanHTien = convertView.findViewById(R.id.txtThanhTienOrder);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (QuanLyThongKeAdapter.ViewHolder) convertView.getTag();
        }
        Order khachHang = (Order) getItem(position);
        viewHolder.txtMaOrder.setText("Mã order :" + khachHang.getMaOrder());
        viewHolder.txtMaKHOrder.setText("Mã khách hàng :" + khachHang.getMaKH());
        viewHolder.txtTenMonOrder.setText("Món ăn :" + khachHang.getTenMon());
        viewHolder.txtNgayOrder.setText("Ngày order : " + khachHang.getNgayOrder());
        viewHolder.txtThanHTien.setText("Thành tiền : " + (khachHang.getGiaMon() * khachHang.getSoLuong()));
        return convertView;

    }

}