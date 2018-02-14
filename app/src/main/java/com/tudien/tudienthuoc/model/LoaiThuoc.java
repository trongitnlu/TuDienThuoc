package com.tudien.tudienthuoc.model;


import java.io.Serializable;

public class LoaiThuoc  implements Serializable
{
    public int id;
    public String name ;
    public int id_nhom;
    public LoaiThuoc(){
    super();
    }
    public void setName(String name){ this.name = name; }
}
