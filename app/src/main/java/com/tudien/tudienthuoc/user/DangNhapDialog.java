package com.tudien.tudienthuoc.user;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.iDialog;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangNhapDialog extends DialogFragment implements iDialog {
    Button btnDN, btnThoat;
    EditText email, pass;
    RequestQueue requestQueue;
    Activity activity;
    StringRequest request;
    TextView quenMk;
    public static int ID_USER;
    static final String URL = "http://trong.890m.com/login.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        quenMk = (TextView) view.findViewById(R.id.quenMK);
        btnDN = (Button) view.findViewById(R.id.btn_dn);
        btnThoat = (Button) view.findViewById(R.id.btn_thoat);
        email = (EditText) view.findViewById(R.id.dn_email);
        pass = (EditText) view.findViewById(R.id.dn_matkhau);
        requestQueue = Volley.newRequestQueue(activity);
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Đang đăng nhập...");
                progressDialog.show();
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.names().get(0).equals("error")) {
                                DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
                                Account account = new Account((DangNhapDialog.ID_USER = jsonObject.getInt("id")), jsonObject.getString("email"),
                                        jsonObject.getString("pass"), jsonObject.getString("name"),
                                        jsonObject.getString("sex"), jsonObject.getString("img"));
                                assetBookmark.insertUser(account);
                                Toast.makeText(activity, jsonObject.getString("email"), Toast.LENGTH_LONG).show();
                                MainActivity.checkLogin = true;
                                Thread.sleep(1500);
                                progressDialog.dismiss();
                                dismiss();
                            } else {
                                Toast.makeText(activity, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Kết nối mạng không ổn định", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hasMap = new HashMap<String, String>();
                        hasMap.put("email", email.getText().toString());
                        hasMap.put("pass", pass.getText().toString());

                        return hasMap;
                    }
                };
                requestQueue.add(request);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        quenMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Đang kiểm tra...");
                progressDialog.show();
                request = new StringRequest(Request.Method.POST, "http://trong.890m.com/quenmatkhau.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(activity, jsonObject.getString("success"), Toast.LENGTH_LONG).show();
                                MainActivity.checkLogin = true;
                                progressDialog.dismiss();
                                dismiss();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(activity, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hasMap = new HashMap<String, String>();
                        hasMap.put("email", email.getText().toString());
                        hasMap.put("pass", pass.getText().toString());
                        return hasMap;
                    }
                };
                requestQueue.add(request);
            }
        });
        return view;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show() {
        this.show(activity.getFragmentManager(), null);
    }
}
