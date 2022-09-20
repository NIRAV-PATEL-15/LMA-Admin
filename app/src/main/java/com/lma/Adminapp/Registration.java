package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    private TextInputLayout fullname_var, username_var, email_var, pass_var, cpass_var, dob_var, phone_var, graduation_var;
    private RadioButton rm;
    private Button register;
    private ImageView pp;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        pp = findViewById(R.id.profile);
        fullname_var = findViewById(R.id.fullname_field);
        username_var = findViewById(R.id.username_field);
        email_var = findViewById(R.id.email_field);
        pass_var = findViewById(R.id.pass_field);
        cpass_var = findViewById(R.id.cpass_field);
        dob_var = findViewById(R.id.dob_field);
        phone_var = findViewById(R.id.phn_field);
        graduation_var = findViewById(R.id.graduation_field);
        rm = findViewById(R.id.rg_male);
        register = findViewById(R.id.reg_btn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db = firebaseDatabase.getReference("Teachers");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateData();
            }
        });

    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }





    //to validate the data
    private void ValidateData() {
        String fullname_ = fullname_var.getEditText().getText().toString();
        String username_ = username_var.getEditText().getText().toString();
        String email_ = email_var.getEditText().getText().toString();
        String pass_ = pass_var.getEditText().getText().toString();
        String cpass = cpass_var.getEditText().getText().toString();
        String dob_ = dob_var.getEditText().getText().toString();
        String phn_ = phone_var.getEditText().getText().toString();
        String grd_ = graduation_var.getEditText().getText().toString();
        String gender_ = "";
        if (rm.isChecked()) {
            gender_ = "male";
        } else {
            gender_ = "female";
        }

        if (!fullname_.isEmpty()) {
            fullname_var.setError(null);
            fullname_var.setErrorEnabled(false);
            if (!username_.isEmpty()) {
                username_var.setError(null);
                username_var.setErrorEnabled(false);
                if (!email_.isEmpty()) {
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);
                    if (!pass_.isEmpty()) {
                        pass_var.setError(null);
                        pass_var.setErrorEnabled(false);
                        if (!cpass.isEmpty()) {
                            cpass_var.setError(null);
                            cpass_var.setErrorEnabled(false);
                            if (!dob_.isEmpty()) {
                                dob_var.setError(null);
                                dob_var.setErrorEnabled(false);
                                if (!phn_.isEmpty()) {
                                    phone_var.setError(null);
                                    phone_var.setErrorEnabled(false);
                                    if (!grd_.isEmpty()) {
                                        graduation_var.setError(null);
                                        graduation_var.setErrorEnabled(false);
                                        //Upload data into firebase

                                        uploadTofirebase();
                                    } else {
                                        graduation_var.setError("Please Enter Your Graduation");
                                    }

                                } else {
                                    phone_var.setError("Please Enter Your Phone-Number");
                                }

                            } else {
                                dob_var.setError("Please Enter Yout Date Of Birth");
                            }

                        } else {
                            cpass_var.setError("Please Enter Your Confirm Password");
                        }

                    } else {
                        pass_var.setError("Please Enter Your Password");
                    }

                } else {
                    email_var.setError("Please Enter Your Email");
                }

            } else {
                username_var.setError("Please Enter Your Username");
            }

        } else {
            fullname_var.setError("Please Enter Your Full-Name");
        }
    }

    private void uploadTofirebase() {
        String fullname = fullname_var.getEditText().getText().toString();
        String username = username_var.getEditText().getText().toString();
        String email = email_var.getEditText().getText().toString();
        String pass = pass_var.getEditText().getText().toString();
        String gender = "";
        rm = findViewById(R.id.rg_male);
        if (rm.isChecked()) {
            gender = "male";
        } else {
            gender = "female";
        }
        String dob = dob_var.getEditText().getText().toString();
        String phn = phone_var.getEditText().getText().toString();
        String grd = graduation_var.getEditText().getText().toString();
        String id = username;
        User_model rg = new User_model(fullname, username, email, pass, gender, dob, phn, grd);
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Registration.this, "User Created", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String  uid = user.getUid();
                    db.child(uid).setValue(rg);
                    Toast.makeText(Registration.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registration.this, Login.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Registration.this, "User creation failed", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


}