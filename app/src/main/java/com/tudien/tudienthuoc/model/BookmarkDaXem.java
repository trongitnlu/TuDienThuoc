package com.tudien.tudienthuoc.model;


public class BookmarkDaXem {
    public int id;
    public String name;

    public BookmarkDaXem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookmarkDaXem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
