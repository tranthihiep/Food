package com.example.mshiep.food.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mshiep.food.R;
import com.example.mshiep.food.Unit.Check;
import com.example.mshiep.food.model.DanhGia;
import com.example.mshiep.food.model.DanhMuc;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemHolder>{
    Context context;
    ArrayList<DanhGia> arrayListMonAn;
    public CommentAdapter(@NonNull Context context, ArrayList<DanhGia> arrayListMonAn) {
        this.context = context;
        this.arrayListMonAn = arrayListMonAn;
    }
    @NonNull
    @Override
    public CommentAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_comment,null);
        CommentAdapter.ItemHolder itemHolder = new CommentAdapter.ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ItemHolder holder, int position) {
        Check.typeFaceTV(context,holder.txtTenKhCM);
        DanhGia monAn = arrayListMonAn.get(position);
        holder.txtTenKhCM.setText(monAn.getTenKhachHang());
        holder.txtCMtCM.setText(monAn.getComment());
        holder.ratingBar.setRating(monAn.getRate());
    }

    @Override
    public int getItemCount() {
        return arrayListMonAn.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView txtTenKhCM,txtCMtCM;
        public RatingBar ratingBar;

        public  ItemHolder(final View itemView){
            super(itemView);
            txtTenKhCM = (TextView) itemView.findViewById(R.id.txtTenKHCM);
            txtCMtCM = itemView.findViewById(R.id.txtcmtCM);
            ratingBar  =itemView.findViewById(R.id.ratingCM);
        }
    }

}