package com.example.blooddonor.points;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonor.LoadingDialog;
import com.example.blooddonor.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class collPointsActivity extends AppCompatActivity {

    private TextView tvCollectedPoint, tvUncollectedPoint;
    private Button btnCollect;
    private MaterialAlertDialogBuilder builder;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference userRef;

    private LoadingDialog loading;

    private int currentPoint, unCollectedPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coll_points);

        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();
        userRef = firebase.getReference().child("Users").child(auth.getUid());

        // init component
        tvCollectedPoint = findViewById(R.id.tvCurrentPoints);
        tvUncollectedPoint = findViewById(R.id.tvUncollectedPoints);

        btnCollect = findViewById(R.id.btnCollectPoints);
        builder = new MaterialAlertDialogBuilder(this);

        loading = new LoadingDialog(this);
        loading.show();

        // retrieve data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        currentPoint = snapshot.hasChild("point") ? snapshot.child("point").getValue(Integer.class) : 0;
                        tvCollectedPoint.setText(""+currentPoint);

                        unCollectedPoint = snapshot.hasChild("uncollected_point") ? snapshot.child("uncollected_point").getValue(Integer.class) : 0;

                        if (unCollectedPoint == 0) {
                            btnCollect.setVisibility(View.INVISIBLE);
                        }
                        tvUncollectedPoint.setText(""+unCollectedPoint);
                        loading.hide();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // btn listener
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("Point Collection")
                        .setMessage("Do you want to collect all points?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {

                            loading.show();

                            // update point
                            Map<String, Object> updatePoints = new HashMap<>();
                            updatePoints.put("/point/", currentPoint + unCollectedPoint);
                            updatePoints.put("/uncollected_point/", 0);

                            userRef.updateChildren(updatePoints);

                            Toast.makeText(collPointsActivity.this, "Point has been collected!", Toast.LENGTH_SHORT).show();

                            tvCollectedPoint.setText(String.valueOf(unCollectedPoint + currentPoint));
                            tvUncollectedPoint.setText(String.valueOf(0));
                            btnCollect.setVisibility(View.INVISIBLE);

                            loading.hide();

                        })
                        .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();

            }
        });
    }
}