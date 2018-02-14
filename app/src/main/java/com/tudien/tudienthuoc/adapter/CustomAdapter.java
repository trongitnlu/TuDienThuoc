package com.tudien.tudienthuoc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tudien.tudienthuoc.LoaiThuocActivity;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.BookmarkModel;
import com.tudien.tudienthuoc.model.NhomThuoc;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<NhomThuoc> {

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
    private MainActivity activity;
    private ArrayList<NhomThuoc> listThuoc;
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<NhomThuoc> objects) {
        super(context, resource, objects);
        this.listThuoc  = objects;
        this.resource = resource;
        this.context =context;

    }

    public void setMainActivity(MainActivity mainActivity){
        this.activity = mainActivity;
    }
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_iteam,parent,false);
        final TextView item = (TextView) convertView.findViewById(R.id.item);
        final NhomThuoc nhomThuoc = listThuoc.get(position);
        item.setText(nhomThuoc.name);
        final Button button = (Button) convertView.findViewById(R.id.btnlike);
        button.setTextSize(1);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setBackgroudButton(button);
//            }
//        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiThuocActivity.NHOM_THUOC = nhomThuoc;
                Intent intent = new Intent(activity, LoaiThuocActivity.class);
                intent.putExtra("IDNHOM", nhomThuoc.id);
                activity.startActivityForResult(intent,1);


            }
        });
        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
        ArrayList<BookmarkModel> list = assetBookmark.getListLikeThuoc();
        for (BookmarkModel e : list) {
            if (e.nhom_id == nhomThuoc.id) {
                Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
                button.setBackground(first);
            }
        }
        return convertView;
    }
    private void setBackgroudButton(Button button){
         Drawable first = context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp);
        if(button.getTextSize()==2.0){
            first = context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
            button.setBackground(first);
            button.setTextSize(2);
        }else{
            button.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
            button.setTextSize(1);
           // button.setBackground(first);

        }
       // Toast.makeText(context,button.getBackground().getLayoutDirection(),Toast.LENGTH_LONG).show();

    }
    public void notifi1(ArrayList arrayList) {
        addAll(arrayList);
        this.notifyDataSetChanged();
        this.notifyDataSetInvalidated();
        this.notifyAll();
    }
}
