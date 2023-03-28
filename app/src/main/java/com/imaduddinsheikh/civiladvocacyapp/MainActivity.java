package com.imaduddinsheikh.civiladvocacyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
// Created by: Imaduddin Sheikh
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int LOCATION_REQUEST = 111;
    private static String locationString = "Unspecified Location";
    private TextView locationTxtView;
    private RecyclerView officialRecyclerView;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private List<Official> officialList = new ArrayList<>();

    private OfficialsAdapter officialAdapter; // Data to recyclerview adapter

    private LinearLayoutManager linearLayoutManager;
    // Created by: Imaduddin Sheikh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTxtView = findViewById(R.id.oLocationTxtView);
        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        determineLocation();

        officialRecyclerView = findViewById(R.id.officialsRecyclerView);
        officialAdapter = new OfficialsAdapter(this.officialList, this);
        officialRecyclerView.setAdapter(officialAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        officialRecyclerView.setLayoutManager(linearLayoutManager);

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
            locationTxtView.setText(l);
            doDownload(l);
        }
    }
    // Created by: Imaduddin Sheikh
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
    // Created by: Imaduddin Sheikh
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuInfo) {
            Intent intent = new Intent(this, AboutActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        } else if (item.getItemId() == R.id.menuChangeLocations) {
            Toast.makeText(this, "Change Location", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            final EditText et = new EditText(this);
            et.setInputType(InputType.TYPE_CLASS_TEXT);
            et.setGravity(Gravity.CENTER_HORIZONTAL);
            builder1.setView(et);
            builder1.setPositiveButton("OK", (dialog, id1) -> {
                this.officialList.clear();
                officialAdapter.notifyDataSetChanged();
                doDownload(et.getText().toString());
            });
            builder1.setNegativeButton("CANCEL", (dialog, id1) -> {
                dialog.dismiss();
            });
            builder1.setTitle("Enter Address");
            AlertDialog dialog = builder1.create();
            dialog.show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    // Created by: Imaduddin Sheikh
    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
    // Created by: Imaduddin Sheikh
    private void determineLocation() {
        // Check perm - if not then start the  request and return
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some situations this can be null.
                    if (location != null) {
                        locationString = getPlace(location);
                        locationTxtView.setText(locationString);
                        doDownload(locationString);
                    }
                })
                .addOnFailureListener(this, e ->
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show());
    }
    // Created by: Imaduddin Sheikh
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (hasNetworkConnection()) {
            if (requestCode == LOCATION_REQUEST) {
                if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        determineLocation();
                        doDownload(locationString);
                    } else {
                        locationTxtView.setText(R.string.location_permission_denied);
                    }
                }
            }
        } else {
            locationTxtView.setText(R.string.no_internet);
        }
    }
    // Created by: Imaduddin Sheikh
    private String getPlace(Location loc) {

        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s",
                    address.substring(0, address.lastIndexOf(","))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    // Created by: Imaduddin Sheikh
    public void updateData(List<Official> officialList, String locationString) {
        if ((officialList == null) || (!(hasNetworkConnection()))) {
            return;
        }

        this.officialList = officialList;
        MainActivity.locationString = locationString;
        locationTxtView.setText(MainActivity.locationString);
        officialAdapter = new OfficialsAdapter(this.officialList, this);
        officialRecyclerView.setAdapter(officialAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        officialRecyclerView.setLayoutManager(linearLayoutManager);
    }
    // Created by: Imaduddin Sheikh
    @Override
    public void onClick(View v) {
        int pos = officialRecyclerView.getChildLayoutPosition(v);
        Official o = officialList.get(pos);
        if (hasNetworkConnection()) {
            Intent intent = new Intent(this, OfficialActivity.class);
            intent.putExtra("LOCATION", locationTxtView.getText().toString());
            intent.putExtra("OFFICIAL", (Serializable) o);
            activityResultLauncher.launch(intent);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    // Created by: Imaduddin Sheikh
    private void doDownload(String location) {
        OfficialsDownloader.downloadOfficials(this, location);
    }


}