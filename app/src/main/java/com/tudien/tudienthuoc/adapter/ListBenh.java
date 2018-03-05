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

import com.tudien.tudienthuoc.ChiTietBenhActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.BookmarkModel;

import java.util.ArrayList;


public class ListBenh extends ArrayAdapter<Benh> implements ListViewAdapter {

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
    private ArrayList<Benh> benhs;
    private Activity activity;

    public ListBenh(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Benh> objects) {
        super(context, resource, objects);
        this.benhs = objects;
        this.resource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        TextView item = (TextView) convertView.findViewById(R.id.item);
        final Button button = (Button) convertView.findViewById(R.id.btnlike);
        button.setTextSize(1);
        final Benh benh = benhs.get(position);
        item.setText(benh.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
                assetBookmark.insertBookmarkBenh(benh);
                Intent intent = new Intent(activity, ChiTietBenhActivity.class);
                intent.putExtra("ID", benh.id);
                activity.startActivityForResult(intent, 12);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroudButton(button, benh);
            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeBenh();
        for (BookmarkModel e : list) {
            if (e.id == benh.id) {
                Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                button.setTextSize(2);
                button.setBackground(first);
            }
        }
        return convertView;
    }

    private void setBackgroudButton(Button button, Benh benh) {
        BookmarkModel bookmarkModel = new BookmarkModel(benh.id, benh.name);
        Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
        if (button.getTextDirection()== 2) {
            first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
            button.setBackground(first);
            button.setTextDirection(1);


            DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
            assetBookmark.insertLikeBenh(benh);
            TabYeuThich.LISTBOOKMARLIKE.add(bookmarkModel);
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetInvalidated();
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            button.setTextDirection(2);

            DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
            assetBookmark.deleteLikeBenh(benh.id);
            assetBookmark = new DbAssetBookmark(activity);
            notifiTab(assetBookmark.getListLikeBenh());

        }
        // Toast.makeText(context,button.getBackground().getLayoutDirection(),Toast.LENGTH_LONG).show();

    }

    public void notifiTab(ArrayList arrayList) {
        TabYeuThich.LISTBOOKMARLIKE.clear();
        TabYeuThich.LISTBOOKMARLIKE.addAll(arrayList);
        TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
