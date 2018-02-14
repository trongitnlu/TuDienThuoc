package com.tudien.tudienthuoc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.tudien.tudienthuoc.MapsActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.model.BenhVien;


import java.util.ArrayList;


public class ListBenhVienAdapter extends ArrayAdapter<BenhVien> implements ListViewAdapter {

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
    private ArrayList<BenhVien> list, orig;
    private Activity activity;


    public ListBenhVienAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<BenhVien> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.resource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        final TextView item = (TextView) convertView.findViewById(R.id.name_benh_vien);
        final TextView diachi = (TextView) convertView.findViewById(R.id.dia_chi_bv);
        final BenhVien model1 = list.get(position);
        item.setText(model1.name);
        diachi.setText(model1.address);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MapsActivity.class);
                intent.putExtra("name", model1.name);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<BenhVien> results = new ArrayList<BenhVien>();
                if (orig == null)
                    orig = list;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final BenhVien g : orig) {
                            if (g.name.toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                list = (ArrayList<BenhVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
