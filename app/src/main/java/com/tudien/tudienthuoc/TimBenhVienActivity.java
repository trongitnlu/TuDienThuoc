package com.tudien.tudienthuoc;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tudien.tudienthuoc.model.InputSearch;
import com.tudien.tudienthuoc.timkiem.Dialog_SearchBenhVien;

public class TimBenhVienActivity extends AppCompatActivity {
    TextView tinhThanh, quanHuyen, duongXa;
    Dialog_SearchBenhVien dialog_searchBenhVien;
    Button button;
    public static int TINH_THANH = 2;
    public static int QUUAN_HUYEN = 0;
    int id_tp_start =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_benh_vien);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tinhThanh = (TextView) findViewById(R.id.tinhThanh);
        quanHuyen = (TextView) findViewById(R.id.quanHuyen);
        duongXa = (TextView) findViewById(R.id.duongXa);
        button = (Button) findViewById(R.id.btntimkiem_bv);
        tinhThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                dialog_searchBenhVien = new Dialog_SearchBenhVien(TimBenhVienActivity.this, "TINH_THANH");
                dialog_searchBenhVien.show(manager, null);
            }
        });
        quanHuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                dialog_searchBenhVien = new Dialog_SearchBenhVien(TimBenhVienActivity.this, "QUAN_HUYEN");
                dialog_searchBenhVien.show(manager, null);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimBenhVienActivity.this, ListBenhVienActivity.class);
                InputSearch inputSearch = new InputSearch();
                inputSearch.diaChiFilter = duongXa.getText().toString();
                inputSearch.tinhThanhId = TINH_THANH;
                inputSearch.quanHuyenId = QUUAN_HUYEN;
                Bundle bundle = new Bundle();
                bundle.putSerializable("search", inputSearch);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
