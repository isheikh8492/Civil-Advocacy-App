package com.imaduddinsheikh.civiladvocacyapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;

// Created by: Imaduddin Sheikh
public class OfficialsAdapter extends RecyclerView.Adapter<OfficialsViewHolder> {
    private static final String TAG = "OfficialsAdapter";

    private final List<Official> officialsList;

    private final MainActivity mainActivity;

    public OfficialsAdapter(List<Official> officialsList, MainActivity mainActivity) {
        this.officialsList = officialsList;
        this.mainActivity = mainActivity;
    }

    // Created by: Imaduddin Sheikh
    @NonNull
    @Override
    public OfficialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW OfficialsViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.official_list_row, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnClickListener(mainActivity);

        return new OfficialsViewHolder(itemView);
    }

    // Created by: Imaduddin Sheikh
    @Override
    public void onBindViewHolder(@NonNull OfficialsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Official " + position);

        Official official = officialsList.get(position);
        holder.officialOfficeTitle.setText(official.getOffice());
        String officialNameParty = official.getName() + " (" + official.getParty() + ")";
        holder.officialNameParty.setText(officialNameParty);

        if (official.getPhoto() != null) {
            Picasso.get().load(official.getPhoto()).error(R.drawable.brokenimage).into(holder.officialImage);
        } else {
            holder.officialImage.setImageResource(R.drawable.missing);
        }
    }

    // Created by: Imaduddin Sheikh
    @Override
    public int getItemCount() {
        return this.officialsList.size();
    }
}
