package com.example.mshiep.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.activity.HomeActivity;
import com.example.mshiep.food.activity.OrderActivity;
import com.example.mshiep.food.model.Order;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Order> arrayOrder;
    ViewHolder viewHolder = null;
    public OrderAdapter(Context context, ArrayList<Order> arrayOrder) {
        this.context = context;
        this.arrayOrder = arrayOrder;
    }

    @Override
    public int getCount() {
        return arrayOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayOrder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        public TextView txtTenOrder, txtGiaOrder;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_order,null);
            viewHolder.txtTenOrder = convertView.findViewById(R.id.txtTenOrder);
            viewHolder.txtGiaOrder = convertView.findViewById(R.id.txtGiaOrder);
            Check.typeFaceTV(context,viewHolder.txtTenOrder);
            Check.typeFaceTV(context,viewHolder.txtGiaOrder);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = (Order) getItem(position);
        viewHolder.txtTenOrder.setText(order.getTenMon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaOrder.setText(decimalFormat.format(order.getGiaMon())+ " Đ");
        return convertView;
    }
}
