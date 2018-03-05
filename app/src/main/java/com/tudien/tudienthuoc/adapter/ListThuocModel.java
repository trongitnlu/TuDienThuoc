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
import android.widget.Toast;

import com.tudien.tudienthuoc.ChiTietThuocActivity;
import com.tudien.tudienthuoc.LoaiThuocActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.BookmarkModel;
import com.tudien.tudienthuoc.model.ThuocModel;

import java.util.ArrayList;


public class ListThuocModel extends ArrayAdapter<ThuocModel> {

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
    private ArrayList<ThuocModel> listThuoc;
    private Activity activity;

    public ListThuocModel(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<ThuocModel> objects) {
        super(context, resource, objects);
        this.listThuoc = objects;
        this.resource = resource;
        this.context = context;

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        TextView item = (TextView) convertView.findViewById(R.id.item);
        final Button button = (Button) convertView.findViewById(R.id.btnlike);
        button.setTextSize(1);
        final ThuocModel thuocModel = listThuoc.get(position);
        item.setText(thuocModel.name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
                assetBookmark.insertBookmarkThuoc(thuocModel);
                Intent intent = new Intent(activity, ChiTietThuocActivity.class);
                intent.putExtra("ID", thuocModel.id);
                activity.startActivityForResult(intent, 3);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroudButton(button, thuocModel);

            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeThuoc();
        for (BookmarkModel e : list) {
            if (e.id == thuocModel.id) {
                Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                button.setTextSize(2);
                button.setBackground(first);
            }
        }
        return convertView;
    }
    private void setBackgroudButton(Button button, ThuocModel thuocModel) {
        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();

        BookmarkModel bookmarkModel = new BookmarkModel(thuocModel.id, thuocModel.name);
        Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
        if (button.getTextDirection() == 1) {
            first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
            button.setBackground(first);
            button.setTextDirection(2);

            DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
            assetBookmark.insertLikeThuoc(thuocModel, LoaiThuocActivity.IDNHOM);
            TabYeuThich.LISTBOOKMARLIKE.add(bookmarkModel);
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetInvalidated();
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            button.setTextDirection(1);
            DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
            assetBookmark.deleteLikeThuoc(thuocModel.id);
            assetBookmark = new DbAssetBookmark(activity);
            TabYeuThich.LISTBOOKMARLIKE.clear();
            TabYeuThich.LISTBOOKMARLIKE.addAll(assetBookmark.getListLikeThuoc());
            TabYeuThich.LISTBOOKMARLIKE.notifyDataSetChanged();

        }
        // Toast.makeText(context,button.getBackground().getLayoutDirection(),Toast.LENGTH_LONG).show();

    }
}
