package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Add_TimeTable extends AppCompatActivity {
    private AutoCompleteTextView time_txt,day_txt;
    private TextInputEditText lno_txt,sub_name_txt,sub_code_txt,fac_name_txt;
    private TextInputLayout lno_field,sub_name_field,sub_code_field,fac_name_field,time_field,day_field;
    private MaterialButton add;
    private FirebaseDatabase db;
    private DatabaseReference dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Time-Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));

        lno_txt = findViewById(R.id.att_lec_no);
        sub_name_txt = findViewById(R.id.att_sub_name);
        sub_code_txt = findViewById(R.id.att_sub_code);
        fac_name_txt = findViewById(R.id.att_faculty_name);
        time_txt = findViewById(R.id.att_time);
        day_txt = findViewById(R.id.att_day);


        db =FirebaseDatabase.getInstance();
        dref = db.getReference("Time-Table");
        add = findViewById(R.id.add_tt_btn);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validate();
            }




        });

    }

    private void validate() {

        lno_field = findViewById(R.id.att_lec_no_field);
        sub_name_field = findViewById(R.id.att_sub_name_field);
        sub_code_field = findViewById(R.id.att_sub_code_field);
        fac_name_field = findViewById(R.id.att_faculty_name_field);
        time_field = findViewById(R.id.att_time_field);
        day_field = findViewById(R.id.att_day_field);

        String lec_no,sub_name,sub_code,faculty,time,day;
        lec_no = lno_field.getEditText().getText().toString();
        sub_name = sub_name_field.getEditText().getText().toString();
        sub_code = sub_code_field.getEditText().getText().toString();
        faculty = fac_name_field.getEditText().getText().toString();
        time = time_field.getEditText().getText().toString();
        day = day_field.getEditText().getText().toString();

        if(!TextUtils.isEmpty(lec_no)){
            lno_field.setError(null);
            lno_field.setErrorEnabled(false);
            if(!TextUtils.isEmpty(sub_name)){
                sub_name_field.setError(null);
                sub_name_field.setErrorEnabled(false);
                if(!TextUtils.isEmpty(sub_code)){
                    sub_code_field.setError(null);
                    sub_code_field.setErrorEnabled(false);
                    if(!TextUtils.isEmpty(faculty)){
                        fac_name_field.setError(null);
                        fac_name_field.setErrorEnabled(false);
                        if(!TextUtils.isEmpty(time)){
                            time_field.setError(null);
                            time_field.setErrorEnabled(false);
                            if(!TextUtils.isEmpty(day)){
                                day_field.setError(null);
                                day_field.setErrorEnabled(false);
                                StoreToFirebase();

                            }else{
                                day_field.setError("Please Select Your Day");                            }

                        }else{
                            time_field.setError("Please Select Your Time");                        }
                    }else{
                        fac_name_field.setError("Faculty name Cannot be empty");
                    }
                }else{
                    sub_code_field.setError("Code Cannot be empty");
                }
            }else{
                sub_name_field.setError("Name Cannot be empty");
            }
        }else{
            lno_field.setError("No Cannot be empty");
        }

    }

    private void StoreToFirebase() {
        String lec_no,sub_name,sub_code,faculty,time,day;
        lec_no = lno_txt.getText().toString();
        sub_name = sub_name_txt.getText().toString();
        sub_code = sub_code_txt.getText().toString();
        faculty = fac_name_txt.getText().toString();
        time = time_txt.getText().toString();
        day = day_txt.getText().toString();

        ttHolder ttHolder = new ttHolder(lec_no,sub_name,sub_code,faculty,time,day);
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dref.child(day).child(lec_no).setValue(ttHolder);
                Toast.makeText(Add_TimeTable.this, "Lecture Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Add_TimeTable.this, TT_display.class));
                finishAndRemoveTask();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            case R.id.dashboard:
                //Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Add_TimeTable.this,TT_display.class);
                startActivity(intent);
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