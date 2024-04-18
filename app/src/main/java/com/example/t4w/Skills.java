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

public class Skills extends RecyclerView.Adapter<Skills.SkillItem>{
    List<Skill> listSkill = new ArrayList<>();

    Context context;
    Gateway gateway;
    SQLiteDatabase database;

    public Skills(Context context, Gateway gateway, SQLiteDatabase database){
        this.context = context;
        this.gateway = gateway;
        this.database = database;

        getSkills();
    }

    @NonNull
    @Override
    public SkillItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iskill, parent, false);
        return new SkillItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillItem holder, int position){
        Skill skill = listSkill.get(position);

        holder.sName.setText(skill.getName());
        holder.sDesc.setText(skill.getDesc());

        try{
            InputStream input = context.getAssets().open(skill.getPic());
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            holder.sPic.setImageBitmap(bitmap);
            input.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return listSkill.size();
    }

    public static class SkillItem extends RecyclerView.ViewHolder{
        ImageView sPic;
        TextView sName, sDesc;

        public SkillItem(@NonNull View itemView){
            super(itemView);

            sName = itemView.findViewById(R.id.sName);
            sDesc = itemView.findViewById(R.id.sDesc);
            sPic = itemView.findViewById(R.id.sPic);
        }
    }

    private void getSkills(){
        String query = "SELECT * FROM skill";
        Cursor cursor = gateway.getData(database, query);

        if(cursor != null && cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String desc = cursor.getString((cursor.getColumnIndex("desc")));
                @SuppressLint("Range") String pic = cursor.getString(cursor.getColumnIndex("pic"));

                listSkill.add(new Skill(id, name, desc, pic));
            }
            while(cursor.moveToNext());
            cursor.close();
        }
    }
}
