package com.imaduddinsheikh.civiladvocacyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
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
    private ImageView facebookImgView;
    private ImageView youtubeImgView;
    private ImageView twitterImgView;

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
        facebookImgView = findViewById(R.id.facebookImgView);
        youtubeImgView = findViewById(R.id.youtubeImgView);
        twitterImgView = findViewById(R.id.twitterImgView);

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
            oAddressTxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickOAddress(v, official.getAddress());
                }
            });
            oAddressTxtView.setPaintFlags(oAddressTxtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            oAddressTxtView.setVisibility(View.GONE);
            addressTxtView.setVisibility(View.GONE);
        }
        if (official.getPhone() != null) {
            oPhoneTxtView.setText(official.getPhone());
            oPhoneTxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickOPhone(v, official.getPhone());
                }
            });
            oPhoneTxtView.setPaintFlags(oPhoneTxtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            oPhoneTxtView.setVisibility(View.GONE);
            phoneTxtView.setVisibility(View.GONE);
        }
        if (official.getEmail() != null) {
            oEmailTxtView.setText(official.getEmail());
            oEmailTxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickOEmail(v, official.getEmail());
                }
            });
            oEmailTxtView.setPaintFlags(oEmailTxtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            oEmailTxtView.setVisibility(View.GONE);
            emailTxtView.setVisibility(View.GONE);
        }
        if (official.getWebsite() != null) {
            oWebsiteTxtView.setText(official.getWebsite());
            oWebsiteTxtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickOWebsite(v, official.getWebsite());
                }
            });
            oWebsiteTxtView.setPaintFlags(oWebsiteTxtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            oWebsiteTxtView.setVisibility(View.GONE);
            websiteTxtView.setVisibility(View.GONE);
        }
        if (official.getPhoto() != null) {
            Picasso.get().load(official.getPhoto()).error(R.drawable.brokenimage).into(oImgView);
        } else {
            oImgView.setImageResource(R.drawable.missing);
        }
        if (!(official.socialMediaChannels.isEmpty())) {
            if (official.socialMediaChannels.containsKey("Facebook")) {
                facebookImgView.setVisibility(View.VISIBLE);
                facebookImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickFacebook(v, official.getSocialMediaChannels().get("Facebook"));
                    }
                });
            } else {
                facebookImgView.setVisibility(View.INVISIBLE);
            }
            if (official.socialMediaChannels.containsKey("Twitter")) {
                twitterImgView.setVisibility(View.VISIBLE);
                twitterImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickTwitter(v, official.getSocialMediaChannels().get("Twitter"));
                    }
                });
            } else {
                twitterImgView.setVisibility(View.INVISIBLE);
            }
            if (official.socialMediaChannels.containsKey("YouTube")) {
                youtubeImgView.setVisibility(View.VISIBLE);
                youtubeImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickYouTube(v, official.socialMediaChannels.get("YouTube"));
                    }
                });
            } else {
                youtubeImgView.setVisibility(View.INVISIBLE);
            }
        } else {
            facebookImgView.setVisibility(View.INVISIBLE);
            twitterImgView.setVisibility(View.INVISIBLE);
            youtubeImgView.setVisibility(View.INVISIBLE);
        }
    }

    private void clickOWebsite(View v, String website) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(website));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void clickOEmail(View v, String email) {
        String[] addresses = new String[]{email};
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        // Check if there is an app that can handle mailto intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles SENDTO (mailto) intents");
        }
    }

    private void clickOPhone(View v, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_DIAL (tel) intents");
        }
    }

    private void clickOAddress(View v, String address) {
        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        // Check if there is an app that can handle geo intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (geo) intents");
        }
    }

    public void clickYouTube(View v, String youTubeUsername) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + youTubeUsername));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + youTubeUsername)));

        }
    }


    private void clickTwitter(View v, String twitterName) {
        Intent intent;
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + twitterName));
        }
        startActivity(intent);
    }

    public void clickFacebook(View v, String facebookUrl) {
        // You need the FB user's id for the url
        String FACEBOOK_URL = "https://www.facebook.com/" + facebookUrl;

        Intent intent;

        // Check if FB is installed, if not we'll use the browser
        if (isPackageInstalled("com.facebook.katana")) {
            String urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }

        // Check if there is an app that can handle fb or https intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
        }
    }

    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}