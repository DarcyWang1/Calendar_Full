package com.example.myapplication;
import java.time.LocalDateTime;
import java.util.HashMap;

public class event implements java.io.Serializable{
    private java.time.LocalDateTime startTime;
    private java.time.LocalDateTime endTime;
    private int id;
    private String name;
    private String text;
    public event(java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,int id,String name,String text){
        this.startTime=startTime;
        this.endTime=endTime;
        this.id=id;
        this.name=name;
        this.text=text;
    }
    public void setName(String newName){
        this.name=newName;
    }
    public void setText(String newText){
        this.text=newText;
    }
    public void setTime(java.time.LocalDateTime newStartTime, java.time.LocalDateTime newEndTime){
        this.startTime=newStartTime;
        this.endTime=newEndTime;
    }
    public int getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getText(){
        return this.text;
    }
    public java.time.LocalDateTime getStartTime(){
        return this.startTime;
    }
    public java.time.LocalDateTime getEndTime(){
        return this.endTime;
    }
    public void setAll(java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text){
        this.setName(name);
        this.setText(text);
        this.setTime(startTime,endTime);
    }
    public HashMap<String,Object> getInfo(){
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("Name",this.name);
        result.put("Text", this.text);
        result.put("Start time",this.startTime);
        result.put("End time",this.endTime);
        result.put("id",this.id);
        return result;
    }

}
