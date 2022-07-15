package com.example.blooddonor.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonor.LoadingDialog;
import com.example.blooddonor.R;
import com.example.blooddonor.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextIdNumber;
    private EditText editTextPhoneNumber;
    private LoadingDialog loading;

    private FirebaseAuth mAuth;

    public RegisterUser() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextIdNumber = (EditText) findViewById(R.id.idNumber);
        editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        loading = new LoadingDialog(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String idNumber = editTextIdNumber.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }

        if (idNumber.isEmpty()) {
            editTextIdNumber.setError("IC number is required");
            editTextIdNumber.requestFocus();
            return;
        }

        if (!(idNumber.length() == 12)) {
            editTextIdNumber.setError("Please insert proper IC number");
            editTextIdNumber.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("PLease insert valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Min. password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

       loading.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //private String idNumber;

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(fullName, idNumber, phoneNumber, email);
                            user.setRole("user");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterUser.this
                                                        , "User has been registered successfully"
                                                        , Toast.LENGTH_LONG).show();
                                               loading.hide();

                                                startActivity(new Intent(RegisterUser.this, LoginActivity.class));

                                                //redirect to login page
                                            } else {
                                                Toast.makeText(RegisterUser.this
                                                        , "Failed to register",
                                                        Toast.LENGTH_LONG).show();
                                                loading.hide();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterUser.this, "Failed to register",
                                    Toast.LENGTH_LONG).show();
                            loading.hide();
                        }
                    }
                });


    }
}