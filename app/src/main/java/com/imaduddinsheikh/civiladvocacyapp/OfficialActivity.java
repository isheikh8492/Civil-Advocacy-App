package com.imaduddinsheikh.civiladvocacyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Hashtable;

public class OfficialActivity extends AppCompatActivity {
    private static final String TAG = "OfficialActivity";
    private TextView oLocationTxtView;
    private TextView oNameTxtView;
    private TextView oOfficeTxtView;
    private TextView oPartyTxtView;
    private TextView oAddressTxtView;
    private TextView addressTxtView;
    private TextView oPhoneTxtView;
    private TextView phoneTxtView;
    private TextView oEmailTxtView;
    private TextView emailTxtView;
    private TextView oWebsiteTxtView;
    private TextView websiteTxtView;

    private ConstraintLayout oConstraintLayout;

    private ImageView oImgView;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        oConstraintLayout = findViewById(R.id.oConstraintLayout);
        oLocationTxtView = findViewById(R.id.oLocationTxtView);
        oNameTxtView = findViewById(R.id.oOfficialNameTxtView);
        oOfficeTxtView = findViewById(R.id.oOfficialOfficeTxtView);
        oPartyTxtView = findViewById(R.id.oOfficialPartyTxtView);
        oAddressTxtView = findViewById(R.id.oAddressTxtView);
        addressTxtView = findViewById(R.id.addressTxtView);
        oPhoneTxtView = findViewById(R.id.oPhoneTxtView);
        phoneTxtView = findViewById(R.id.phoneTxtView);
        oEmailTxtView = findViewById(R.id.oEmailTxtView);
        emailTxtView = findViewById(R.id.EmailTxtView);
        oWebsiteTxtView = findViewById(R.id.oWebsiteTxtView);
        websiteTxtView = findViewById(R.id.websiteTxtView);
        oImgView = findViewById(R.id.oOfficialImgView);

        Intent intent = getIntent();
        if (intent.hasExtra("LOCATION")) {
            String l = (String) getIntent().getSerializableExtra("LOCATION");
            oLocationTxtView.setText(l);
        }
        if (intent.hasExtra("OFFICIAL")) {
            Official o = (Official) getIntent().getSerializableExtra("OFFICIAL");
            setActivityTextViews(o);
        }

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult);
    }

    public void handleResult(ActivityResult result) {

        if (result == null || result.getData() == null) {
            Log.d(TAG, "handleResult: NULL ActivityResult received");
        }

        Intent data = result.getData();
        if (result.getResultCode() == Activity.RESULT_OK) {
            String l = data.getStringExtra("LOCATION");
            oLocationTxtView.setText(l);
            Official o = (Official) data.getSerializableExtra("OFFICIAL");
            setActivityTextViews(o);
        }
    }

    private void setActivityTextViews(Official official) {
        oNameTxtView.setText(official.getName());
        oOfficeTxtView.setText(official.getOffice());
        oPartyTxtView.setText(official.getParty());
        Hashtable<String, Integer> colorSet = new Hashtable<>();
        colorSet.put("Republican Party", R.color.red_background);
        colorSet.put("Democratic Party", R.color.blue_background);
        colorSet.put("Nonpartisan", R.color.black_background);
        oConstraintLayout.setBackgroundColor(getResources()
                .getColor(colorSet.get(official.getParty())));
        if (official.getAddress() != null) {
            oAddressTxtView.setText(official.getAddress());
        } else {
            oAddressTxtView.setVisibility(View.GONE);
            addressTxtView.setVisibility(View.GONE);
        }
        if (official.getPhone() != null) {
            oPhoneTxtView.setText(official.getPhone());
        } else {
            oPhoneTxtView.setVisibility(View.GONE);
            phoneTxtView.setVisibility(View.GONE);
        }
        if (official.getEmail() != null) {
            oEmailTxtView.setText(official.getEmail());
        } else {
            oEmailTxtView.setVisibility(View.GONE);
            emailTxtView.setVisibility(View.GONE);
        }
        if (official.getWebsite() != null) {
            oWebsiteTxtView.setText(official.getWebsite());
        } else {
            oWebsiteTxtView.setVisibility(View.GONE);
            websiteTxtView.setVisibility(View.GONE);
        }
        if (official.getPhoto() != null) {
            Picasso.get().load(official.getPhoto()).error(R.drawable.brokenimage).into(oImgView);
        } else {
            oImgView.setImageResource(R.drawable.missing);
        }
    }
}