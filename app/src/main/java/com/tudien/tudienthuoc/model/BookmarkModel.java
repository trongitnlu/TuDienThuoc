package com.tudien.tudienthuoc.model;



public class BookmarkModel {
    public int id;
    public int id_loai;
    public String name;
    public int nhom_id;

    public BookmarkModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
