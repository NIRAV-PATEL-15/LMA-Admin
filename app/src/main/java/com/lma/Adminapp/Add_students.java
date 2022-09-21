package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Add_students extends AppCompatActivity {

    //Spinner addstudent_branch_spinner;
    private TextInputEditText fullnameedt,enrollmentnoedt,emailedt,passwordedt,cpasswordedt,dobedt,phoneedt,semesteredt,divisionedt;
    private Button addstudentsbtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String addstudentsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Add Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));

        Spinner spinner = (Spinner) findViewById(R.id.addstudent_branch_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, R.layout.branch_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fullnameedt = findViewById(R.id.as_fullname);
        enrollmentnoedt = findViewById(R.id.as_enrollmentno);
        emailedt = findViewById(R.id.as_email);
        passwordedt = findViewById(R.id.as_password);
        cpasswordedt = findViewById(R.id.as_cpassword);
        phoneedt = findViewById(R.id.as_phn);
        dobedt = findViewById(R.id.as_dob);
        semesteredt = findViewById(R.id.as_semester);
        divisionedt = findViewById(R.id.as_division);
        addstudentsbtn = findViewById(R.id.addstudent_btn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Add students");

        addstudentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = fullnameedt.getText().toString();
                String enrollmentno = enrollmentnoedt.getText().toString();
                String email = emailedt.getText().toString();
                String password = passwordedt.getText().toString();
                String confirmpassword = cpasswordedt.getText().toString();
                String dob = dobedt.getText().toString();
                String phone = phoneedt.getText().toString();
                String semester = semesteredt.getText().toString();
                String division = divisionedt.getText().toString();
                addstudentsID = enrollmentno;

                Student_Model Student_Model = new Student_Model(fullname,enrollmentno,email,password,confirmpassword,dob,phone,semester,division,addstudentsID);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(addstudentsID).setValue(Student_Model);
                        Toast.makeText(Add_students.this, "Students Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Add_students.this, Add_students.class));
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Add_students.this, "Error is "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            case R.id.dashboard:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this, "Help me", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }


}