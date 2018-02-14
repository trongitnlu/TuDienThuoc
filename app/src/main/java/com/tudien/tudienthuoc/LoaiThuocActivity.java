package com.tudien.tudienthuoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tudien.tudienthuoc.adapter.ListLoaiThuoc;
import com.tudien.tudienthuoc.database.DbAssetThuoc;
import com.tudien.tudienthuoc.model.LoaiThuoc;
import com.tudien.tudienthuoc.model.NhomThuoc;

import java.util.ArrayList;

public class LoaiThuocActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    DbAssetThuoc assetThuoc;
    public static int IDNHOM;
    public static NhomThuoc NHOM_THUOC;
    ListLoaiThuoc listLoaiThuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_thuoc);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportParentActivityIntent();
        listView = (ListView) findViewById(R.id.listItemLoaiThuoc);
        assetThuoc = new DbAssetThuoc(this);
        Intent intent = getIntent();
        if (intent.getIntExtra("IDNHOM", 0) != 0) {
            LoaiThuocActivity.IDNHOM = intent.getIntExtra("IDNHOM", 0);
        }
        ArrayList<LoaiThuoc> list = assetThuoc.queryLoaiThuoc(LoaiThuocActivity.IDNHOM);
        listLoaiThuoc = new ListLoaiThuoc(this, R.layout.row_iteam, list);
        // Toast.makeText(this, list.isEmpty()+"", Toast.LENGTH_LONG).show();
        listLoaiThuoc.setLoaiThuocActivity(this);
        listView.setAdapter(listLoaiThuoc);
        setTitle(NHOM_THUOC.name);
//        onCreateSupportNavigateUpTaskStack();

    }

    @Override
    protected void onResume() {
        super.onResume();
        listLoaiThuoc.notifyDataSetChanged();
    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==2){
//            if(resultCode==RESULT_OK){
//                Toast.makeText(this,
//                        String.valueOf(data.getIntExtra(ListThuocActivity.ID_LOAITHUOC,0)),
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_second, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
