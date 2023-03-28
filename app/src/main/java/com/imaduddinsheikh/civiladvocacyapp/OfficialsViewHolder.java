package com.imaduddinsheikh.civiladvocacyapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Created by: Imaduddin Sheikh
public class OfficialsViewHolder extends RecyclerView.ViewHolder {
    TextView officialOfficeTitle;

    TextView officialNameParty;

    ImageView officialImage;

    public OfficialsViewHolder(@NonNull View view) {
        super(view);
        this.officialOfficeTitle = view.findViewById(R.id.officeTitleTxtView);
        this.officialNameParty = view.findViewById(R.id.officialNamePartyTxtView);
        this.officialImage = view.findViewById(R.id.officialImgView);
    }
}
