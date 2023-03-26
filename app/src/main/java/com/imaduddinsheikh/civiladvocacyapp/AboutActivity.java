package com.imaduddinsheikh.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {
    private TextView ApiNameTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ApiNameTxtView = findViewById(R.id.apiNameTxtView);
        ApiNameTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String demUrl = "https://developers.google.com/civicinformation/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(demUrl));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        ApiNameTxtView.setPaintFlags(ApiNameTxtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}