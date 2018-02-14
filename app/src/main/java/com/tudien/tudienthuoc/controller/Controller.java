package com.tudien.tudienthuoc.controller;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.SendMailActivity;
import com.tudien.tudienthuoc.database.DbAssetBenh;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.database.DbAssetQuanHuyenTinhThanh;
import com.tudien.tudienthuoc.database.DbAssetThuoc;

import org.json.JSONException;
import org.json.JSONObject;

public class Controller {
    private Activity activity;
    private final static String URL = "http://trong.890m.com/version/version.php";
    private final static String VERSION = "1.0";

    public Controller(Activity activity) {
        this.activity = activity;
    }

    public void sendMail() {
        Intent intent = new Intent(activity, SendMailActivity.class);
        activity.startActivity(intent);
    }

    public void capNhatDuLieu() {
        DbAssetThuoc dbAssetThuoc = new DbAssetThuoc(activity);
        DbAssetBenh dbAssetBenh = new DbAssetBenh(activity);
        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
        DbAssetQuanHuyenTinhThanh thanh = new DbAssetQuanHuyenTinhThanh(activity);
    }

    public void goMainActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public void goActivity(Class lop) {
        Intent intent = new Intent(activity, lop);
        activity.startActivity(intent);
    }

    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://www.facebook.com";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Chọn phương tiện chia sẻ"));
    }

    public void checkVersion() {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String version = jsonObject.getString("version");
                    if (!version.isEmpty()) {
                        if (!version.equals(VERSION)) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                            alertDialogBuilder.setTitle("Đã có bản cập nhật mới?");
                            alertDialogBuilder
                                    .setMessage("Bấm Có để cập nhật!")
                                    .setCancelable(false)
                                    .setPositiveButton("Cập nhật",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    activity.moveTaskToBack(true);
                                                    android.os.Process.killProcess(android.os.Process.myPid());
                                                    System.exit(1);
                                                }
                                            })

                                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                return;
            }
        });
        requestQueue.add(request);
    }

    public void goLike(TabLayout tabLayout) {
        tabLayout.getTabAt(1).select();
    }

    public void initShowDialog(iDialog iDialog) {
        iDialog.setActivity(activity);
        iDialog.show();

    }
}
