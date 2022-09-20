package com.lma.Adminapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Student_adapter extends RecyclerView.Adapter<Student_adapter.ViewHolder> {
    private ArrayList<Student_Model> studentModelArrayList;
    private Context context;
    int lastPos = -1;
    private addstudentsClickInterface addstudentsClickInterface;

    public Student_adapter(ArrayList<Student_Model> studentModelArrayList, Context context, Student_adapter.addstudentsClickInterface addstudentsClickInterface) {
        this.studentModelArrayList = studentModelArrayList;
        this.context = context;
        this.addstudentsClickInterface = addstudentsClickInterface;
    }

    @NonNull
    @Override
    public Student_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.addstudents_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Student_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Student_Model Student_Model = studentModelArrayList.get(position);
        holder.Fullname.setText(Student_Model.getFullname());
        holder.Enrollmentno.setText(Student_Model.getEnrollmentno());
        holder.Semester.setText("Semester: "+ Student_Model.getSemester());
        holder.Division.setText("Class: "+ Student_Model.getDivision());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addstudentsClickInterface.onstudentsClick(position);
            }
        });
    }

    private void setAnimation(View itemView,int position){
        if (position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }
    @Override
    public int getItemCount() {
        return studentModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Fullname,Enrollmentno,Semester,Division;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Fullname = itemView.findViewById(R.id.txtfullname);
            Enrollmentno = itemView.findViewById(R.id.txtenrollmentno);
            Semester = itemView.findViewById(R.id.txtsemester);
            Division = itemView.findViewById(R.id.txtdivision);
        }
    }

    public interface addstudentsClickInterface{
        void onstudentsClick(int position);
    }
}
