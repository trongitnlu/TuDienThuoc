package com.tudien.tudienthuoc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tudien.tudienthuoc.adapter.TabYeuThich;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.database.DbAssetThuoc;
import com.tudien.tudienthuoc.model.BookmarkModel;
import com.tudien.tudienthuoc.model.ThuocModel;

import java.util.ArrayList;

public class ChiTietThuocActivity extends AppCompatActivity {
    DbAssetThuoc assetThuoc;
    ThuocModel thuocModel;
    Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thuoc);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportParentActivityIntent();
        assetThuoc = new DbAssetThuoc(this);
        Intent intent = getIntent();
        thuocModel = assetThuoc.queryThuoc(intent.getIntExtra("ID", 0));
        setTitle(thuocModel.name);
        init();
    }


    TextView textView;
    ImageView imageView1;
    TextView tvTenThuoc, tvDongGoi, tvBaoQuan, tvHanSD, tvThanhPhan, tvGia, tvTP, tvChiDinh, tvChongChiDinh, tvLieuLuong, tvCachSudung, tvChuY, tvTDPhu;

    private void init() {
        textView = (TextView) findViewById(R.id.tenThuoc);
        imageView1 = (ImageView) findViewById(R.id.imageThuoc);
        textView.setText(thuocModel.name);
        Picasso.with(this).load("http://app.vietbao.vn/health-images/drugs/" + thuocModel.image).into(imageView1);
        tvTenThuoc = (TextView) findViewById(R.id.tvtTenThuoc);
        tvGia = (TextView) findViewById(R.id.tvGiaThuoc);
        tvGia.setText(thuocModel.price + "");
        tvBaoQuan = (TextView) findViewById(R.id.tvBaoQuan);
        tvHanSD = (TextView) findViewById(R.id.tvHanSD);
        tvThanhPhan = (TextView) findViewById(R.id.tvThanhPhan);
        tvTenThuoc.setText(thuocModel.name);

        tvBaoQuan.setText(thuocModel.baoquan);
        tvHanSD.setText(thuocModel.hansudung);
        tvThanhPhan.setText(thuocModel.thanhphan);
        tvChiDinh = (TextView) findViewById(R.id.tvChiDinh);
        tvChiDinh.setText(thuocModel.chidinh);
        tvChongChiDinh = (TextView) findViewById(R.id.tvChongCD);
        tvChongChiDinh.setText(thuocModel.chongchidinh);
        tvLieuLuong = (TextView) findViewById(R.id.tvLieuLuong);
        tvLieuLuong.setText(thuocModel.lieuluong);
        tvCachSudung = (TextView) findViewById(R.id.tvCachSD);
        tvCachSudung.setText(thuocModel.cachsudung);
        tvChuY = (TextView) findViewById(R.id.tvChuY);
        tvChuY.setText(thuocModel.chuy);

        tvTDPhu = (TextView) findViewById(R.id.tvTacDungPhu);
        tvTDPhu.setText(thuocModel.tacdungphu);

        btnLike = (Button) findViewById(R.id.btnLikeThuoc);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroudButton(btnLike, thuocModel);
            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeThuoc();
        for (BookmarkModel e : list) {
            if (e.id == thuocModel.id) {
                Drawable first = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                btnLike.setTextSize(2);
                btnLike.setBackground(first);
            }
        }

    }

    private void setBackgroudButton(Button button, ThuocModel thuocModel) {
        BookmarkModel bookmarkModel = new BookmarkModel(thuocModel.id, thuocModel.name);
        Drawable first = getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
        if (button.getTextDirection()==2) {
            first = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
            button.setBackground(first);
            button.setTextDirection(1);

            DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
            assetBookmark.insertLikeThuoc(thuocModel, LoaiThuocActivity.IDNHOM);
            TabYeuThich.LISTBOOKMARLIKE.add(bookmarkModel);
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetInvalidated();
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            button.setTextDirection(2);

            DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
            assetBookmark.deleteLikeThuoc(thuocModel.id);
            assetBookmark = new DbAssetBookmark(this);
            TabYeuThich.LISTBOOKMARLIKE.clear();
            TabYeuThich.LISTBOOKMARLIKE.addAll(assetBookmark.getListLikeThuoc());
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();

        }
    }

}
