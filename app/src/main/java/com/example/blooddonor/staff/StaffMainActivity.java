package com.example.blooddonor.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.blooddonor.user.LoginActivity;
import com.example.blooddonor.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StaffMainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;

    private MaterialAlertDialogBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();

        // init component
        Button btnListDonor = findViewById(R.id.btnListDonor);
        Button btnLogout = findViewById(R.id.btnLogout);

        builder = new MaterialAlertDialogBuilder(this);

        // btn listener
        btnListDonor.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnListDonor:
                startActivity(new Intent(StaffMainActivity.this, DonorListActivity.class));
                break;

            case R.id.btnLogout:
                builder.setTitle("Logout")
                        .setMessage("Are you sure want to logout?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            auth.signOut();
                            startActivity(new Intent(StaffMainActivity.this, LoginActivity.class));
                            finish();
                        })
                        .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
                break;
        }
    }
}