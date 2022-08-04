package com.example.blooddonor.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.blooddonor.R;
import com.example.blooddonor.appoinment.appointmentActivity;
import com.example.blooddonor.points.PointsActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private MaterialAlertDialogBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button profile = findViewById(R.id.profile);
        profile.setOnClickListener(this);
        Button kdd = findViewById(R.id.kdd);
        kdd.setOnClickListener(this);
        Button appointment = findViewById(R.id.appointment);
        appointment.setOnClickListener(this);
        Button points = findViewById(R.id.points);
        points.setOnClickListener(this);
        Button screening = findViewById(R.id.screening);
        screening.setOnClickListener(this);
        Button logout = findViewById(R.id.signOut);

        builder = new MaterialAlertDialogBuilder(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setTitle("Logout")
                        .setMessage("Are you sure want to logout?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        })
                        .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NewUser.class));
            }
        });

        kdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, kddActivity.class));
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, appointmentActivity.class));
            }
        });

        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PointsActivity.class));
            }
        });

        screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EligibilityActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile:
                startActivity(new Intent(this, NewUser.class));
                break;
        }
    }
}