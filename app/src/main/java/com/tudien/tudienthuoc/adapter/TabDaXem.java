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
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.BookmarkDaXem;

import java.util.ArrayList;

public class TabDaXem extends Fragment {
    TextView text;
    TableLayout tblNhanVien;
    ListView listView;
    Button btnGet;
    MainActivity mainActivity;
    public static String LOAI = "THUOC";
    public final String BOOKMARK_THUOC = "THUOC";

    public TabDaXem(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    DbAssetBookmark assetBookmark;
    ArrayList<BookmarkDaXem> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_tatca, container, false);
        assetBookmark = new DbAssetBookmark(view.getContext());
        if (MainActivity.MENU_LOAI != null) {
            if (MainActivity.MENU_LOAI.equals(BOOKMARK_THUOC)) {
                arrayList = assetBookmark.getListBookmarkThuoc();
            } else {
                arrayList = assetBookmark.getListBookmarkBenh();
            }
        } else {
            arrayList = assetBookmark.getListBookmarkThuoc();
        }
        listView = (ListView) view.findViewById(R.id.listItem);
        ListViewAdapter customAdapter = new ListBookmarkDaXemAdapter(view.getContext(), R.layout.row_iteam_da_xem, arrayList);
        listView.setAdapter(customAdapter);
        customAdapter.setActivity(mainActivity);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), position + "--" + id, Toast.LENGTH_LONG).show();
//            }
//        });
        return view;
    }

}
