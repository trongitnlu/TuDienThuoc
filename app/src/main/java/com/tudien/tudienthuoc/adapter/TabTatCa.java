package com.tudien.tudienthuoc.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.database.DbAssetThuoc;
import com.tudien.tudienthuoc.model.NhomThuoc;

import java.util.ArrayList;

public class TabTatCa extends Fragment {
    TextView text;
    TableLayout tblNhanVien;
    ListView listView;
    Button btnGet;
    MainActivity mainActivity;
    public static CustomAdapter CUSTOM_ADAPTER;
    CustomAdapter customAdapter;
    public TabTatCa(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    DbAssetThuoc assetThuoc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_tatca, container, false);
        assetThuoc = new DbAssetThuoc(view.getContext());
        ArrayList<NhomThuoc> arrayList = assetThuoc.queryNhomThuoc();
        listView = (ListView) view.findViewById(R.id.listItem);
        customAdapter = new CustomAdapter(view.getContext(), R.layout.row_iteam, arrayList);
        CUSTOM_ADAPTER = customAdapter;
        listView.setAdapter(customAdapter);
        customAdapter.setMainActivity(mainActivity);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        customAdapter.notifyDataSetChanged();
    }
}
