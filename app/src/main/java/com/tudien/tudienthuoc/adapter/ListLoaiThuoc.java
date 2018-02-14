package com.tudien.tudienthuoc.adapter;

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
import android.widget.Toast;

import com.tudien.tudienthuoc.ListThuocActivity;
import com.tudien.tudienthuoc.LoaiThuocActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.BookmarkModel;
import com.tudien.tudienthuoc.model.LoaiThuoc;

import java.util.ArrayList;


public class ListLoaiThuoc extends ArrayAdapter<LoaiThuoc> {

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    private Context context;
    private int resource;
    private ArrayList<LoaiThuoc> listThuoc;
    private LoaiThuocActivity activity;
    public ListLoaiThuoc(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<LoaiThuoc> objects) {
        super(context, resource, objects);
        this.listThuoc  = objects;
        this.resource = resource;
        this.context =context;

    }
    public void setLoaiThuocActivity(LoaiThuocActivity loaiThuocActivity){this.activity = loaiThuocActivity;}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_iteam,parent,false);
        TextView item = (TextView) convertView.findViewById(R.id.item);
        final Button button = (Button) convertView.findViewById(R.id.btnlike);
        final LoaiThuoc loaiThuoc = listThuoc.get(position);
        item.setText(loaiThuoc.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListThuocActivity.ID_LOAI = loaiThuoc.id;
                ListThuocActivity.LOAI_THUOC = loaiThuoc;
                Intent intent = new Intent(activity, ListThuocActivity.class);
                intent.putExtra("IDLOAI", loaiThuoc.id);
                activity.startActivityForResult(intent,2);
            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeThuoc();
        for (BookmarkModel e : list) {
            if (e.id_loai == loaiThuoc.id) {
                Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                button.setBackground(first);
            }
        }
        return convertView;
    }

}
