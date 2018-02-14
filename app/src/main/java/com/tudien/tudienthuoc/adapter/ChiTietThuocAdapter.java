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

import com.tudien.tudienthuoc.ChiTietThuocActivity;
import com.tudien.tudienthuoc.ListThuocActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.model.ThuocModel;

import java.util.ArrayList;


public class ChiTietThuocAdapter extends ArrayAdapter<String> {

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    private Context context;
    private int resource;
    private ArrayList<String> listThuoc;
    private ListThuocActivity listThuocActivity;
    public ChiTietThuocAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.listThuoc  = objects;
        this.resource = resource;
        this.context =context;

    }
    public void setThuocActivity(ListThuocActivity thuocActivity){this.listThuocActivity = thuocActivity;}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.chi_tiet_row,parent,false);
//        TextView title = (TextView) convertView.findViewById(R.id.titleCTT);
        TextView item = (TextView) convertView.findViewById(R.id.titleCTT);
        final String thuocModel = listThuoc.get(position);
        item.setText(thuocModel);
        return convertView;
    }

}
