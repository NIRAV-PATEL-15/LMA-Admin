package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Edit_profile extends AppCompatActivity {

    private TextInputEditText user,fname,email,dob,phn,gradu;
    private TextInputLayout fullname_var, username_var, email_var, pass_var, cpass_var, dob_var, phone_var, graduation_var;
    private RadioButton rm,rf;
    private MaterialButton ep;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private  RegDataHolder rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));

        user = findViewById(R.id.ep_username);
        fname = findViewById(R.id.ep_fullname);
        email = findViewById(R.id.ep_email);
        rm = findViewById(R.id.ep_male);
        rf = findViewById(R.id.ep_female);
        dob = findViewById(R.id.ep_dob);
        phn = findViewById(R.id.ep_phn);
        gradu = findViewById(R.id.ep_gradu);

        fullname_var = findViewById(R.id.ep_fullname_field);
        username_var = findViewById(R.id.ep_username_field);
        email_var = findViewById(R.id.ep_email_field);
        dob_var = findViewById(R.id.ep_dob_field);
        phone_var = findViewById(R.id.ep_phn_field);
        graduation_var = findViewById(R.id.ep_graduation_field);


        ep = findViewById(R.id.mp_update_btn);

Intent i = getIntent();
user.setText(i.getStringExtra("username"));
fname.setText(i.getStringExtra("fname"));
email.setText(i.getStringExtra("email"));

        String gender = i.getStringExtra("gender");
        if(gender.equals("male")){
            rm.setChecked(true);
        }
        else {
            rf.setChecked(true);
        }

        dob.setText(i.getStringExtra("dob"));
        phn.setText(i.getStringExtra("contact"));
        gradu.setText(i.getStringExtra("graduation"));
        String userid = i.getStringExtra("uid");
        firebaseDatabase = FirebaseDatabase.getInstance();

        db = firebaseDatabase.getReference("Teachers").child(userid);

    ep.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

validateData();

    }

    private void validateData() {
        String fullname,username,email,gender_,dob,cno,graduation;
        fullname = fullname_var.getEditText().getText().toString();
        username = username_var.getEditText().getText().toString();
        String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
        String phn_pttn = "[6-9][0-9]{9}";
        email = email_var.getEditText().getText().toString();
        graduation = graduation_var.getEditText().getText().toString();
        gender_ ="";
        if (rm.isChecked()){
            gender_ = "male";
        }else {
            gender_ = "female";
        }
        dob = dob_var.getEditText().getText().toString();
        cno = phone_var.getEditText().getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("email",email);
        map.put("fullname",fullname);
        map.put("username",username);
        map.put("gender",gender_);
        map.put("graduation",graduation);
        map.put("cno",cno);
        map.put("dob",dob);

         if(!TextUtils.isEmpty(username)){
         username_var.setError(null);
         username_var.setErrorEnabled(false);
             if(!TextUtils.isEmpty(fullname)){
                 fullname_var.setError(null);
                 fullname_var.setErrorEnabled(false);
                 if(!TextUtils.isEmpty(email) && email.matches(email_pttn)){
                     email_var.setError(null);
                     email_var.setErrorEnabled(false);
                     if(!TextUtils.isEmpty(dob)){
                         dob_var.setError(null);
                         dob_var.setErrorEnabled(false);
                         if(!TextUtils.isEmpty(cno) && cno.matches(phn_pttn)){
                             phone_var.setError(null);
                             phone_var.setErrorEnabled(false);
                             if(!TextUtils.isEmpty(graduation)){
                                 graduation_var.setError(null);
                                 graduation_var.setErrorEnabled(false);
                                 //update data
db.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        db.updateChildren(map);
        Toast.makeText(Edit_profile.this, "Updated successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Edit_profile.this,manage_profile.class));
        finish();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(Edit_profile.this, "Update failed", Toast.LENGTH_SHORT).show();
    }
});

                             }else {
                                 graduation_var.setError("Graduation cannot be empty");
                             }
                         }else {
                             phone_var.setError("Invalid phone number");
                         }
                     }else {
                         dob_var.setError("Date of Birth cannot be empty");
                     }
                 }else {
                     email_var.setError("Invalid email address");
                 }
             }else {
                 fullname_var.setError("Fullname cannot be empty");
             }
         }else {
             username_var.setError("username cannot be empty");
         }

    }




    });
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}