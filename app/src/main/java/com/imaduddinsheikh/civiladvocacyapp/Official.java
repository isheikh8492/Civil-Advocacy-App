package com.imaduddinsheikh.civiladvocacyapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Hashtable;

// Created by: Imaduddin Sheikh
public class Official implements Serializable {
    private final String name;

    private final String office;

    private final String party;

    @Nullable
    private final String address;

    @Nullable
    private final String phone;

    @Nullable
    private final String email;

    @Nullable
    private final String website;

    @Nullable
    private final String photo;

    @Nullable final Hashtable<String, String> socialMediaChannels;

    // Created by: Imaduddin Sheikh
    public Official(String name, String office, String party, String address, String phone, String email, String website, String photo,
                    Hashtable<String, String> socialMediaChannels) {
        this.name = name;
        this.office = office;
        this.party = party;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.photo = photo;
        this.socialMediaChannels = socialMediaChannels;
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
    }

    public String getParty() {
        return party;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getWebsite() {
        return website;
    }

    @Nullable
    public String getPhoto() { return photo; }

    @Nullable
    public Hashtable<String, String> getSocialMediaChannels() {
        return socialMediaChannels;
    }

    @NonNull
    @Override
    public String toString() {
        return "Official{" +
                "name='" + name + '\'' +
                ", office='" + office + '\'' +
                ", party='" + party + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", photo='" + photo + '\'' +
                ", socialMediaChannels=" + (socialMediaChannels != null ? socialMediaChannels.toString() : null) +
                '}';
    }
}
