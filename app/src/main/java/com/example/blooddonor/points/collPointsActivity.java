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

    private TextView tvCollectedPoint, tvEntitlement1;
    private Button btnCollect;
    private MaterialAlertDialogBuilder builder;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference userRef;

    private LoadingDialog loading;

    private int currentPoint;

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
        tvEntitlement1 = findViewById(R.id.tvEntitlement1);

        //only staff will be able to give points
        //btnCollect = findViewById(R.id.btnCollectPoints);
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

                       if (currentPoint < 1) {
                           tvEntitlement1.setText("You did not make any donation yet");
                       }
                        else if(currentPoint == 1){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months");
                        }
                        else if(currentPoint >1 && currentPoint < 6){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months");
                        }
                        else if(currentPoint > 5 && currentPoint < 11){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months " +
                                    "\n\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period");
                        }
                        else if(currentPoint > 10 && currentPoint < 16){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period" +
                                    "\n 4. Free 2 year outpatient treatment and medical treatment & second class wards for a 1 year period");
                        }
                        else if(currentPoint > 15 && currentPoint < 21){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period" +
                                    "\n 4. Free 2 year outpatient treatment and medical treatment & second class wards for a 1 year period" +
                                    "\n 5. Free outpatient treatment and medical treatment & second class wards for a 2 year period");
                        }
                        else if(currentPoint > 20 && currentPoint < 31){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period" +
                                    "\n 4. Free 2 year outpatient treatment and medical treatment & second class wards for a 1 year period" +
                                    "\n 5. Free outpatient treatment and medical treatment & second class wards for a 2 year period" +
                                    "\n 6. Free outpatient treatment and medical treatment & second class wards for a 3 year period");
                        }
                        else if(currentPoint > 30 && currentPoint < 41){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period" +
                                    "\n 4. Free 2 year outpatient treatment and medical treatment & second class wards for a 1 year period" +
                                    "\n 5. Free outpatient treatment and medical treatment & second class wards for a 2 year period" +
                                    "\n 6. Free outpatient treatment and medical treatment & second class wards for a 3 year period" +
                                    "\n 7. Free outpatient treatment and medical treatment & first class wards for a 4 year period");
                        }
                        else if(currentPoint > 40 && currentPoint < 51){
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period" +
                                    "\n 4. Free 2 year outpatient treatment and medical treatment & second class wards for a 1 year period" +
                                    "\n 5. Free outpatient treatment and medical treatment & second class wards for a 2 year period" +
                                    "\n 6. Free outpatient treatment and medical treatment & second class wards for a 3 year period" +
                                    "\n 7. Free outpatient treatment and medical treatment & first class wards for a 4 year period" +
                                    "\n 8. Free outpatient treatment and medical treatment & first class wards for a 6 year period");
                        }
                        else{
                            tvEntitlement1.setText("1. Free outpatient treatment & medical treatment (excluding X-ray & surgical charges) & second class wards for a period of 4 months" +
                                    "\n 2. Free outpatient treatment and medical treatment & second class wards for a period of 4 months" +
                                    "\n 3. Free 1 year outpatient treatment & second class medical treatment for a 6 months period" +
                                    "\n 4. Free 2 year outpatient treatment and medical treatment & second class wards for a 1 year period" +
                                    "\n 5. Free outpatient treatment and medical treatment & second class wards for a 2 year period" +
                                    "\n 6. Free outpatient treatment and medical treatment & second class wards for a 3 year period" +
                                    "\n 7. Free outpatient treatment and medical treatment & first class wards for a 4 year period" +
                                    "\n 8. Free outpatient treatment and medical treatment & first class wards for a 6 year period" +
                                    "\n 9. Free outpatient treatment and first-class medical treatment and wards for 10 years and second-class wards of life after 10 years in first-class wards");
                        }


                        //removed as requested for uncollected points
                        //unCollectedPoint = snapshot.hasChild("uncollected_point") ? snapshot.child("uncollected_point").getValue(Integer.class) : 0;

//                        if (unCollectedPoint == 0) {
//                            btnCollect.setVisibility(View.INVISIBLE);
//                        }
//                        tvUncollectedPoint.setText(""+unCollectedPoint);
                        loading.hide();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

        //removed to make sure only staff can give points to user if the user attend the blood donation
        // btn listener
//        btnCollect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                builder.setTitle("Point Collection")
//                        .setMessage("Do you want to collect all points?")
//                        .setCancelable(true)
//                        .setPositiveButton("Yes", (dialogInterface, i) -> {
//
//                            loading.show();
//
//                            // update point
//                            Map<String, Object> updatePoints = new HashMap<>();
//                            updatePoints.put("/point/", currentPoint + unCollectedPoint);
//                            updatePoints.put("/uncollected_point/", 0);
//
//                            userRef.updateChildren(updatePoints);
//
//                            Toast.makeText(collPointsActivity.this, "Point has been collected!", Toast.LENGTH_SHORT).show();
//
//                            tvCollectedPoint.setText(String.valueOf(unCollectedPoint + currentPoint));
//                            tvUncollectedPoint.setText(String.valueOf(0));
//                            btnCollect.setVisibility(View.INVISIBLE);
//
//                            loading.hide();
//
//                        })
//                        .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
//                        .show();
//
//            }
//        });
    }
}