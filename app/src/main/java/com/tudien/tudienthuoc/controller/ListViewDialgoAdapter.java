package com.tudien.tudienthuoc.controller;


import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.tudien.tudienthuoc.model.TinhThanh;
import com.tudien.tudienthuoc.timkiem.Dialog_SearchBenhVien;

public interface ListViewDialgoAdapter extends ListAdapter {

  public   void setDialog(Dialog_SearchBenhVien dialog_searchBenhVien);
}
