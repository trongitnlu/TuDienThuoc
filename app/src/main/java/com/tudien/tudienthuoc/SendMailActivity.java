package com.tudien.tudienthuoc;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SendMailActivity extends AppCompatActivity {

    EditText editTextEmailSubject;
    EditText editTextEmailContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        editTextEmailSubject = (EditText) findViewById(R.id.editTextEmailSubject);
        editTextEmailContent = (EditText) findViewById(R.id.editTextEmailContent);
    }
    public void onButtonClickSend(View v){
        String emailTo 		= "nguyenwipwa@gmail.com";
        String emailSubject 	= editTextEmailSubject.getText().toString();
        String emailContent 	= editTextEmailContent.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailTo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
//        emailIntent.setType("image/jpeg");
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Chọn phương tiện để gửi:"));
    }

}
