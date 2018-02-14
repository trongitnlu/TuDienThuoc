package com.tudien.tudienthuoc.controller;


import android.app.Activity;
import android.widget.ListAdapter;

import com.tudien.tudienthuoc.ListBenhVienActivity;

public interface ListViewAdapter extends ListAdapter{
   void setActivity(Activity activity);
}
