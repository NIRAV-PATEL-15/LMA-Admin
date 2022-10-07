package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Timetable_Display extends AppCompatActivity implements Timetable_Adapter.TimetableClick {
    private RecyclerView rv;
    private FloatingActionButton fab;
    private FirebaseDatabase fdb;
    private MaterialButton day_btn;
    private DatabaseReference dref;
    private ArrayList<ttHolder> ttHolderArrayList;
    private LinearLayout bottom;
    private Timetable_Adapter tt_adapter;
    private Spinner ttd_day;
    private ProgressBar loading;
    TextView lec, subname, subcode, faculty, time;

    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_display);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Time-Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        rv = findViewById(R.id.ttRV);
        fab = findViewById(R.id.floating_add);
        fdb = FirebaseDatabase.getInstance();
        bottom = findViewById(R.id.tt_bottom);
        ttd_day = findViewById(R.id.ttd_days);


        loading = findViewById(R.id.tt_loading);
        loading.setVisibility(View.VISIBLE);
        String day = ttd_day.getSelectedItem().toString();
        dref = fdb.getReference("Time-Table").child(day);

        ttHolderArrayList = new ArrayList<>();
        tt_adapter = new Timetable_Adapter(ttHolderArrayList, this, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(tt_adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Timetable_Display.this, Add_TimeTable.class));
            }
        });
        getallData();

    }

    private void getallData() {
        ttHolderArrayList.clear();
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                ttHolderArrayList.add(snapshot.getValue(ttHolder.class));
                tt_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                tt_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loading.setVisibility(View.GONE);
                tt_adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loading.setVisibility(View.GONE);
                tt_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);
                tt_adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onTTclick(int position) {
        displayBottom(ttHolderArrayList.get(position));

    }

    private void displayBottom(ttHolder ttHolder) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.item_timetable_bottomsheet, bottom);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        edit = layout.findViewById(R.id.ttdb_edit);
        lec = layout.findViewById(R.id.ttdb_lecno);
        subname = layout.findViewById(R.id.ttdb_sub_name);
        subcode = layout.findViewById(R.id.ttdb_sub_code);
        faculty = layout.findViewById(R.id.ttdb_fac_name);
        time = layout.findViewById(R.id.ttdb_time);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Timetable_Display.this, Edit_TimeTable.class);
                i.putExtra("data", ttHolder);
                startActivity(i);
                finishAndRemoveTask();

            }
        });
        lec.setText(ttHolder.getLec_no());
        subname.setText("Sub : "+ttHolder.getSub_name());
        subcode.setText("Code : "+ttHolder.getSub_code());
        faculty.setText("Faculty : "+ttHolder.getFaculty());
        time.setText("Time : "+ttHolder.getTime());



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}