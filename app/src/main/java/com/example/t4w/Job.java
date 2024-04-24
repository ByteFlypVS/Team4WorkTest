package com.example.t4w;

public class Job{
    int id, eID;
    String name, desc, address, manager, eName, eWeb, ePic;
    long salary, lat, lng;

    public Job(int id, int eID, String name, String desc, String address, String manager, String eName, String eWeb, String ePic, long salary, long lat, long lng){
        this.id = id;
        this.eID = eID;
        this.name = name;
        this.desc = desc;
        this.address = address;
        this.manager = manager;
        this.eName = eName;
        this.eWeb = eWeb;
        this.ePic = ePic;
        this.salary = salary;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int geteID(){
        return eID;
    }

    public void seteID(int eID){
        this.eID = eID;
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

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getManager(){
        return manager;
    }

    public void setManager(String manager){
        this.manager = manager;
    }

    public String geteName(){
        return eName;
    }

    public void seteName(String eName){
        this.eName = eName;
    }

    public String geteWeb(){
        return eWeb;
    }

    public void seteWeb(String eWeb){
        this.eWeb = eWeb;
    }

    public String getePic(){
        return ePic;
    }

    public void setePic(String ePic){
        this.ePic = ePic;
    }

    public long getSalary(){
        return salary;
    }

    public void setSalary(long salary){
        this.salary = salary;
    }

    public long getLat(){
        return lat;
    }

    public void setLat(long lat){
        this.lat = lat;
    }

    public long getLng(){
        return lng;
    }

    public void setLng(long lng){
        this.lng = lng;
    }
}