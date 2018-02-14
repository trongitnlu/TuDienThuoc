package com.tudien.tudienthuoc.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.LoaiBenh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DbAssetBenh {
    private static String DATABASE_NAME = "TuDienThuoc.db";
    private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DbAssetBenh(Context context) {
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

    public ArrayList<LoaiBenh> queryLoaiBenh() {
        ArrayList<LoaiBenh> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM benh_category", null);
        if (param.moveToFirst()) {
            do {
                LoaiBenh loaiBenh = new LoaiBenh();
                loaiBenh.id = param.getInt(0);
                loaiBenh.name = param.getString(1);
                list.add(loaiBenh);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }

    public ArrayList<Benh> queryListBenh(int id_loai) {
        ArrayList<Benh> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM benh_chitiet where idcat=" + id_loai, null);
        if (param.moveToFirst()) {
            do {
                Benh benh = new Benh();
                benh.id = param.getInt(0);
                benh.id_cat = param.getInt(3);
                benh.name = param.getString(1);
                benh.content = param.getString(2);
                list.add(benh);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }

    public Benh queryBenh(int id) {
        Benh benh = new Benh();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM benh_chitiet where id=" + id, null);
        if (param.moveToFirst()) {
            benh.id = param.getInt(0);
            benh.id_cat = param.getInt(3);
            benh.name = param.getString(1);
            benh.content = param.getString(2);
        }
        param.close();
        this.myDataBase.close();
        return benh;
    }

    public ArrayList<Benh> querySearch(String nameSearch) {
        ArrayList<Benh> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("select * from benh_chitiet where title like  '%" + nameSearch + "%' LIMIT 100", null);
        if (param.moveToFirst()) {
            do {
                Benh benh = new Benh();
                benh.id = param.getInt(0);
                benh.id_cat = param.getInt(3);
                benh.name = param.getString(1);
                benh.content = param.getString(2);
                list.add(benh);
            } while (param.moveToNext());
        }
        param.close();
        return list;
    }

    public void closeDatabase() {
        if (myDataBase.isOpen()) {
            myDataBase.close();
        }
    }
}
