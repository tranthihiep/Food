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
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.model.MonAn;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonAnAdapter extends BaseAdapter {
    ArrayList<MonAn> monAnArrayList;
    Context context;

    public MonAnAdapter(ArrayList<MonAn> danhMucArrayList, Context context) {
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
        public TextView txtGia,txtTenMonAn;
        public ImageView imgHinhAnh;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MonAnAdapter.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder =  new MonAnAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_mon_an,null);
            viewHolder.txtTenMonAn =convertView.findViewById(R.id.txtTenMonAn);
            viewHolder.txtGia =convertView.findViewById(R.id.txtGia);
            Check.typeFaceTV(context,viewHolder.txtTenMonAn);
            Check.typeFaceTV(context,viewHolder.txtGia);
            viewHolder.imgHinhAnh = convertView.findViewById(R.id.imgHinhAnh);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (MonAnAdapter.ViewHolder) convertView.getTag();
        }
        MonAn monAn = (MonAn) getItem(position);
        viewHolder.txtTenMonAn.setText(monAn.getmTenMonAn());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGia.setText(decimalFormat.format(monAn.getmGia()));
        Picasso.get().load(monAn.getmLinkHinhAnh()).into(viewHolder.imgHinhAnh);
        return convertView;

    }

}

