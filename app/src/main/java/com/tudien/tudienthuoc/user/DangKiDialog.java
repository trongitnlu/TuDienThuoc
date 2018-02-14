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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.iDialog;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.Account;
import com.tudien.tudienthuoc.chat.ChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangKiDialog extends DialogFragment implements iDialog {
    EditText name, email, pass, rePass;
    RadioGroup sex;
    String gioiTinh = "Nam";
    RadioButton r_nam, r_nu;
    RequestQueue requestQueue;
    StringRequest request;
    Button btk_dk, btn_thoat;
    final static String URL = "http://trong.890m.com/register.php";

    static final String URL_DN = "http://trong.890m.com/login.php";
    Activity activity;
    private boolean check = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_dangki, container, false);
        getDialog().getWindow().setLayout(300, 500);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setTitle("Đăng kí");
//        setStyle(DialogFragment.STYLE_NO_FRAME, );
//        setDialogPosition(rootView);
        r_nam = (RadioButton) view.findViewById(R.id.r_nam);
        r_nu = (RadioButton) view.findViewById(R.id.r_nu);
        r_nam.setChecked(true);
        r_nam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gioiTinh = r_nam.getText().toString();
                    r_nu.setChecked(false);
                }
            }
        });
        r_nu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gioiTinh = r_nu.getText().toString();
                    r_nam.setChecked(false);
                }
            }
        });
        sex = (RadioGroup) view.findViewById(R.id.radio_group);
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {


        name = (EditText) view.findViewById(R.id.dk_name);
        email = (EditText) view.findViewById(R.id.dk_email);
        pass = (EditText) view.findViewById(R.id.dk_matkhau);
        rePass = (EditText) view.findViewById(R.id.dk_RmatKhau);
        btn_thoat = (Button) view.findViewById(R.id.btn_thoatUp);
        btk_dk = (Button) view.findViewById(R.id.btn_DK);
        requestQueue = Volley.newRequestQueue(activity);


        btk_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().isEmpty() && !name.getText().toString().isEmpty()) {
                    checkMailExists(email.getText().toString());
                } else {
                    Toast.makeText(activity, "Nhập thông tin tài khoản!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void dangKi() {
        boolean checkRepass = (rePass.getText().toString().equals(pass.getText().toString()) && pass.getText().length() >= 6) ? true : false;
        if (checkRepass) {
            Toast.makeText(activity, "Đang đăng kí...", Toast.LENGTH_SHORT).show();
            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.names().get(0).equals("success")) {
                            Toast.makeText(activity, jsonObject.getString("success"), Toast.LENGTH_LONG).show();
                            try {
                                Thread.sleep(1500);
                                login();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            dismiss();
                        } else {
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
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("email", email.getText().toString());
                    hashMap.put("name", name.getText().toString());
                    hashMap.put("pass", pass.getText().toString());
//                        hashMap.put("rePass", email.getText().toString());
                    hashMap.put("sex", gioiTinh);
                    return hashMap;
                }
            };
            requestQueue.add(request);

        } else {
            Toast.makeText(activity, "Mật khẩu không trùng. Nhập lại! (Mật khẩu trên 6 kí tự)", Toast.LENGTH_LONG).show();
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show() {
        this.show(activity.getFragmentManager(), null);
    }

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public void dangKiUser(Account account) {
        DatabaseReference con = mData.child("Conversation").child(account.id + "");
        ChatMessage message = new ChatMessage();
        message.setMe(true);
        message.setId(0);
        message.setUserId(account.id);
        con.child("Messager").push().setValue(message);
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("User").child(account.id + "").setValue(account);


    }

    ProgressDialog progressDialog;


    public void login() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Đang đăng nhập");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, URL_DN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.names().get(0).equals("error")) {
                        DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
                        Account account = new Account((DangNhapDialog.ID_USER = jsonObject.getInt("id")), jsonObject.getString("email"),
                                jsonObject.getString("pass"), jsonObject.getString("name"),
                                jsonObject.getString("sex"), jsonObject.getString("img"));
                        dangKiUser(account);
                        assetBookmark.insertUser(account);
                        MainActivity.checkLogin = true;
                        Thread.sleep(1500);
                        dismiss();
                    } else {
                        Toast.makeText(activity, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
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

    public ProgressDialog progress(String title) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(title);
        return progressDialog;
    }

    public void checkMailExists(final String email) {
        final ProgressDialog progressDialog1 = progress("Đang kiểm tra mail...");
        progressDialog1.show();
        String URL_DN1 = "http://trong.890m.com/checkmail/mail.php?email=" + email;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.GET, URL_DN1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success")) {
                        progressDialog1.dismiss();
                        dangKi();
                    } else {
                        Toast.makeText(activity, "Mail không tồn tại!", Toast.LENGTH_LONG).show();
                        progressDialog1.dismiss();
                    }
                } catch (JSONException e) {
                    Toast.makeText(activity, "Mail không tồn tại!", Toast.LENGTH_LONG).show();
                    progressDialog1.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Kết nối mạng không ổn định!", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }
}
