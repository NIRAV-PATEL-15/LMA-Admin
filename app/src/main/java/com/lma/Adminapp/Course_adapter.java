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

public class Course_adapter extends RecyclerView.Adapter<Course_adapter.ViewHolder> {
    private ArrayList<Course_Model> c_array;
    private Context context;
    int lastpos = -1;
    private CourseClick courseClick;

    public Course_adapter(ArrayList<Course_Model> c_array, Context context, CourseClick courseClick) {
       this.c_array = c_array;
       this.context = context;
       this.courseClick = courseClick;
    }
    @NonNull
    @Override

    public Course_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Course_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setAnimation(holder.itemView,position);
        Course_Model course_model = c_array.get(position);
        holder.subname.setText("Name : "+course_model.getName());
        holder.subcode.setText("Code : "+course_model.getCode());
        holder.faculty.setText("Faculty : "+course_model.getFaculty());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        courseClick.onCOurseClick(position);
    }
});

    }

    @Override
    public int getItemCount() {
        return c_array.size();
    }
    private void setAnimation(View itemView, int position) {
        if(position > lastpos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }
    }

public interface CourseClick{
        void onCOurseClick(int position);
}

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView faculty,subname,subcode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            faculty = itemView.findViewById(R.id.mc_fac_name);
            subcode = itemView.findViewById(R.id.mc_sub_code);
            subname = itemView.findViewById(R.id.mc_sub_name);
        }
    }
}
