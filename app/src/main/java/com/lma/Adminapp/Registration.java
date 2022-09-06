package com.lma.Adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    TextInputLayout fullname_var,username_var,email_var,pass_var,cpass_var,dob_var,phone_var,graduation_var;
    RadioButton rm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));

        fullname_var = findViewById(R.id.fullname_field);
        username_var = findViewById(R.id.username_field);
        email_var = findViewById(R.id.email_field);
        pass_var = findViewById(R.id.pass_field);
        cpass_var = findViewById(R.id.cpass_field);
        dob_var = findViewById(R.id.dob_field);
        phone_var = findViewById(R.id.phn_field);
        graduation_var = findViewById(R.id.graduation_field);
        rm = findViewById(R.id.rg_male);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void registerbtn(View view) {

        String fullname_ = fullname_var.getEditText().getText().toString();
        String username_ = username_var.getEditText().getText().toString();
        String email_ = email_var.getEditText().getText().toString();
        String pass_ = pass_var.getEditText().getText().toString();
        String cpass = cpass_var.getEditText().getText().toString();
        String dob_ = dob_var.getEditText().getText().toString();
        String phn_ = phone_var.getEditText().getText().toString();
        String grd_ = graduation_var.getEditText().getText().toString();
        String gender_ ="";
        if (rm.isChecked()){
             gender_ = "male";
        }else {
            gender_ = "female";
        }

        if (!fullname_.isEmpty()){
            fullname_var.setError(null);
            fullname_var.setErrorEnabled(false);
            if (!username_.isEmpty()){
                username_var.setError(null);
                username_var.setErrorEnabled(false);
                if (!email_.isEmpty()){
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);
                    if (!pass_.isEmpty()){
                        pass_var.setError(null);
                        pass_var.setErrorEnabled(false);
                        if (!cpass.isEmpty()){
                            cpass_var.setError(null);
                            cpass_var.setErrorEnabled(false);
                            if (!dob_.isEmpty()){
                                dob_var.setError(null);
                                dob_var.setErrorEnabled(false);
                                if (!phn_.isEmpty()){
                                    phone_var.setError(null);
                                    phone_var.setErrorEnabled(false);
                                    if (!grd_.isEmpty()){
                                        graduation_var.setError(null);
                                        graduation_var.setErrorEnabled(false);

                                    }else {
                                        graduation_var.setError("Please Enter Your Graduation");
                                    }

                                }else {
                                    phone_var.setError("Please Enter Your Phone-Number");
                                }

                            }else {
                                dob_var.setError("Please Enter Yout Date Of Birth");
                            }

                        }else {
                            cpass_var.setError("Please Enter Your Confirm Password");
                        }

                    }else {
                        pass_var.setError("Please Enter Your Password");
                    }

                }else {
                    email_var.setError("Please Enter Your Email");
                }

            }else {
                username_var.setError("Please Enter Your Username");
            }

        }else{
            fullname_var.setError("Please Enter Your Full-Name");
        }
    }
}