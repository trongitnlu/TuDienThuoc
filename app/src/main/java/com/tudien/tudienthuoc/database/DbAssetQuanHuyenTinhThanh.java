package com.tudien.tudienthuoc.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.LoaiBenh;
import com.tudien.tudienthuoc.model.QuanHuyen;
import com.tudien.tudienthuoc.model.TinhThanh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DbAssetQuanHuyenTinhThanh extends ArrayList<TinhThanh> {
    private static String DATABASE_NAME = "BenhVien.db";
    private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;
    public DbAssetQuanHuyenTinhThanh(Context context){
        myContext = context;
        myDataBase = initDatabase();
    }
    public SQLiteDatabase initDatabase() {
        try {
            String outFileName = this.myContext.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
            File f = new File(outFileName);
            if (!f.exists()) {
                InputStream e = this.myContext.getAssets().open(DATABASE_NAME);
                File folder = new File(this.myContext.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.myContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    public ArrayList<TinhThanh> queryTinhThanh() {
        ArrayList<TinhThanh> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblThanhPho", null);
        if (param.moveToFirst()) {
            do {
                TinhThanh model = new TinhThanh();
                model.id = param.getInt(0);
                model.name = param.getString(1);
                list.add(model);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }
    public ArrayList<QuanHuyen> queryKhuVuc(int id_tp) {
        ArrayList<QuanHuyen> list = new ArrayList<>();
        QuanHuyen quanHuyen = new QuanHuyen();
        quanHuyen.name = "Tất cả";
        quanHuyen.id = 0;
        list.add(0, quanHuyen);
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblKhuVuc where tp_id=" + id_tp, null);
        if (param.moveToFirst()) {
            do {
                QuanHuyen model = new QuanHuyen();
                model.id = param.getInt(0);
                model.tinhThanh_id = param.getInt(1);
                model.name = param.getString(2);
                list.add(model);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }

}
