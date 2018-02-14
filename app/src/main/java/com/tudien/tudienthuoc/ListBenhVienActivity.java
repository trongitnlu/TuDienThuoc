package com.tudien.tudienthuoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tudien.tudienthuoc.adapter.ListBenhVienAdapter;
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.database.DbAssetBenhVien;
import com.tudien.tudienthuoc.model.BenhVien;
import com.tudien.tudienthuoc.model.InputSearch;

import java.util.ArrayList;

public class ListBenhVienActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_benh_vien);
        DbAssetBenhVien assetBenhVien = new DbAssetBenhVien(this);
        InputSearch inputSearch = (InputSearch) getIntent().getExtras().get("search");
//        Toast.makeText(this,inputSearch.quanHuyenId+"", Toast.LENGTH_LONG).show();
        ArrayList<BenhVien> list = assetBenhVien.search(inputSearch);
        ListViewAdapter listViewAdapter = new ListBenhVienAdapter(this, R.layout.row_list_benh_vien ,list );
        listViewAdapter.setActivity(this);
        listView = (ListView) findViewById(R.id.list_view_bv);
        listView.setAdapter(listViewAdapter);
        editText = (EditText) findViewById(R.id.list_search_bv);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
