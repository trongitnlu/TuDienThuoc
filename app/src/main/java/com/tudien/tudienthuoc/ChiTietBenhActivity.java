package com.tudien.tudienthuoc;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.tudien.tudienthuoc.adapter.TabYeuThich;
import com.tudien.tudienthuoc.database.DbAssetBenh;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.BookmarkModel;

import java.util.ArrayList;

public class ChiTietBenhActivity extends AppCompatActivity {
    TextView tenBenh;
    WebView noidung;
    Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_benh);
        DbAssetBenh assetBenh = new DbAssetBenh(this);
        final Benh benh = assetBenh.queryBenh(getIntent().getIntExtra("ID", 0));
        setTitle(benh.name);
        tenBenh = (TextView) findViewById(R.id.tenBenh);
        tenBenh.setText(benh.name);
        btnLike = (Button) findViewById(R.id.btnLikeBenh);
        btnLike.setTextSize(1);
        noidung = (WebView) findViewById(R.id.noiDung);
        String style = "\n<style> .bTitle {   font-size: 18px;\n" +
                "    font-family: serif;\n" +
                "    font-weight: bold;\n" +
                "    color: blue;}</style>";
        noidung.loadData(benh.content + style, "text/html; charset=UTF-8", null);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroudButton(btnLike, benh);
            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeBenh();
        for (BookmarkModel e : list) {
            if (e.id == benh.id) {
                Drawable first = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                btnLike.setTextSize(2);
                btnLike.setBackground(first);
            }
        }
    }

    private void setBackgroudButton(Button button, Benh benh) {
        BookmarkModel bookmarkModel = new BookmarkModel(benh.id, benh.name);
        Drawable first = getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
        if (button.getTextDirection()==2) {
            first = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
            button.setBackground(first);
            button.setTextDirection(1);


            DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
            assetBookmark.insertLikeBenh(benh);
            TabYeuThich.LISTBOOKMARLIKE.add(bookmarkModel);
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetInvalidated();
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            button.setTextDirection(2);
            DbAssetBookmark assetBookmark = new DbAssetBookmark(this);
            assetBookmark.deleteLikeBenh(benh.id);
            assetBookmark = new DbAssetBookmark(this);
            notifiTab(assetBookmark.getListLikeBenh());

        }
        // Toast.makeText(context,button.getBackground().getLayoutDirection(),Toast.LENGTH_LONG).show();

    }

    public void notifiTab(ArrayList arrayList) {
        TabYeuThich.LISTBOOKMARLIKE.clear();
        TabYeuThich.LISTBOOKMARLIKE.addAll(arrayList);
        TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
    }
}
