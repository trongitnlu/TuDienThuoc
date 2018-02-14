package com.tudien.tudienthuoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.tudien.tudienthuoc.adapter.ListThuocModel;
import com.tudien.tudienthuoc.database.DbAssetThuoc;
import com.tudien.tudienthuoc.model.LoaiThuoc;
import com.tudien.tudienthuoc.model.ThuocModel;

import java.util.ArrayList;

public class ListThuocActivity extends AppCompatActivity {
    public static String ID_LOAITHUOC = "ID_LOAITHUOC";
    DbAssetThuoc assetThuoc;
    ListView listView;
    public static int ID_LOAI;
    ListThuocModel listThuocModel;
    public static LoaiThuoc LOAI_THUOC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_thuoc);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportParentActivityIntent();
        listView = (ListView) findViewById(R.id.listThuoc);
        assetThuoc = new DbAssetThuoc(this);
        ArrayList<ThuocModel> list = assetThuoc.queryListThuoc(ListThuocActivity.ID_LOAI);
        listThuocModel = new ListThuocModel(this, R.layout.row_iteam, list);
        listThuocModel.setActivity(this);
        listView.setAdapter(listThuocModel);
        setTitle(LOAI_THUOC.name);
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        Intent intent = new Intent();
        intent.putExtra("ID_LOAITHUOC", 123);
        setResult(RESULT_OK, intent);
        super.onCreateSupportNavigateUpTaskStack(builder);

    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(getApplicationContext(),"Ngon",Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listThuocModel.notifyDataSetChanged();
    }
}
