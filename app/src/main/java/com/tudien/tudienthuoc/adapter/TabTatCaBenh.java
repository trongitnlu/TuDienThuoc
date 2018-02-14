package com.tudien.tudienthuoc.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.database.DbAssetBenh;
import com.tudien.tudienthuoc.model.LoaiBenh;

import java.util.ArrayList;

public class TabTatCaBenh extends Fragment {
    TextView text;
    TableLayout tblNhanVien;
    static ListView listView;
    Button btnGet;
    Activity activity;
    public static ListLoaiBenh LIST_LOAI_BENH;
    View view;
    ListLoaiBenh listLoaiBenh;
    public TabTatCaBenh(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_tatca, container, false);
        this.view = view;
        DbAssetBenh assetBenh = new DbAssetBenh(view.getContext());
        ArrayList<LoaiBenh> arrayList = assetBenh.queryLoaiBenh();
        listView = (ListView) view.findViewById(R.id.listItem);
        listLoaiBenh = new ListLoaiBenh(view.getContext(), R.layout.row_iteam, arrayList);
        LIST_LOAI_BENH = listLoaiBenh;
        listView.setAdapter(listLoaiBenh);
        listLoaiBenh.setActivity(activity);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        listLoaiBenh.notifyDataSetChanged();
    }
}
