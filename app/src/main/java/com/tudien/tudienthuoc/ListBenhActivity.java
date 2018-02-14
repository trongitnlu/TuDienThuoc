package com.tudien.tudienthuoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.tudien.tudienthuoc.adapter.ListBenh;
import com.tudien.tudienthuoc.database.DbAssetBenh;
import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.LoaiBenh;

import java.util.ArrayList;

public class ListBenhActivity extends AppCompatActivity {
    public static int ID_LOAI_BENH;
    ListBenh listBenh;
    public static LoaiBenh LOAI_BENH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_benh);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DbAssetBenh assetBenh = new DbAssetBenh(this);
        ArrayList<Benh> list = assetBenh.queryListBenh(ID_LOAI_BENH);
        listBenh = new ListBenh(this, R.layout.row_iteam, list);
        listBenh.setActivity(this);
        ListView listView = (ListView) findViewById(R.id.listBenh);
        listView.setAdapter(listBenh);
        setTitle(LOAI_BENH.name);

    }

    @Override
    protected void onResume() {
        super.onResume();
        listBenh.notifyDataSetChanged();
    }
}
