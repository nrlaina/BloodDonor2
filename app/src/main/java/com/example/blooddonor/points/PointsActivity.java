package com.example.blooddonor.points;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.blooddonor.R;

public class PointsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        // init components
        Button btnCollectedPoints = findViewById(R.id.btnMyPoints);
        //Button btnRedeemPoints = findViewById(R.id.btnRedeem); removed

        // btn listener
        btnCollectedPoints.setOnClickListener(this);
        //btnRedeemPoints.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMyPoints:
                startActivity(new Intent(PointsActivity.this, collPointsActivity.class));
                break;
            //case R.id.btnRedeem:
                //startActivity(new Intent(PointsActivity.this, redeemActivity.class));
                //break;
        }
    }
}