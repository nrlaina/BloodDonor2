package com.example.blooddonor.points;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Appointment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.blooddonor.adapter.rewardAppointmentAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class rewardAppointment extends AppCompatActivity {

    //testing date4;
    Calendar date = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("d-M-yyyy");
    String currentDate = df.format(date.getTime()).trim();
    //testing date2
    //String date = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault()).format(new Date());
    RecyclerView recyclerView;
    CardView card;
    Button btnApp;
    DatabaseReference db;
    rewardAppointmentAdapter myAdapter;
    ArrayList<Appointment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_appointment);

        recyclerView = findViewById(R.id.apptList);
        db = FirebaseDatabase.getInstance().getReference("Appointments");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new rewardAppointmentAdapter(this,list);
        recyclerView.setAdapter(myAdapter);


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Appointment appt = dataSnapshot.getValue(Appointment.class);
                    Log.e(currentDate, "currentDate: " );
                    Log.e(appt.getDate(), "evry date: " );

                    //check if the appointment is approved
                    if(appt.getStatus().equals("approved")){
                        Log.e("dah masuk first if", "testing");
                        //check if the  appointment is the same as current date
                        if(currentDate.equals(appt.getDate())){
                            Log.e("dah masuk second if", "testing");
                            //check if the appointment is already rewarded
                            if(appt.getAttendance() == false){
                                appt.setAppointmentId(dataSnapshot.getKey());
                                list.add(appt);
                                Log.e("masuk third if", "testing");
                                Log.e(appt.getUserId(), "onDataChange: ");
                                Log.e(list.toString(), "onDataChange: ");
                            }
                        }
                    }


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}