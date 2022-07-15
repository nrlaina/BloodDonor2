package com.example.blooddonor.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private Button update;
    private TextView emailAddressTitle, fullNameTitle, idNumberTitle, phoneNumberTitle;

    private EditText email, fullName, idNumber, phoneNumber;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        update = findViewById(R.id.update);
        emailAddressTitle = findViewById(R.id.emailAddressTitle);
        fullNameTitle = findViewById(R.id.fullNameTitle);
        idNumberTitle = findViewById(R.id.idNumberTitle);
        phoneNumberTitle = findViewById(R.id.phoneNumberTitle);
        email = findViewById(R.id.emailAddress);
        fullName = findViewById(R.id.fullName);
        idNumber = findViewById(R.id.idNumber);
        phoneNumber = findViewById(R.id.phoneNumber);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Users");

        reference.child("fullName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    fullName.setText(snapshot.getValue(String.class));
                } else {
                    fullName.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
          });

        reference.child("idNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    idNumber.setText(snapshot.getValue(String.class));
                } else {
                    idNumber.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("phoneNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumber.setText(snapshot.getValue(String.class));
                } else {
                    phoneNumber.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("email").addValueEventListener(new ValueEventListener() {
           @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    email.setText(snapshot.getValue(String.class));
                } else {
                   email.setText("");
                }
          }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name = fullName.getText().toString();
                 String id = idNumber.getText().toString();
                 String phone = phoneNumber.getText().toString();
                 String emel = email.getText().toString();

                 reference.child("fullName").setValue(name);
                reference.child("idNumber").setValue(id);
                reference.child("phoneNumber").setValue(phone);
                reference.child("email").setValue(emel);

                Toast.makeText(UpdateActivity.this, "Successfully register", Toast.LENGTH_LONG).show();

            }
        });

    }
}