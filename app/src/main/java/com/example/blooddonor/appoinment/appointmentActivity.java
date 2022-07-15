package com.example.blooddonor.appoinment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.blooddonor.R;
import com.example.blooddonor.user.ProfileActivity;

public class appointmentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Button newAppt = findViewById(R.id.newAppt);
        newAppt.setOnClickListener(this);
        Button myAppt = findViewById(R.id.myAppt);
        myAppt.setOnClickListener(this);
        Button notiAppt = findViewById(R.id.notiAppt);
        notiAppt.setOnClickListener(this);

        TextView bannerAppointment = findViewById(R.id.bannerAppointment);
        bannerAppointment.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newAppt:
                startActivity(new Intent(this, NewApptActivity.class));
                break;
            case R.id.myAppt:
                startActivity(new Intent(this, myApptActivity.class));
                break;
            case R.id.notiAppt:
                startActivity(new Intent(this, notiApptActivity.class));
                break;
            case R.id.bannerAppointment:
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
                break;
        }
    }

}