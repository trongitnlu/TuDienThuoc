package com.tudien.tudienthuoc.user;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.iDialog;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateDialog extends DialogFragment implements iDialog {
    EditText name;
    RadioGroup sex;
    String gioiTinh = "Nam";
    RadioButton r_nam, r_nu;
    RequestQueue requestQueue;
    StringRequest request;
    Button btk_update, btn_thoat;
    final static String URL = "http://trong.890m.com/update.php";
    Activity activity;
    Account account;

    public UpdateDialog(Account account) {
        this.account = account;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update, container, false);
        getDialog().getWindow().setLayout(300, 400);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setTitle("Update");
//        setDialogPosition(rootView);
        imageView = (ImageView) view.findViewById(R.id.update_avatar);
        r_nam = (RadioButton) view.findViewById(R.id.r_nam);
        r_nu = (RadioButton) view.findViewById(R.id.r_nu);
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivityForResult(intent, 1);

            }
        });

        anhXa(view);
        setViewAccount(account);
        return view;
    }

    private void setViewAccount(Account account) {
        if (!ProfileDialog.urlImage.isEmpty()) {
            Picasso.with(activity).load(ProfileDialog.urlImage).into(imageView);
        }
        name.setText(account.name);
        if (account.sex.equals("Nam")) {
            r_nam.setChecked(true);
            r_nu.setChecked(false);
        } else {
            r_nu.setChecked(true);
            r_nam.setChecked(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == activity.RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            upload();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void anhXa(View view) {


        name = (EditText) view.findViewById(R.id.dk_name);
        btn_thoat = (Button) view.findViewById(R.id.btn_thoatUp);
        btk_update = (Button) view.findViewById(R.id.btn_update);
        requestQueue = Volley.newRequestQueue(activity);
        btk_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Đang cập nhật");
                progressDialog.show();
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(activity, jsonObject.getString("success"), Toast.LENGTH_LONG).show();
                                Account account = new Account(DangNhapDialog.ID_USER, ProfileDialog.email, "", name.getText().toString(), gioiTinh, ProfileDialog.urlImage + "");
                                dangKiUser(account);
                                DbAssetBookmark assetBookmark = new DbAssetBookmark(activity);
                                assetBookmark.insertUser(account);
                                progressDialog.dismiss();
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
                        progressDialog.dismiss();
                        Toast.makeText(activity, "Kết nối mạng không ổn định", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //Toast.makeText(activity, "ngon" + ProfileDialog.email, Toast.LENGTH_LONG).show();
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("email", ProfileDialog.email);
                        hashMap.put("name", name.getText().toString());
                        hashMap.put("sex", gioiTinh);
                        hashMap.put("img", ProfileDialog.urlImage);
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
//        String encrypedPwd = Base64.encodeToString(account.email.getBytes(), Base64.DEFAULT);
//        DatabaseReference con = mData.child("Conversation").child(encrypedPwd);
//        ChatMessage message = new ChatMessage();
//        message.setMe(true);
//        message.setId(0);
//        message.setUserId(account.id);
//        con.child("Messager").push().setValue(message);
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("User").child(account.id + "").setValue(account);


    }

    ImageView imageView;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    Uri downloadUrl;
    String urlImage = ProfileDialog.urlImage;
    ProgressDialog progressDialog;

    public void upload() {
        // Get the data from an ImageView as bytes
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Đang tải lên");
        progressDialog.show();
        btk_update.setVisibility(View.INVISIBLE);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child("image" + ProfileDialog.email.replace("@", "1") + ".png");
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
                urlImage = downloadUrl.toString();
                ProfileDialog.urlImage = urlImage;
                progressDialog.dismiss();
                btk_update.setVisibility(View.VISIBLE);
            }
        });
    }
}
