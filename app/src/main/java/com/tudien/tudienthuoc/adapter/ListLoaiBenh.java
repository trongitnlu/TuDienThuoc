package com.tudien.tudienthuoc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tudien.tudienthuoc.ListBenhActivity;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.BookmarkModel;
import com.tudien.tudienthuoc.model.LoaiBenh;

import java.util.ArrayList;


public class ListLoaiBenh extends ArrayAdapter<LoaiBenh> implements ListViewAdapter {

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     * instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    private Context context;
    private int resource;
    private ArrayList<LoaiBenh> loaiBenhs;
    private Activity activity;

    public ListLoaiBenh(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<LoaiBenh> objects) {
        super(context, resource, objects);
        this.loaiBenhs = objects;
        this.resource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_iteam, parent, false);
        TextView item = (TextView) convertView.findViewById(R.id.item);
        final Button button = (Button) convertView.findViewById(R.id.btnlike);
        final LoaiBenh loaiBenh = loaiBenhs.get(position);
        item.setText(loaiBenh.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBenhActivity.LOAI_BENH = loaiBenh;
                ListBenhActivity.ID_LOAI_BENH = loaiBenh.id;
                Intent intent = new Intent(activity, ListBenhActivity.class);
                intent.putExtra("ID_LOAI", loaiBenh.id);
                activity.startActivityForResult(intent, 11);
            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeBenh();
        for (BookmarkModel e : list) {
            if (e.id_loai == loaiBenh.id) {
                Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                button.setBackground(first);
            }
        }
        return convertView;
    }

//    private void setBackgroudButton(Button button, Benh benh) {
//        BookmarkModel bookmarkModel = new BookmarkModel(benh.id, benh.name);
//        Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
//        if (button.getTextSize() == 2.0) {
//            first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
//            button.setBackground(first);
//            button.setTextSize(2);
//
//            DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
//            assetBookmark.insertLikeBenh(benh);
//            TabYeuThich.LISTBOOKMARLIKE.add(bookmarkModel);
//            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetInvalidated();
//            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
//        } else {
//            button.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
//            button.setTextSize(1);
//            DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
//            assetBookmark.deleteLikeBenh(benh.id);
//            assetBookmark = new DbAssetBookmark(activity);
//            TabYeuThich.LISTBOOKMARLIKE.clear();
//            TabYeuThich.LISTBOOKMARLIKE.addAll(assetBookmark.getListLikeBenh());
//            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
//            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
//
//        }
    // Toast.makeText(context,button.getBackground().getLayoutDirection(),Toast.LENGTH_LONG).show();

//    }

    public void setMainActivity(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
