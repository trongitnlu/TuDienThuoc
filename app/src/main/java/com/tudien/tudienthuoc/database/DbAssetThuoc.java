package com.tudien.tudienthuoc.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tudien.tudienthuoc.model.LoaiThuoc;
import com.tudien.tudienthuoc.model.NhomThuoc;
import com.tudien.tudienthuoc.model.ThuocModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class DbAssetThuoc {
    private static String DATABASE_NAME = "TuDienThuoc.db";
    private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DbAssetThuoc(Context paramContext) {
        this.myContext = paramContext;
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

    public ArrayList<LoaiThuoc> queryLoaiThuoc(int id_nhom) {
        String cmd = "select * from tblLoaiThuoc where nhom_id = " + id_nhom;
        ArrayList<LoaiThuoc> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery(cmd, null);
        if (param.moveToFirst()) {
            do {
                LoaiThuoc loaiThuoc = new LoaiThuoc();
                loaiThuoc.id = param.getInt(0);
                loaiThuoc.name = param.getString(2);
                loaiThuoc.id_nhom = param.getInt(1);
                list.add(loaiThuoc);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }

    public ArrayList<NhomThuoc> queryNhomThuoc() {
        ArrayList<NhomThuoc> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblNhomThuoc", null);
        if (param.moveToFirst()) {
            do {
                NhomThuoc loaiThuoc = new NhomThuoc();
                loaiThuoc.id = param.getInt(0);
                loaiThuoc.name = param.getString(1);
                list.add(loaiThuoc);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }

    public ThuocModel queryThuoc(int id) {
        ThuocModel thuocModel = new ThuocModel();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblThuoc where id=" + id, null);
        if (param.moveToFirst()) {
            thuocModel.id = param.getInt(0);
            thuocModel.loai_id = param.getInt(1);
            thuocModel.name = param.getString(2);
            thuocModel.thanhphan = param.getString(3);
            thuocModel.chidinh = param.getString(4);
            thuocModel.chongchidinh = param.getString(5);
            thuocModel.lieuluong = param.getString(6);
            thuocModel.cachsudung = param.getString(7);
            thuocModel.tacdungphu = param.getString(8);
            thuocModel.chuy = param.getString(9);
            thuocModel.baoquan = param.getString(10);
            thuocModel.donggoi = param.getString(11);
            thuocModel.hansudung = param.getString(12);
            thuocModel.price = param.getInt(13);
            thuocModel.image = param.getString(15);
        }
        param.close();
        this.myDataBase.close();
        return thuocModel;
    }

    public ArrayList<ThuocModel> queryListThuoc(int id_loai) {
        ArrayList<ThuocModel> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM tblThuoc where loai_id=" + id_loai, null);
        if (param.moveToFirst()) {
            do {
                ThuocModel thuocModel = new ThuocModel();
                thuocModel.id = param.getInt(0);
                thuocModel.loai_id = param.getInt(1);
                thuocModel.name = param.getString(2);
                list.add(thuocModel);
            } while (param.moveToNext());
        }
        param.close();
        this.myDataBase.close();
        return list;
    }

    public ArrayList<ThuocModel> querySearch(String nameSearch) {
        ArrayList<ThuocModel> list = new ArrayList<>();
        Cursor param = this.myDataBase.rawQuery("select * from tblThuoc where content_search like '%" + nameSearch + "%' or name  like '%" + nameSearch + "%' LIMIT 100", null);
        if (param.moveToFirst()) {
            do {
                ThuocModel thuocModel = new ThuocModel();
                thuocModel.id = param.getInt(0);
                thuocModel.loai_id = param.getInt(1);
                thuocModel.name = param.getString(2);
                list.add(thuocModel);
            } while (param.moveToNext());
        }
        param.close();
        return list;
    }

    public void updateBookmark(int id, int n) {
        String sql = "UPDATE tblThuoc SET bookmark=" + n + " WHERE id=" + id;
        this.myDataBase.execSQL(sql);
        this.closeDatabase();
    }

    public void closeDatabase() {
        if (myDataBase.isOpen()) {
            myDataBase.close();
        }
    }
}
