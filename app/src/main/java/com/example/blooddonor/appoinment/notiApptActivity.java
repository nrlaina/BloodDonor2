package com.example.blooddonor.appoinment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.blooddonor.R;
import com.example.blooddonor.adapter.AppointmentNotiAdapter;
import com.example.blooddonor.model.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notiApptActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference appointmentRef;

    private AppointmentNotiAdapter appointmentNotiAdapter;
    private ArrayList<Appointment> appointments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_appt);

        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();
        appointmentRef = firebase.getReference().child("Appointments");

        // init component
        recyclerView = findViewById(R.id.rcNotiAppointment);

        // retrieve user data
        appointmentRef
                .orderByChild("userID")
                .equalTo(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String status = dataSnapshot.child("status").getValue(String.class);
                            //Log.d("found", ", " + status);
                            if (!status.equals("pending")) {
                                Appointment appointment = dataSnapshot.getValue(Appointment.class);
                                appointments.add(appointment);
                            }
                            appointmentNotiAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // setup recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        appointmentNotiAdapter = new AppointmentNotiAdapter(appointments);
        recyclerView.setAdapter(appointmentNotiAdapter);

    }
}