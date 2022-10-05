package com.lma.Adminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import javax.security.auth.Subject;

public class Add_chapter extends AppCompatActivity {
    private TextInputLayout title_field;
    private TextInputEditText title, pdf_file;
    private Button upload;
    private FirebaseDatabase db;
    private DatabaseReference dref;
    private StorageReference storageReference;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Chapter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.bg));
        title = findViewById(R.id.ac_title);
        title_field = findViewById(R.id.title_field);
        pdf_file = findViewById(R.id.ac_file);
        upload = findViewById(R.id.upload_btn);
        db = FirebaseDatabase.getInstance();
        String Subject = getIntent().getStringExtra("sub");
        dref = db.getReference("Courses").child(Subject).child("content");
        storageReference = FirebaseStorage.getInstance().getReference();
        upload.setEnabled(false);

        pdf_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPdf();

            }


        });


    }

    private void SelectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF File Select"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            upload.setEnabled(true);
            pdf_file.setText(title.getText().toString());
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadpdfFirebase(data.getData());
                }
            });
        }
    }

    private void uploadpdfFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is uploading.....");
        progressDialog.show();
        String Subject = getIntent().getStringExtra("sub");
        db = FirebaseDatabase.getInstance();
        dref = db.getReference("Courses").child(Subject).child("content");
        title = findViewById(R.id.ac_title);
        String name = title.getText().toString();
        StorageReference reference = storageReference.child("Chapters").child(Subject).child(name);
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Chapter_Model chapter_model = new Chapter_Model(name,uri.toString());
                        dref.child(title.getText().toString()).setValue(chapter_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Add_chapter.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                showdialog();

                            }

                            private void showdialog() {
                                builder.setTitle("URL").setIcon(R.drawable.nav_mp);
                                builder.setMessage(uri.toString());
                                builder.setCancelable(true)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();

                                            }
                                        });
                                builder.show();
                            }
                        });
                    }
                });

//                dref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                        Toast.makeText(Add_chapter.this, "Chapter Uploaded", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Add_chapter.this, Chapters.class));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("uploaded...." + (int) progress + "%");
            }
        });
    }


}