package com.example.t4w;

public class Interest{
    int id;
    String name, desc, pic;

    public Interest(int id, String name, String desc, String pic){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.pic = pic;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getPic(){
        return pic;
    }

    public void setPic(String pic){
        this.pic = pic;
    }
}
