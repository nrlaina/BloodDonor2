package com.example.blooddonor.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonor.R;
import com.example.blooddonor.model.Appointment;
import com.example.blooddonor.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private Button update;
    private TextView emailAddressTitle, fullNameTitle, idNumberTitle, phoneNumberTitle;

    private EditText email, fullName, idNumber, phoneNumber;

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser user;

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

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Users").child(auth.getUid());

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

                user.updateEmail(emel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("success", "email user has been successfully updated!");
                            }
                        });

                Map<String, Object> newUser = new HashMap<>();
                newUser.put("/email/", emel);
                newUser.put("/fullName/", name);
                newUser.put("/idNumber/", id);
                newUser.put("/phoneNumber/", phone);

                reference.updateChildren(newUser);

                Toast.makeText(UpdateActivity.this, "Successfully update!", Toast.LENGTH_LONG).show();

            }
        });

    }
}