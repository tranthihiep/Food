package com.example.mshiep.food.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.model.DanhMuc;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemHolder>{
        Context context;
        ArrayList<DanhMuc> arrayListMonAn;
public HomeAdapter(@NonNull Context context, ArrayList<DanhMuc> arrayListMonAn) {
        this.context = context;
        this.arrayListMonAn = arrayListMonAn;
        }
@NonNull
@Override
public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_activity,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
        }

@Override
public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
    Check.typeFaceTV(context,holder.txtTenDM);
        DanhMuc monAn = arrayListMonAn.get(position);
         holder.imgHinhMon.setImageResource(monAn.getHinhAnh());
         holder.txtTenDM.setText(monAn.getTenDanhMuc());
        }

@Override
public int getItemCount() {
        return arrayListMonAn.size();
        }

public class ItemHolder extends RecyclerView.ViewHolder{
    public ImageView imgHinhMon;
    public TextView txtTenDM;

    public  ItemHolder(final View itemView){
        super(itemView);
        imgHinhMon = (ImageView) itemView.findViewById(R.id.imghinh);
        txtTenDM = (TextView) itemView.findViewById(R.id.txtTenDM);
        }
}

}
