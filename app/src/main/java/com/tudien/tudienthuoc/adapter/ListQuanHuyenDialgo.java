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
import com.tudien.tudienthuoc.model.QuanHuyen;
import com.tudien.tudienthuoc.timkiem.Dialog_SearchBenhVien;

import java.util.ArrayList;


public class ListQuanHuyenDialgo extends ArrayAdapter<QuanHuyen> implements ListViewDialgoAdapter {

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
    private ArrayList<QuanHuyen> list;
    private Dialog_SearchBenhVien dialog;

    public ListQuanHuyenDialgo(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<QuanHuyen> objects) {
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
        final QuanHuyen model1 = list.get(position);
        item.setText(model1.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = dialog.activity.getIntent();
                TimBenhVienActivity.QUUAN_HUYEN = model1.id;
                TextView textView = (TextView) dialog.activity.findViewById(R.id.quanHuyen);
                textView.setText(model1.name);
                dialog.dismiss();
            }
        });
        return convertView;
    }

    public void setDialog(Dialog_SearchBenhVien dialog) {
        this.dialog = dialog;
    }
}
