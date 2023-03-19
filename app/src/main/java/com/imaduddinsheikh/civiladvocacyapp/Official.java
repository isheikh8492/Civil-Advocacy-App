package com.imaduddinsheikh.civiladvocacyapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Official {
    private final String name;

    private final String office;

    private final String party;

    private final String address;

    private final String phone;

    private final String email;

    private final String website;

    public Official(String name, String office, String party, String address, String phone, String email, String website) {
        this.name = name;
        this.office = office;
        this.party = party;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
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

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
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
                '}';
    }
}
