package com.example.mshiep.food.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.model.DanhMuc;
import com.example.mshiep.food.model.KhachHang;
import com.example.mshiep.food.model.MonAn;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuanLyKhachHangAdapter extends BaseAdapter implements Filterable {
    ArrayList<KhachHang> monAnArrayList;
    Context context;
    private LayoutInflater inflater;
    private ValueFilter valueFilter;
    private ArrayList<KhachHang> mStringFilterList;

    public QuanLyKhachHangAdapter(ArrayList<KhachHang> danhMucArrayList, Context context) {
        this.monAnArrayList = danhMucArrayList;
        this.context = context;
        mStringFilterList =  monAnArrayList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
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
        public TextView txtMaKhachHang, txtTenKhachHang, txtGioiTinh,txtNgaySinh,txtSDt, txtMatKhau;
        public static String txtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuanLyKhachHangAdapter.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder =  new QuanLyKhachHangAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_quan_ly_khach_hang,null);
            viewHolder.txtMaKhachHang = convertView.findViewById(R.id.txtMKHHH);
            viewHolder.txtTenKhachHang =convertView.findViewById(R.id.txtTenKhachHang);
            viewHolder.txtGioiTinh =convertView.findViewById(R.id.txtGioiTinh);
            viewHolder.txtNgaySinh =convertView.findViewById(R.id.txtNgaySinh);
            viewHolder.txtSDt =convertView.findViewById(R.id.txtSDT);
            viewHolder.txtMatKhau = convertView.findViewById(R.id.txtMatKhau);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (QuanLyKhachHangAdapter.ViewHolder) convertView.getTag();
        }
        KhachHang khachHang = (KhachHang) getItem(position);
        viewHolder.txtMaKhachHang.setText("Mã :" + khachHang.getMaKH());
        viewHolder.txtTenKhachHang.setText("Tên :" + khachHang.getTenKH());
        viewHolder.txtGioiTinh.setText("Giới tính :" + khachHang.getGioiTinh());
        viewHolder.txtNgaySinh.setText("Ngày sinh : " + khachHang.getNgaySinh());
        viewHolder.txtSDt.setText("SDT : " + khachHang.getSDT());
        viewHolder.txtMatKhau.setText("MK : " + khachHang.getMK());
        return convertView;

    }
    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }
    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<KhachHang> filterList=new ArrayList<KhachHang>();
                for(int i=0;i<mStringFilterList.size();i++){
                    if((mStringFilterList.get(i).getTenKH().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        KhachHang contacts = new KhachHang();
                        contacts.setTenKH(mStringFilterList.get(i).getTenKH());
                        contacts.setMaKH(mStringFilterList.get(i).getMaKH());
                        contacts.setSDT(mStringFilterList.get(i).getSDT());
                        contacts.setGioiTinh(mStringFilterList.get(i).getGioiTinh());
                        contacts.setNgaySinh(mStringFilterList.get(i).getNgaySinh());
                        contacts.setMK(mStringFilterList.get(i).getMK());
                        filterList.add(contacts);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=mStringFilterList.size();
                results.values=mStringFilterList;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            monAnArrayList=(ArrayList<KhachHang>) results.values;
            notifyDataSetChanged();
        }
    }

}