package com.tudien.tudienthuoc.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tudien.tudienthuoc.model.BenhVien;
import com.tudien.tudienthuoc.model.InputSearch;
import com.tudien.tudienthuoc.model.QuanHuyen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DbAssetBenhVien {
    private static String DATABASE_NAME = "BenhVien.db";
    private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DbAssetBenhVien(Context context) {
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

    public ArrayList<BenhVien> search(InputSearch inputSearch) {
        ArrayList<BenhVien> list = new ArrayList<>();
        String cmd = "";
        if (inputSearch.quanHuyenId == 0) {
            List<QuanHuyen> huyens = new ArrayList<>();
            cmd = "SELECT * FROM tblKhuVuc where tp_id =" + inputSearch.tinhThanhId;
            Cursor khuVuc = this.myDataBase.rawQuery(cmd, null);
            if (khuVuc.moveToFirst()) {
                do {
                    QuanHuyen quanHuyen = new QuanHuyen();
                    quanHuyen.id = khuVuc.getInt(0);
                    huyens.add(quanHuyen);
                } while (khuVuc.moveToNext());
            }
            khuVuc.close();
            for (int i = 0; i < huyens.size(); i++) {
                Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblBenhVien where location_id=" + huyens.get(i).id + " and (address like '%" + inputSearch.diaChiFilter + "%' or content_search like '%" + inputSearch.diaChiFilter + "%')", null);
                if (param.moveToFirst()) {
                    do {
                        BenhVien benhVien = new BenhVien();
                        benhVien.id = param.getInt(0);
                        benhVien.location_id = param.getInt(1);
                        benhVien.name = param.getString(2);
                        benhVien.address = param.getString(3);
                        list.add(benhVien);
                    } while (param.moveToNext());
                }
                param.close();
            }
        } else {
            Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblBenhVien where location_id=" + inputSearch.quanHuyenId + " and (address like '%" + inputSearch.diaChiFilter + "%' or content_search like '%" + inputSearch.diaChiFilter + "%')", null);
            if (param.moveToFirst()) {
                do {
                    BenhVien benhVien = new BenhVien();
                    benhVien.id = param.getInt(0);
                    benhVien.location_id = param.getInt(1);
                    benhVien.name = param.getString(2);
                    benhVien.address = param.getString(3);
                    list.add(benhVien);
                } while (param.moveToNext());
            }
            param.close();
        }

        this.myDataBase.close();
        return list;
    }
}
