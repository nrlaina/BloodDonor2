package com.example.blooddonor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Donor implements Parcelable {
    private String fullname;
    private String idNumber;
    private String location;
    private String date;
    private String time;
    private String userId;
    private String status;
    private String appointmentId;

    public Donor() {
    }

    public Donor(String fullname, String idNumber) {
        this.fullname = fullname;
        this.idNumber = idNumber;
    }

    public Donor(String location, String date, String time, String userId, String status) {
        this.location = location;
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.status = status;
    }

    public Donor(String name, String userIc, String location, String date, String time, String userID, String status) {
        this.fullname = name;
        this.idNumber = userIc;
        this.location = location;
        this.date = date;
        this.time = time;
        this.userId = userID;
        this.status = status;
    }

    protected Donor(Parcel in) {
        fullname = in.readString();
        idNumber = in.readString();
        location = in.readString();
        date = in.readString();
        time = in.readString();
        userId = in.readString();
        status = in.readString();
        appointmentId = in.readString();
    }

    public static final Creator<Donor> CREATOR = new Creator<Donor>() {
        @Override
        public Donor createFromParcel(Parcel in) {
            return new Donor(in);
        }

        @Override
        public Donor[] newArray(int size) {
            return new Donor[size];
        }
    };

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String name) {
        this.fullname = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullname);
        parcel.writeString(idNumber);
        parcel.writeString(location);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(userId);
        parcel.writeString(status);
        parcel.writeString(appointmentId);
    }
}
