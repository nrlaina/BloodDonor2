package com.example.blooddonor.appoinment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.blooddonor.R;
import com.example.blooddonor.adapter.AppoinmentAdapter;
import com.example.blooddonor.model.Appointment;
import com.example.blooddonor.user.LoginActivity;
import com.example.blooddonor.user.ProfileActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myApptActivity extends AppCompatActivity implements AppoinmentAdapter.OnDeleteAppointmentListener {

    private RecyclerView rcAppointment;
    private MaterialAlertDialogBuilder builder;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference appoinmentRef;
    private AppoinmentAdapter appoinmentAdapter;

    private ArrayList<Appointment> appoinmentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appt);


        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();
        appoinmentRef = firebase.getReference().child("Appointments");

        // retrieve appointment
        appoinmentRef.orderByChild("userID")
                .equalTo(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Log.e("data wujud", "onDataChange: ");
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Appointment appointment = dataSnapshot.getValue(Appointment.class);
                                appoinmentArrayList.add(appointment);

                            }

                        }
                        else{
                            Log.e("data xde", "onDataChange: " );
                        }

                        appoinmentAdapter.notifyDataSetChanged();
                        int x = appoinmentArrayList.size();
                        Log.e(String.valueOf(x), "checking arraysize: " );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // init component
        rcAppointment = findViewById(R.id.rcAppointment);
        builder = new MaterialAlertDialogBuilder(this);

        // recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcAppointment.setLayoutManager(linearLayoutManager);
        appoinmentAdapter = new AppoinmentAdapter(appoinmentArrayList, this);
        rcAppointment.setAdapter(appoinmentAdapter);


    }

    @Override
    public void onAppointmentDelete(int position, Appointment appointment) {

        builder.setTitle("Delete Appointment")
                .setMessage("Are you sure want to delete this appointment?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialogInterface, i) -> {

                    // delete appointment
                    appoinmentRef.orderByChild("appointmentId")
                            .equalTo(appointment.getAppointmentId())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            dataSnapshot.getRef().removeValue();
                                        }

                                        Toast.makeText(myApptActivity.this, "Appointment has been deleted!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(myApptActivity.this, appointmentActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                })
                .setNegativeButton("Nope", (dialogInterface, i) -> dialogInterface.cancel())
                .show();
    }
}