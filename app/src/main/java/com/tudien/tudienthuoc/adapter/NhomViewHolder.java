package com.tudien.tudienthuoc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tudien.tudienthuoc.R;



public class NhomViewHolder extends RecyclerView.ViewHolder {
    public NhomViewHolder(View itemView) {
        super(itemView);
        this.textView = (TextView) itemView.findViewById(R.id.item);
    }
    public TextView textView;
}
