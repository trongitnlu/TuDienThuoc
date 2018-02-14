package com.tudien.tudienthuoc.model;


public class Account {
    public int id;
    public String email;
    public String pass;
    public String name;
    public String sex;
    public String img;
    public String conversation;
    public Account(int id, String email, String pass, String name, String sex, String img) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.sex = sex;
        this.img = img;
    }

    public Account() {
    }
}
