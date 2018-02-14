package com.tudien.tudienthuoc.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tudien.tudienthuoc.model.Account;
import com.tudien.tudienthuoc.model.Benh;
import com.tudien.tudienthuoc.model.BookmarkDaXem;
import com.tudien.tudienthuoc.model.BookmarkModel;
import com.tudien.tudienthuoc.model.ThuocModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DbAssetBookmark extends ArrayList<Account> {
    private static String DATABASE_NAME = "bookmark.db";
    private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DbAssetBookmark(Context context) {
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

    public void insertUser(Account account) {
        this.myDataBase.execSQL("delete from account");
        String sql = "insert into account values(" + account.id + ", '" + account.email + "', '" + account.pass + "', '" + account.name + "', '" + account.sex + "', '" + account.img + "' )";
        this.myDataBase.execSQL(sql);
        this.myDataBase.close();
    }

    public int count() {
        int c = 0;
        String sql = "select count(*) from account";
        Cursor param = this.myDataBase.rawQuery(sql, null);
        if (param.moveToFirst()) {
            c = param.getInt(0);
        }

        return c;
    }

    public void removeUser() {
        this.myDataBase.execSQL("delete from account");
        this.myDataBase.close();
    }

    public Account queryUser() {
        Account account = null;
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM account", null);
        if (param.moveToFirst()) {
            account = new Account();
            account.id = param.getInt(0);
            account.email = param.getString(1);
            account.pass = param.getString(2);
            account.name = param.getString(3);
            account.sex = param.getString(4);
            account.img = param.getString(5);
        }
        param.close();
        this.myDataBase.close();
        return account;
    }

    public ArrayList<BookmarkDaXem> getListBookmarkThuoc() {
        ArrayList<BookmarkDaXem> list = new ArrayList<>();
        Cursor cursor = this.myDataBase.rawQuery("Select * from bookmarkThuoc", null);
        if (cursor.moveToFirst())
            do {
                BookmarkDaXem bookmarkThuoc = new BookmarkDaXem(cursor.getInt(0), cursor.getString(1));
                list.add(bookmarkThuoc);
            } while (cursor.moveToNext());
        cursor.close();
        this.myDataBase.close();
        return list;
    }

    public ArrayList<BookmarkDaXem> getListBookmarkBenh() {
        ArrayList<BookmarkDaXem> list = new ArrayList<>();
        Cursor cursor = this.myDataBase.rawQuery("Select * from bookmarkBenh", null);
        if (cursor.moveToFirst())
            do {
                BookmarkDaXem bookmarkBenh = new BookmarkDaXem(cursor.getInt(0), cursor.getString(1));
                list.add(bookmarkBenh);
            } while (cursor.moveToNext());
        cursor.close();
        this.myDataBase.close();
        return list;
    }

    public void insertBookmarkThuoc(ThuocModel thuocModel) {
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM bookmarkThuoc where id=" + thuocModel.id, null);
        if (!param.moveToFirst()) {
            param.close();
            String sql = "insert into bookmarkThuoc values(" + thuocModel.id + ", '" + thuocModel.name + "' )";
            this.myDataBase.execSQL(sql);
        }
        this.myDataBase.close();
    }

    public void insertBookmarkBenh(Benh benh) {
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM bookmarkBenh where id=" + benh.id, null);
        if (!param.moveToFirst()) {
            param.close();
            String sql = "insert into bookmarkBenh values(" + benh.id + ", \"" + benh.name + "\" )";
            this.myDataBase.execSQL(sql);
            this.myDataBase.close();
        }
    }

    public void deleteBookmarkThuoc(int id) {
        this.myDataBase.execSQL("DELETE FROM bookmarkThuoc where id=" + id);
        this.myDataBase.close();
    }

    public void deleteBookmarkBenh(int id) {
        this.myDataBase.execSQL("DELETE FROM bookmarkBenh where id=" + id);
        this.myDataBase.close();
    }

    public void insertLikeThuoc(ThuocModel thuocModel, int nhom_id) {
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM likeThuoc where id=" + thuocModel.id, null);
        if (!param.moveToFirst()) {
            param.close();
            String sql = "insert into likeThuoc values(" + thuocModel.id + ", '" + thuocModel.name + "', " + thuocModel.loai_id + ", " + nhom_id + " )";
            this.myDataBase.execSQL(sql);
        }
        this.myDataBase.close();
    }

    public void insertLikeBenh(Benh benh) {
        Cursor param = this.myDataBase.rawQuery("SELECT * FROM likeBenh where id=" + benh.id, null);
        if (!param.moveToFirst()) {
            param.close();
            String sql = "insert into likeBenh values(" + benh.id + ", '" + benh.name + "', " + benh.id_cat + " )";
            this.myDataBase.execSQL(sql);
            this.myDataBase.close();
        }
    }

    public void deleteLikeThuoc(int id) {
        this.myDataBase.execSQL("DELETE FROM likeThuoc where id=" + id);
        this.myDataBase.close();
    }

    public void deleteLikeBenh(int id) {
        this.myDataBase.execSQL("DELETE FROM likeBenh where id=" + id);
        this.myDataBase.close();
    }

    public ArrayList<BookmarkModel> getListLikeBenh() {
        ArrayList<BookmarkModel> list = new ArrayList<>();
        Cursor cursor = this.myDataBase.rawQuery("Select * from likeBenh", null);
        if (cursor.moveToFirst())
            do {
                BookmarkModel bookmarkBenh = new BookmarkModel(cursor.getInt(0), cursor.getString(1));
                bookmarkBenh.id_loai = cursor.getInt(2);
                list.add(bookmarkBenh);
            } while (cursor.moveToNext());
        cursor.close();
        this.myDataBase.close();
        return list;
    }

    public ArrayList<BookmarkModel> getListLikeThuoc() {
        ArrayList<BookmarkModel> list = new ArrayList<>();
        Cursor cursor = this.myDataBase.rawQuery("Select * from likeThuoc", null);
        if (cursor.moveToFirst())
            do {
                BookmarkModel bookmarkThuoc = new BookmarkModel(cursor.getInt(0), cursor.getString(1));
                bookmarkThuoc.id_loai = cursor.getInt(2);
                bookmarkThuoc.nhom_id = cursor.getInt(3);
                list.add(bookmarkThuoc);
            } while (cursor.moveToNext());
        cursor.close();
        this.myDataBase.close();
        return list;
    }

    public void open() {
        this.myDataBase = initDatabase();
    }
}
