package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Registration extends AppCompatActivity {

    private TextInputLayout fullname_var, username_var, email_var, pass_var, cpass_var, dob_var, phone_var, graduation_var;
    private RadioButton rm;
    private Button register;
    private ImageView pp;
    private Uri uri;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private Bitmap bmp;
    private final int REQ = 1;

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
        registerForContextMenu(pp);
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //adding contextmenu to upload image
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addimage, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.u_pp:
                opengallery();
                return true;


            default:
                return super.onContextItemSelected(item);
        }
    }

    // To get image from gallery and upload image in imageview
    private void opengallery() {
        Intent pick_image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pick_image, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            uri = data.getData();

            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            pp.setImageBitmap(bmp);
        }
    }

    //to validate the data
    private void ValidateData() {
        String fullname_ = fullname_var.getEditText().getText().toString();
        String username_ = username_var.getEditText().getText().toString();
        String email_ = email_var.getEditText().getText().toString();
        String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
        String phn_pttn = "[6-9][0-9]{9}";
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

        if (!TextUtils.isEmpty(fullname_)){
            fullname_var.setError(null);
            fullname_var.setErrorEnabled(false);
            if (!TextUtils.isEmpty(username_)){
                username_var.setError(null);
                username_var.setErrorEnabled(false);
                if (!TextUtils.isEmpty(email_) && email_.matches(email_pttn) ){
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);
                    if ( pass_.length() >= 6 ){
                        pass_var.setError(null);
                        pass_var.setErrorEnabled(false);
                        if (!TextUtils.isEmpty(cpass) && cpass.equals(pass_)){
                            cpass_var.setError(null);
                            cpass_var.setErrorEnabled(false);
                            if (!TextUtils.isEmpty(dob_)){
                                dob_var.setError(null);
                                dob_var.setErrorEnabled(false);
                                if (!TextUtils.isEmpty(phn_) && phn_.matches(phn_pttn)){
                                    phone_var.setError(null);
                                    phone_var.setErrorEnabled(false);
                                    if (!TextUtils.isEmpty(grd_)){
                                        graduation_var.setError(null);
                                        graduation_var.setErrorEnabled(false);
                                        //Upload data into firebase

                               uploadTofirebase();
                                    }else {
                                        graduation_var.setError("Please Enter Your Graduation");
                                    }

                                }else {
                                    phone_var.setError("Invalid Phone number");
                                }

                            }else {
                                dob_var.setError("Please Enter Yout Date Of Birth");
                            }

                        }else {
                            cpass_var.setError("Invalid Confirm Password");
                        }

                    }else {
                        pass_var.setError("Password must be atleast 6 letters");
                    }

                }else {
                    email_var.setError("Invalid Email Address");
                }

            }else {
                username_var.setError("Please Enter Your Username");
            }

        }else{
            fullname_var.setError("Please Enter Your Full-Name");
        }
    }

    private void uploadTofirebase() {
        String fullname = fullname_var.getEditText().getText().toString();
        String username = username_var.getEditText().getText().toString();
        String email = email_var.getEditText().getText().toString();
        String pass = pass_var.getEditText().getText().toString();
        String gender ="";
        rm = findViewById(R.id.rg_male);
        if (rm.isChecked()){
            gender = "male";
        }else {
            gender = "female";
        }
        String dob = dob_var.getEditText().getText().toString();
        String phn = phone_var.getEditText().getText().toString();
        String grd = graduation_var.getEditText().getText().toString();
        String id = username;
        RegDataHolder rg = new RegDataHolder(fullname, username, email, pass, gender, dob, phn, grd);
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