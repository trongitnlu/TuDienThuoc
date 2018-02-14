package com.tudien.tudienthuoc.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tudien.tudienthuoc.adapter.NhomViewHolder;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.model.NhomThuoc;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<NhomViewHolder> {
    /**

     */
    List<NhomThuoc> nhomThuocs;
    Context context;

    public RecycleViewAdapter(Context context, List<NhomThuoc> nhomThuoc) {
        this.context = context;
        this.nhomThuocs = nhomThuoc;

    }


    @Override
    public NhomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_iteam, null);
        NhomViewHolder nhomViewHolder = new NhomViewHolder(view);
        return nhomViewHolder;
    }

    @Override
    public void onBindViewHolder(NhomViewHolder holder, int position) {
        NhomThuoc nhomThuoc = nhomThuocs.get(position);
        holder.textView.setText(nhomThuoc.name);
    }

    @Override
    public int getItemCount() {
        return nhomThuocs == null ? 0 : nhomThuocs.size();
    }
}
