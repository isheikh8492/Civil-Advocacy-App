package com.imaduddinsheikh.civiladvocacyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

// Created by: Imaduddin Sheikh
public class PhotoDetailActivity extends AppCompatActivity {
    private static final String TAG = "PhotoDetailActivity";
    private TextView pLocationTxtView;
    private TextView pOfficialNameTxtView;
    private TextView pOfficialOfficeTxtView;
    private ImageView pOfficialImgView;
    private ImageView pOfficialPartyLogoImgView;
    private ConstraintLayout pConstraintLayout;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    // Created by: Imaduddin Sheikh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        pLocationTxtView = findViewById(R.id.pLocationTxtView);
        pOfficialNameTxtView = findViewById(R.id.pOfficialNameTxtView);
        pOfficialOfficeTxtView = findViewById(R.id.pOfficialOfficeTxtView);
        pOfficialImgView = findViewById(R.id.pOfficialImgView);
        pOfficialPartyLogoImgView = findViewById(R.id.pPartyLogoImgView);
        pConstraintLayout = findViewById(R.id.pConstraintLayout);

        Intent intent = getIntent();
        if (intent.hasExtra("LOCATION")) {
            String l = (String) getIntent().getSerializableExtra("LOCATION");
            pLocationTxtView.setText(l);
        }
        if (intent.hasExtra("OFFICIAL")) {
            Official o = (Official) getIntent().getSerializableExtra("OFFICIAL");
            setActivityViews(o);
        }

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult);
    }

    // Created by: Imaduddin Sheikh
    public void handleResult(ActivityResult result) {

        if (result == null || result.getData() == null) {
            Log.d(TAG, "handleResult: NULL ActivityResult received");
        }

        Intent data = result.getData();
        if (result.getResultCode() == Activity.RESULT_OK) {
            String l = data.getStringExtra("LOCATION");
            pLocationTxtView.setText(l);
            Official o = (Official) data.getSerializableExtra("OFFICIAL");
            setActivityViews(o);
        }
    }

    // Created by: Imaduddin Sheikh
    private void setActivityViews(Official official) {
        pOfficialNameTxtView.setText(official.getName());
        pOfficialOfficeTxtView.setText(official.getOffice());
        Picasso.get().load(official.getPhoto()).error(R.drawable.brokenimage).into(pOfficialImgView);
        switch (official.getParty()) {
            case "Republican Party":
                pConstraintLayout.setBackgroundColor(getResources()
                        .getColor(R.color.red_background));
                pOfficialPartyLogoImgView.setImageResource(R.drawable.rep_logo);
                pOfficialPartyLogoImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String repUrl = "https://www.gop.com";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(repUrl));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                break;
            case "Democratic Party":
                pConstraintLayout.setBackgroundColor(getResources()
                        .getColor(R.color.blue_background));
                pOfficialPartyLogoImgView.setImageResource(R.drawable.dem_logo);
                pOfficialPartyLogoImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String demUrl = "https://democrats.org/";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(demUrl));
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });
                break;
            case "Nonpartisan":
                pConstraintLayout.setBackgroundColor(getResources()
                        .getColor(R.color.black_background));
                pOfficialPartyLogoImgView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}