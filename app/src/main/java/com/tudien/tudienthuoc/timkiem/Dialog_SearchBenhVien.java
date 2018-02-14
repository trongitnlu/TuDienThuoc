package com.tudien.tudienthuoc.timkiem;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.TimBenhVienActivity;
import com.tudien.tudienthuoc.adapter.ListQuanHuyenDialgo;
import com.tudien.tudienthuoc.adapter.ListTinhThanhDialgo;
import com.tudien.tudienthuoc.controller.ListViewDialgoAdapter;
import com.tudien.tudienthuoc.database.DbAssetQuanHuyenTinhThanh;

import java.util.ArrayList;

public class Dialog_SearchBenhVien extends DialogFragment {
    public Activity activity;
    final String TITLE;
    DbAssetQuanHuyenTinhThanh thanh;
    ListView listView;
    public Bundle bundle;

    public Dialog_SearchBenhVien(Activity activity, String TITLE) {
        this.TITLE = TITLE;
        this.activity = activity;
        thanh = new DbAssetQuanHuyenTinhThanh(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tim_benh_vien, container, false);
        getDialog().setTitle("Chọn Tỉnh Thành");
        TextView textView = (TextView) getDialog().findViewById(android.R.id.title);
        if(textView != null) {
            textView.setGravity(Gravity.CENTER);
        }

        listView = (ListView) view.findViewById(R.id.list_timbv);
        dialog(TITLE);
        return view;
    }

    public void setArguments(Bundle args) {
        this.bundle = args;
    }
    int id_tp = 0;
    public void dialog(String title) {
        if (title.equals("TINH_THANH")) {
            getDialog().setTitle("Chọn tỉnh thành");
            ArrayList arrayList = thanh.queryTinhThanh();
            ListViewDialgoAdapter listTinhThanh = new ListTinhThanhDialgo(activity, R.layout.row_iteam_search_bv, arrayList);
            listTinhThanh.setDialog(this);
            listView.setAdapter(listTinhThanh);

        } else {
            getDialog().setTitle("Chọn quận huyện");
            ArrayList arrayList = thanh.queryKhuVuc(TimBenhVienActivity.TINH_THANH);
            ListViewDialgoAdapter listQuanHuyen = new ListQuanHuyenDialgo(activity, R.layout.row_iteam_search_bv, arrayList);
            listQuanHuyen.setDialog(this);
            listView.setAdapter(listQuanHuyen);
        }
    }

}
