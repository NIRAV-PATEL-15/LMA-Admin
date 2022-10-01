package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Edit_TimeTable extends AppCompatActivity {
    Spinner sday, stime;
    private EditText lno, sub_name, sub_code, fac_name;
    private MaterialButton update, delete;
    private FirebaseDatabase db;
    private String id;
    private DatabaseReference dref;
    private ttHolder ttHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_table);
        //SupportActonbar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Time-Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        //Spinner configs
        sday = (Spinner) findViewById(R.id.sday);
        ArrayAdapter<CharSequence> a1 = ArrayAdapter.createFromResource(this,
                R.array.days, R.layout.branch_spinner);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sday.setAdapter(a1);
        stime = (Spinner) findViewById(R.id.stime);
        ArrayAdapter<CharSequence> a2 = ArrayAdapter.createFromResource(this,
                R.array.time, R.layout.branch_spinner);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stime.setAdapter(a2);
        //getting id's of textviews
        lno = findViewById(R.id.tt_lec_no);
        sub_name = findViewById(R.id.tt_sub_name);
        sub_code = findViewById(R.id.tt_sub_code);
        fac_name = findViewById(R.id.tt_faculty_name);
        update = findViewById(R.id.update_tt_btn);
        delete = findViewById(R.id.delete_tt_btn);
        // Setting up data into the Edit-text boxes
        ttHolder = getIntent().getParcelableExtra("data");
        if (ttHolder != null) {
            lno.setText(ttHolder.getLec_no());
            sub_code.setText(ttHolder.getSub_code());
            sub_name.setText(ttHolder.getSub_name());
            fac_name.setText(ttHolder.getFaculty());
            id = ttHolder.getDay();
        }
//getting instance of database
        db = FirebaseDatabase.getInstance();
        dref = db.getReference("Time-Table").child(id).child(lno.getText().toString());
        //update btn event listner
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    //update function
    private void Update() {
//Getting the data into variables
        String lec_no = lno.getText().toString();
        String subj_name = sub_name.getText().toString();
        String subj_code = sub_code.getText().toString();
        String faculty = fac_name.getText().toString();
        String day = sday.getSelectedItem().toString();
        String time = stime.getSelectedItem().toString();
//mapping data into hashmap
        Map<String, Object> map = new HashMap<>();
        map.put("lec_no", lec_no);
        map.put("sub_name", subj_name);
        map.put("sub_code", subj_code);
        map.put("faculty", faculty);
        map.put("time", time);
        map.put("day", day);
        //value event listener
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dref.updateChildren(map);
                Toast.makeText(Edit_TimeTable.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Edit_TimeTable.this, TT_display.class));
                finishAndRemoveTask();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Edit_TimeTable.this, "Updation failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Delete function
    private void delete() {
        dref.removeValue();
        Toast.makeText(Edit_TimeTable.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Edit_TimeTable.this, TT_display.class));
        finishAndRemoveTask();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}