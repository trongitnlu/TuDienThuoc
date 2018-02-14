package com.tudien.tudienthuoc.user;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tudien.tudienthuoc.ChatActivity;
import com.tudien.tudienthuoc.MainActivity;
import com.tudien.tudienthuoc.R;
import com.tudien.tudienthuoc.controller.iDialog;
import com.tudien.tudienthuoc.database.DbAssetBookmark;
import com.tudien.tudienthuoc.model.Account;

public class ProfileDialog extends DialogFragment implements iDialog {
    Activity activity;
    TextView pro_name, pro_email, pro_sex, btnDangXuat, pro_chinhSua,btn_tinNhan;
    static   DbAssetBookmark assetBookmark;
    ImageView imageView;
    public static String email;
    public static String urlImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_dialog, container, false);
        pro_chinhSua = (TextView) view.findViewById(R.id.pro_chinhSua);
        imageView = (ImageView)view.findViewById(R.id.pro_avatart);
        pro_email = (TextView) view.findViewById(R.id.pr_email);
        pro_name = (TextView) view.findViewById(R.id.pr_name);
        pro_sex = (TextView) view.findViewById(R.id.pro_sex);
        btnDangXuat = (TextView) view.findViewById(R.id.btn_dangXuat);
        assetBookmark = new DbAssetBookmark(activity);
        final Account account =  assetBookmark.queryUser();
        email = account.email;
        pro_email.setText(account.email);
        pro_name.setText(account.name);
        pro_sex.setText(account.sex);
        if(!account.img.equals("")){
            ProfileDialog.urlImage = account.img;
            Picasso.with(activity).load(account.img).into(imageView);
        }else{
            ProfileDialog.urlImage = "";
        }
//        Toast.makeText(activity, account.email, Toast.LENGTH_LONG).show();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        assetBookmark = new DbAssetBookmark(activity);
//        Toast.makeText(activity, assetBookmark.count()+"", Toast.LENGTH_LONG).show();
        setDialogPosition(view);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetBookmark = new DbAssetBookmark(activity);
                assetBookmark.removeUser();
                dismiss();
            }
        });
        pro_chinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                MainActivity.controller.initShowDialog(new UpdateDialog(account));
//                FragmentManager manager = getFragmentManager();
//                UpdateDialog dialog = new UpdateDialog(account);
//                dialog.setActivity(activity);
//                dialog.show(manager, null);
            }
        });
        btn_tinNhan = (TextView) view.findViewById(R.id.pro_tinNhan);
        btn_tinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("id", account.id);
                activity.startActivity(intent);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(activity, requestCode+", "+ resultCode+"="+ activity.RESULT_OK+" ,"+ data, Toast.LENGTH_LONG).show();
        if(requestCode==1 && resultCode == activity.RESULT_OK && data !=null )
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setDialogPosition(View source) {
        int[] location = new int[2];
        source.getLocationOnScreen(location);
        int sourceX = location[0];
        int sourceY = location[1];

        Window window = getDialog().getWindow();

        // set "origin" to top left corner
//        window.setGravity(Gravity.TOP|Gravity.LEFT);

        WindowManager.LayoutParams params = window.getAttributes();

//        // Just an example; edit to suit your needs.
        params.x = sourceX - dpToPx(120); // about half of confirm button size left of source view
        params.y = sourceY - dpToPx(80); // above source view

        window.setAttributes(params);
    }

    public int dpToPx(float valueInDp) {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show() {
        this.show(activity.getFragmentManager(), null);
    }
}
