package com.tudien.tudienthuoc.model;


import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckVersion {
    final static String URL = "http://trong.890m.com/version/version.php";
    final static String VERSION = "1.0";
    public static boolean CHECK_VERSION = true;

    public static void checkVersion(final Activity activity) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String version = jsonObject.getString("version");
                    if (!version.isEmpty()) {
                        if (version.equals(CheckVersion.VERSION))
                            CheckVersion.CHECK_VERSION = true;
                        else CheckVersion.CHECK_VERSION = false;
                        Toast.makeText(activity, CheckVersion.CHECK_VERSION+" nÓ" , Toast.LENGTH_LONG).show();
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
}
