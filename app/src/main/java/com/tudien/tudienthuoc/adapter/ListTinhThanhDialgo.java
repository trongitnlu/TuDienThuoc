package com.tudien.tudienthuoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.TimBenhVienActivity;
import com.tudien.tudienthuoc.controller.ListViewDialgoAdapter;
import com.tudien.tudienthuoc.model.TinhThanh;
import com.tudien.tudienthuoc.timkiem.Dialog_SearchBenhVien;

import java.util.ArrayList;


public class ListTinhThanhDialgo extends ArrayAdapter<TinhThanh> implements ListViewDialgoAdapter {

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     * instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    private Context context;
    private int resource;
    private ArrayList<TinhThanh> list;
    private Dialog_SearchBenhVien dialog;

    public ListTinhThanhDialgo(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<TinhThanh> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.resource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        final TextView item = (TextView) convertView.findViewById(R.id.iteam_search_bv);
        final TinhThanh model1 = list.get(position);
        item.setText(model1.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = dialog.activity.getIntent();
//                intent.putExtra("id_tp", model1.id);
                TimBenhVienActivity.TINH_THANH = model1.id;
                TextView textView = (TextView) dialog.activity.findViewById(R.id.tinhThanh);
                textView.setText(model1.name);
                TextView quanhuyen = (TextView) dialog.activity.findViewById(R.id.quanHuyen);
                quanhuyen.setText("Tất cả");
                TimBenhVienActivity.QUUAN_HUYEN = 0;
                dialog.dismiss();
            }
        });
        return convertView;
    }

    public void setDialog(Dialog_SearchBenhVien dialog) {
        this.dialog = dialog;
    }
}
