package com.example.blooddonor.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonor.R;
import com.example.blooddonor.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewUser extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private Button update;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        update = (Button) findViewById(R.id.update);

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView idNumberTextView = (TextView) findViewById(R.id.idNumber);
        final TextView phoneNumberTextView = (TextView) findViewById(R.id.phoneNumber);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null){
                    String fullName = userProfile.fullName;
                    String idNumber = userProfile.idNumber;
                    String phoneNumber = userProfile.phoneNumber;
                    String email = userProfile.email;

                    fullNameTextView.setText(fullName);
                    idNumberTextView.setText(idNumber);
                    phoneNumberTextView.setText(phoneNumber);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NewUser.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewUser.this, UpdateActivity.class));
            }
        });

    }
    }

    //rivate void isPasswordChanged() {
        //if (user.equals(userID.getEditText().getText().toString())){
            



    //private boolean isNameChanged() {

