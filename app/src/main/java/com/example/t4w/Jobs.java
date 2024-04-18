package com.example.t4w;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Jobs extends RecyclerView.Adapter<Jobs.JobItem>{
    List<Job> listJob = new ArrayList<>();

    Context context;
    Gateway gateway;
    SQLiteDatabase database;

    public Jobs(Context context, Gateway gateway, SQLiteDatabase database){
        this.context = context;
        this.gateway = gateway;
        this.database = database;

        getJobs();
    }

    @NonNull
    @Override
    public JobItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ijob, parent, false);
        return new JobItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobItem holder, int position){
        Job job = listJob.get(position);

        holder.jName.setText(job.getName());
        holder.jEmployer.setText(job.geteName());

        try{
            InputStream input = context.getAssets().open(job.getePic());
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            holder.jPic.setImageBitmap(bitmap);
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return listJob.size();
    }

    public static class JobItem extends RecyclerView.ViewHolder{
        ImageView jPic;
        TextView jName, jEmployer;

        public JobItem(@NonNull View itemView){
            super(itemView);

            jName = itemView.findViewById(R.id.jName);
            jEmployer = itemView.findViewById(R.id.jEmployer);
            jPic = itemView.findViewById(R.id.jPic);
        }
    }

    private void getJobs(){
        String query = "SELECT employer.name eName, employer.website eWeb, employer.pic ePic, " +
                "job.id id, job.eID eID, job.name name, job.desc desc, job.address address, " +
                "job.lat lat, job.long long, job.salary salary, job.manager manager " +
                "FROM job INNER JOIN employer ON job.eID = employer.id";
        Cursor cursor = gateway.getData(database, query);

        if(cursor != null && cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int eID = cursor.getInt(cursor.getColumnIndex("eID"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String desc = cursor.getString(cursor.getColumnIndex("desc"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String manager = cursor.getString(cursor.getColumnIndex("manager"));
                @SuppressLint("Range") String eName = cursor.getString(cursor.getColumnIndex("eName"));
                @SuppressLint("Range") String eWeb = cursor.getString(cursor.getColumnIndex("eWeb"));
                @SuppressLint("Range") String ePic = cursor.getString(cursor.getColumnIndex("ePic"));
                @SuppressLint("Range") long salary = cursor.getLong(cursor.getColumnIndex("salary"));
                @SuppressLint("Range") long lat = cursor.getLong(cursor.getColumnIndex("lat"));
                @SuppressLint("Range") long lng = cursor.getLong(cursor.getColumnIndex("long"));

                listJob.add(new Job(id, eID, name, desc, address, manager, eName, eWeb, ePic, salary, lat, lng));
            }
            while(cursor.moveToNext());
            cursor.close();
        }
    }
}
