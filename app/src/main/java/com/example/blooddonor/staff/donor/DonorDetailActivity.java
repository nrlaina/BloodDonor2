package com.example.blooddonor.staff.donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Donor;
import com.example.blooddonor.staff.DonorListActivity;
import com.example.blooddonor.staff.StaffMainActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DonorDetailActivity extends AppCompatActivity {

    private MaterialAlertDialogBuilder builder;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference appointmentRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_detail);

        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();
        appointmentRef = firebase.getReference().child("Appointments");
        userRef = firebase.getReference().child("Users");

        // get donor data from list
        Bundle bundle = getIntent().getExtras();
        Donor donor = bundle.getParcelable("donor");

        // Log.d("donor here", " " + donor.getIdNumber() + ", " + donor.getFullName());

        // init component
        TextView tvFullName = findViewById(R.id.tvDonorFullName);
        TextView tvIcNumber = findViewById(R.id.tvDonorICNumber);
        TextView tvLocation = findViewById(R.id.tvDonorLocation);
        TextView tvTime = findViewById(R.id.tvDonorTime);
        TextView tvDate = findViewById(R.id.tvDonorDate);

        Button btnApprove = findViewById(R.id.btnApproveDonor);
        Button btnDisapprove = findViewById(R.id.btnDisapproveDonor);

        builder = new MaterialAlertDialogBuilder(this);

        // set data
        tvFullName.setText(donor.getFullName());
        tvIcNumber.setText(getICNumber(donor.getIdNumber()));
        tvLocation.setText(donor.getLocation());
        tvTime.setText(getFullTime(donor.getTime()));
        tvDate.setText(getFullDate(donor.getDate()));

        // btn listener
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Donor's approval")
                        .setMessage("Are you sure want to approve the donor?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {

                            // update donor appointment status
                            appointmentRef.orderByChild("appointmentId")
                                    .equalTo(donor.getAppointmentId())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Map<String, Object> updateStatus = new HashMap<>();
                                            updateStatus.put(donor.getAppointmentId() + "/status/", "approved");

                                            appointmentRef.updateChildren(updateStatus);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                            // update uncollected points
                            userRef.child(donor.getUserId())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            int point = snapshot.hasChild("uncollected_point") ? snapshot.child("uncollected_point").getValue(Integer.class) + 1 : 1;

                                            Map<String, Object> updateUncollectedPoint = new HashMap<>();
                                            updateUncollectedPoint.put(donor.getUserId() + "/uncollected_point/", point);

                                            userRef.updateChildren(updateUncollectedPoint);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                            Toast.makeText(DonorDetailActivity.this, "Donor has been approved!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DonorDetailActivity.this, StaffMainActivity.class));
                            finish();
                        })
                        .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
            }
        });

        btnDisapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Donor's disapproval")
                        .setMessage("Are you sure want to disapprove the donor?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {

                            // update donor appointment status
                            appointmentRef.orderByChild("appointmentId")
                                    .equalTo(donor.getAppointmentId())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Map<String, Object> updateStatus = new HashMap<>();
                                            updateStatus.put(donor.getAppointmentId() + "/status/", "disapproved");

                                            appointmentRef.updateChildren(updateStatus);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                            Toast.makeText(DonorDetailActivity.this, "Donor has been disapproved!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DonorDetailActivity.this, StaffMainActivity.class));
                            finish();

                        })
                        .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
            }
        });
    }

    private String getFullDate(String date) {
        String result = "";
        String[] dates = date.split("-");
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        result = dates[0] + " " + monthNames[Integer.parseInt(dates[1]) - 1] + " " + dates[2];

        return result;
    }

    private String getFullTime(String time) {
        String result = "";
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);

        if (hour > 12 && hour <= 23) {
            result = times[0] + ":" + times[1] + " PM";
        } else {
            result = times[0] + ":" + times[1] + " AM";
        }

        return result;
    }

    private String getICNumber(String idNumber) {
        String result = "";
        String front, mid, end;

        front = idNumber.substring(0, 6);
        mid = idNumber.substring(6, 8);
        end = idNumber.substring(8);

        result = front + "-" + mid + "-" + end;

        return result;
    }
}