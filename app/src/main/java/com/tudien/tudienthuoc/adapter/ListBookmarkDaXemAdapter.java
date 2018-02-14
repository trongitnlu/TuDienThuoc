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
import com.tudien.tudienthuoc.ChiTietThuocActivity;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.ListViewAdapter;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.BookmarkDaXem;

import java.util.ArrayList;

import static com.tudien.tudienthuoc.R.id.viewPager;


public class ListBookmarkDaXemAdapter extends ArrayAdapter<BookmarkDaXem> implements ListViewAdapter {

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
    private Activity activity;
    private ArrayList<BookmarkDaXem> list;

    public ListBookmarkDaXemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<BookmarkDaXem> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.resource = resource;
        this.context = context;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        final TextView item = (TextView) convertView.findViewById(R.id.item);
        final BookmarkDaXem model = list.get(position);
        item.setText(model.name);
        final Button button = (Button) convertView.findViewById(R.id.btn_delete);
        button.setTextSize(1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
                if (MainActivity.MENU_LOAI == null || MainActivity.MENU_LOAI.equals("THUOC")) {

//                    Toast.makeText(activity, model.id+"", Toast.LENGTH_LONG).show();
                    assetBookmark.deleteBookmarkThuoc(model.id);
                } else {
//                    Toast.makeText(activity, model.id+"", Toast.LENGTH_LONG).show();
                    assetBookmark.deleteBookmarkBenh(model.id);

                }
                ListBookmarkDaXemAdapter.this.remove(model);
                ListBookmarkDaXemAdapter.this.notifyDataSetInvalidated();
                ListBookmarkDaXemAdapter.this.notifyDataSetChanged();
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.MENU_LOAI == null || MainActivity.MENU_LOAI.equals("THUOC")) {
                    Intent intent = new Intent(activity, ChiTietThuocActivity.class);
                    intent.putExtra("ID", model.id);
                    activity.startActivityForResult(intent, 3);
                } else {
                    Intent intent = new Intent(activity, ChiTietBenhActivity.class);
                    intent.putExtra("ID", model.id);
                    activity.startActivityForResult(intent, 12);

                }
            }
        });
        return convertView;
    }

    private void setBackgroudButton(Button button) {
        Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
        if (button.getTextSize() == 2.0) {
            first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
            button.setBackground(first);
            button.setTextSize(2);
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            button.setTextSize(1);

        }
    }


    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
