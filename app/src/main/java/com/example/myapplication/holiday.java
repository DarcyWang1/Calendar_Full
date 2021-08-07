package com.example.myapplication;
import java.util.HashMap;

public abstract class holiday implements java.io.Serializable{
    protected String name;
    protected java.time.Month month;
    protected int id;
    public java.time.Month getMonth() {
        return month;
    }
    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }
    public HashMap<String,Object> getInfo(int year){
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("Name",this.name);
        result.put("Date",this.getDate(year));
        return result;
    }
    public abstract java.time.LocalDate getDate(int year);
}
