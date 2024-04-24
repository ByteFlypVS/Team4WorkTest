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

public class Interests extends RecyclerView.Adapter<Interests.InterestItem>{
    List<Interest> listInterest = new ArrayList<>();

    Context context;
    Gateway gateway;
    SQLiteDatabase database;

    public Interests(Context context, Gateway gateway, SQLiteDatabase database){
        this.context = context;
        this.gateway = gateway;
        this.database = database;

        getInterests();
    }

    @NonNull
    @Override
    public InterestItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iinterest, parent, false);
        return new InterestItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestItem holder, int position){
        Interest interest = listInterest.get(position);

        holder.iName.setText(interest.getName());
        holder.iDesc.setText(interest.getDesc());

        try{
            InputStream input = context.getAssets().open(interest.getPic());
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            holder.iPic.setImageBitmap(bitmap);
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return listInterest.size();
    }

    public static class InterestItem extends RecyclerView.ViewHolder{
        ImageView iPic;
        TextView iName, iDesc;

        public InterestItem(@NonNull View itemView){
            super(itemView);

            iName = itemView.findViewById(R.id.iName);
            iDesc = itemView.findViewById(R.id.iDesc);
            iPic = itemView.findViewById(R.id.iPic);
        }
    }

    private void getInterests(){
        String query = "SELECT * FROM interest";
        Cursor cursor = gateway.getData(query);  // Updated to match the Gateway method signature

        if(cursor != null && cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String desc = cursor.getString((cursor.getColumnIndex("desc")));
                @SuppressLint("Range") String pic = cursor.getString(cursor.getColumnIndex("pic"));

                listInterest.add(new Interest(id, name, desc, pic));
            }
            while(cursor.moveToNext());
            cursor.close();
        }
    }
}
