package com.tudien.tudienthuoc.model;



public class Benh {
   public int id;
    public String name;
    public String content;
    public int id_cat;

    public Benh(int id, String name, String content, int id_cat) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.id_cat = id_cat;
    }

    public Benh() {
    }
}
