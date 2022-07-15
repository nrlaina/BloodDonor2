package com.example.blooddonor.model;

public class Appointment {

    public String location;
    public String date;
    public String time;
    public String status;
    public String userId;
    public String appointmentId;

    public Appointment() {
    }

    public Appointment(String location, String date, String time, String status, String userId, String appointmentId) {
        this.location = location;
        this.date = date;
        this.time = time;
        this.status = status;
        this.userId = userId;
        this.appointmentId = appointmentId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
