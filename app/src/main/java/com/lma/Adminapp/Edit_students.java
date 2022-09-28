package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Edit_students extends AppCompatActivity {

    private Spinner branch_es;
    private RadioButton male_es,female_es;
    private TextInputLayout field_fullname,field_eno,field_email,field_password,field_cpassword,field_dob,field_phone,field_semester,field_division;
    private TextInputEditText fullname_txt,username_txt,email_txt,password_txt,cpassword_txt,dob_txt,phone_txt,semester_txt,division_txt;
    private Button update,delete;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Student_Model Student_Model;
    private ProgressBar loadingbar;
    private FirebaseAuth mAuth;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Student");
        loadingbar = findViewById(R.id.es_loading);
        //loadingbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));

        Spinner spinner = (Spinner) findViewById(R.id.es_branch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, R.layout.branch_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        fullname_txt = findViewById(R.id.es_fullname);
        username_txt = findViewById(R.id.es_username);
        email_txt = findViewById(R.id.es_email);
        password_txt = findViewById(R.id.es_password);
        cpassword_txt = findViewById(R.id.es_cpassword);
        phone_txt = findViewById(R.id.es_phone);
        dob_txt = findViewById(R.id.es_dob);
        semester_txt = findViewById(R.id.es_semester);
        division_txt = findViewById(R.id.es_division);
        division_txt = findViewById(R.id.es_division);
        male_es = findViewById(R.id.es_male);
        female_es = findViewById(R.id.es_female);
        branch_es = findViewById(R.id.es_branch);
        update = findViewById(R.id.es_update_btn);
        delete = findViewById(R.id.es_delete_btn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dob_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Edit_students.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month = month + 1;
                String date = dayofMonth + "/" + month + "/" + year;
                dob_txt.setText(date);

            }
        };

        Student_Model = getIntent().getParcelableExtra("Students");
        if (Student_Model != null) {
            fullname_txt.setText(Student_Model.getFullname());
            username_txt.setText(Student_Model.getUsername());
            email_txt.setText(Student_Model.getEmail());
            password_txt.setText(Student_Model.getPassword());
            cpassword_txt.setText(Student_Model.getPassword());

            String gender = Student_Model.getGender();
            if (gender.equals("Male")){
                male_es.setChecked(true);
            }
            else {
                female_es.setChecked(true);
            }
            dob_txt.setText(Student_Model.getDob());
            phone_txt.setText(Student_Model.getPhone());
            semester_txt.setText(Student_Model.getSemester());
            division_txt.setText(Student_Model.getDivision());

        }
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getReference("Students").child(userid);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateDate();
            }

            private void ValidateDate() {

                field_fullname = findViewById(R.id.es_fullname_field);
                field_eno = findViewById(R.id.es_username_field);
                field_email = findViewById(R.id.es_email_field);
                field_password = findViewById(R.id.es_password_field);
                field_cpassword = findViewById(R.id.es_cpassword_field);
                field_dob = findViewById(R.id.es_dob_field);
                field_phone = findViewById(R.id.es_phone_field);
                field_semester = findViewById(R.id.es_semester_field);
                field_division = findViewById(R.id.es_division_field);


                String fullname = field_fullname.getEditText().getText().toString();
                String username = field_eno.getEditText().getText().toString();
                String email = field_email.getEditText().getText().toString();
                String password = field_password.getEditText().getText().toString();
                String cpassword = field_cpassword.getEditText().getText().toString();
                String dob = field_dob.getEditText().getText().toString();
                String phone = field_phone.getEditText().getText().toString();
                String semester = field_semester.getEditText().getText().toString();
                String division = field_division.getEditText().getText().toString();
                String branch = branch_es.getSelectedItem().toString();
                String gender_ = "";
                if (male_es.isChecked()) {
                    gender_ = "male";
                } else {
                    gender_ = "female";
                }
                String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
                String phone_pttn = "[6-9][0-9]{9}";
                String semester_pttn = "[1-8]";
                String division_pttn = "^[A-Z]{2}+(-)[A-Z]{1}$";


                if (!TextUtils.isEmpty(fullname)) {
                    field_fullname.setError(null);
                    field_fullname.setErrorEnabled(false);
                    if (!TextUtils.isEmpty(username) && username.length() <= 12) {
                        field_eno.setError(null);
                        field_eno.setErrorEnabled(false);
                        if (!TextUtils.isEmpty(email) && email.matches(email_pttn)) {
                            field_email.setError(null);
                            field_email.setErrorEnabled(false);
                            if (!TextUtils.isEmpty(password) && password.length() <= 10 && password.length() >= 6) {
                                field_password.setError(null);
                                field_password.setErrorEnabled(false);
                                if (!TextUtils.isEmpty(cpassword) && cpassword.matches(password)) {
                                    field_cpassword.setError(null);
                                    field_cpassword.setErrorEnabled(false);
                                    if (!TextUtils.isEmpty(dob)) {
                                        field_dob.setError(null);
                                        field_dob.setErrorEnabled(false);
                                        if (!TextUtils.isEmpty(phone) && phone.matches(phone_pttn)) {
                                            field_phone.setError(null);
                                            field_phone.setErrorEnabled(false);
                                            if (!TextUtils.isEmpty(semester) && semester.matches(semester_pttn)) {
                                                field_semester.setError(null);
                                                field_semester.setErrorEnabled(false);
                                                if (!TextUtils.isEmpty(division) && division.matches(division_pttn)) {
                                                    field_division.setError(null);
                                                    field_division.setErrorEnabled(false);
                                                    if (!branch.equals("Select Branch")) {
                                                        upload();
                                                    } else {
                                                        Toast.makeText(Edit_students.this, "Please select a branch", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    field_division.setError("Invalid Class");
                                                }
                                            } else {
                                                field_semester.setError("Semester Must be 1-8");
                                            }
                                        } else {
                                            field_phone.setError("Invalid Phone Number");
                                        }
                                    } else {
                                        field_dob.setError("Please Enter Your Date Of Birth");
                                    }
                                } else {
                                    field_cpassword.setError("Password did not Match");
                                }
                            } else {
                                field_password.setError("Password Must Be 6-8");
                            }
                        } else {
                            field_email.setError("Invalid Email Address");
                        }
                    } else {
                        field_eno.setError("Invalid Enrollment Number");
                    }
                } else {
                    field_fullname.setError("Please Enter Your Fullname");
                }
            }
        });
    }



    private void upload() {
        String fullname = fullname_txt.getText().toString();
        String username = username_txt.getText().toString();
        String email = email_txt.getText().toString();
        String password = password_txt.getText().toString();
        String confirmpassword = cpassword_txt.getText().toString();
        String dob = dob_txt.getText().toString();
        String phone = phone_txt.getText().toString();
        String semester = semester_txt.getText().toString();
        String division = division_txt.getText().toString();
        String branch = branch_es.getSelectedItem().toString();
        String gender = "";
        if (male_es.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("username", username);
        map.put("email", email);
        map.put("password", password);
        map.put("gender", gender);
        map.put("dob", dob);
        map.put("phone", phone);
        map.put("semester", semester);
        map.put("division", division);
        map.put("branch", branch);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.updateChildren(map);
                loadingbar.setVisibility(View.VISIBLE);
                Toast.makeText(Edit_students.this, "Students Details Updated..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Edit_students.this, Students_list.class));
                finishAndRemoveTask();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingbar.setVisibility(View.VISIBLE);
                Toast.makeText(Edit_students.this, "Fail to Update Students Details..", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletestudents();
            }

            public void deletestudents() {
                databaseReference.removeValue();
                //loadingbar.setVisibility(View.VISIBLE);
                Toast.makeText(Edit_students.this, "Students Deleted..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Edit_students.this, Students_list.class));
                finishAndRemoveTask();
            }
        });


    }




}