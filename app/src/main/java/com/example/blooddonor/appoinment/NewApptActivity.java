package com.example.blooddonor.appoinment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewApptActivity extends AppCompatActivity {

    private EditText apptDate, apptTime;
    private ImageView btnDatePicker, btnTimePicker;
    private Spinner apptSpinner;
    private Button btnSubmit;
    private int hour, minute;
    private String location;

    private FirebaseAuth auth;
    private FirebaseDatabase firebase;
    private DatabaseReference newAppoinmentRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appt);

        // init firebase
        auth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance();
        newAppoinmentRef = firebase.getReference().child("Appointments");
        userRef = firebase.getReference().child("Users");

        // init component
        apptDate = findViewById(R.id.appDate);
        apptTime = findViewById(R.id.appTime);
        btnDatePicker = findViewById(R.id.imgViewBtnDate);
        btnTimePicker = findViewById(R.id.imgViewBtnTime);
        apptSpinner = findViewById(R.id.apptSpinner);
        btnSubmit = findViewById(R.id.btnSubmitNewAppt);

        // init datepicker
        datePickerDialog();

        // init timepicker
        timePickerDialog();

        // spinner
        apptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = apptSpinner.getSelectedItem().toString();
                //Toast.makeText(NewApptActivity.this, "choose " + location, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // submit new appointment
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validationPass()){
                    String date = apptDate.getText().toString().trim();
                    String time = apptTime.getText().toString().trim();
                    String key = newAppoinmentRef.push().getKey();

                    Appointment appointment = new Appointment(location, date, time, "pending", auth.getUid(), key, false);

                    Map<String, Object> newAppointment = new HashMap<>();
                    newAppointment.put(key, appointment);
                    newAppoinmentRef.updateChildren(newAppointment);

                    userRef.child(auth.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Map<String, Object> insertUser = new HashMap<>();

                                    String fullName = snapshot.child("fullName").getValue(String.class);
                                    String ic = snapshot.child("idNumber").getValue(String.class);
                                    String userID = FirebaseAuth.getInstance().getUid();
                                    boolean x = false;


                                    insertUser.put(key + "/appointmentId/", key.toString());
                                    insertUser.put(key + "/fullName/", fullName);
                                    insertUser.put(key + "/idNumber/", ic);
                                    insertUser.put(key + "/attendance/",false);
                                    insertUser.put(key + "/status/","pending");
                                    insertUser.put(key + "/date/",date);
                                    insertUser.put(key + "/time/",time);
                                    insertUser.put(key + "/location/",location);
                                    insertUser.put(key + "/userID/", userID);
//
                                    newAppoinmentRef.updateChildren(insertUser);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    Toast.makeText(NewApptActivity.this, "New appointment has been set!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewApptActivity.this, appointmentActivity.class));
                    finish();
                }
            }

            private boolean validationPass() {
                if (apptDate.getText() == null) {
                    apptDate.requestFocus();
                    apptDate.setError("Please choose date for appointment.");
                    return false;
                } else if (apptTime.getText() == null) {
                    apptTime.requestFocus();
                    apptTime.setError("Please select time for appointment.");
                    return false;
                } else if (location.equals("Static Donation Centre")) {
                    Toast.makeText(NewApptActivity.this, "Please choose a donation centre.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
    }

    private void timePickerDialog() {
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        apptTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(NewApptActivity.this, onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Select time for appoinment");
                timePickerDialog.show();
            }
        });
    }

    public void datePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(NewApptActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1+=1;
                        String newDate = i2+"-"+i1+"-"+i;
                        apptDate.setText(newDate);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }
}