package com.example.myapplication;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeTableManager {
    public static final String timeTableFile="timetable";
    public HashMap<Integer,TimeTable> allTimeTables;
    public String dataFile;
    //public static TimeTable current;
    public TimeTableManager(String dataFile){
        this.dataFile=dataFile;
        allTimeTables= DataAccess.read(dataFile,timeTableFile);
        if(allTimeTables==null){
            allTimeTables=new HashMap<Integer,TimeTable>();
        }
        //ArrayList<TimeTable> b = dataHelper.readAll(TimeTableManager.timeTableFolder);
        //allTimeTables = new HashMap<Integer,TimeTable>();
        //for(TimeTable t : b){
        //    allTimeTables.put(t.getId(), t);
        //}
    }
    public TimeTableManager(ArrayList<TimeTable> input){
        allTimeTables = new HashMap<Integer,TimeTable>();
        for(TimeTable t : input){
            allTimeTables.put(t.getId(), t);
        }
    }
    public void addEvent(int timeTableId,java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text){
        if(allTimeTables.containsKey(timeTableId)) {
            allTimeTables.get(timeTableId).addEvent(startTime, endTime, name, text);
        }
    }
    public void removeEvent(int timeTableId,int eventId){
        if(allTimeTables.containsKey(timeTableId)) {
            allTimeTables.get(timeTableId).removeEvent(eventId);
        }
    }
    public void fixEvent(int timeTableId, int eventid, java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text){
        if(allTimeTables.containsKey(timeTableId)){
            allTimeTables.get(timeTableId).changeEvent(eventid,startTime,endTime,name,text);
        }
    }
    public ArrayList<event> getEventOnDate(int timeTableId, java.time.LocalDate date){
        if(allTimeTables.containsKey(timeTableId)){
            allTimeTables.get(timeTableId).getEventsOnDate(date);
        }
        return new ArrayList<event>();
    }
    public void addTimeTable(String name){
        int a = allTimeTables.size();
        for(int i=0;i<=a;i++){
            if(!(allTimeTables.containsKey(i))){
                allTimeTables.put(i,new TimeTable(name,i));
                break;
            }
        }
    }
    public HashMap<java.time.LocalDate,ArrayList<HashMap<String,Object>>> eventsInfoOnMonth(int timTableId,int year,java.time.Month m){
        if(allTimeTables.containsKey(timTableId)){
            return allTimeTables.get(timTableId).eventsInfoOnMonth(year,m);
        }
        return new HashMap<java.time.LocalDate,ArrayList<HashMap<String,Object>>>();
    }
    public void saveToFile(){
        DataAccess.writeToFile(dataFile,timeTableFile,allTimeTables);
        //ArrayList<String> timeTableName = new ArrayList<String>();
        //ArrayList<java.io.Serializable> input = new ArrayList<java.io.Serializable>();
        //for(int i: this.allTimeTables.keySet()){
        //    timeTableName.add(""+i);
        //    input.add(allTimeTables.get(i));
        //}
        //dataHelper.saveAll(timeTableFolder,timeTableName,input);
    }
    public ArrayList<HashMap<String,Object>> eventsInfoOnDate(int timTableId, LocalDate d){
        if(allTimeTables.containsKey(timTableId)){
            return allTimeTables.get(timTableId).getEventInFoOnDate(d);
        }
        return new ArrayList<HashMap<String,Object>>();
    }
    public HashMap<Integer,String> getTimeTableNames(){
        HashMap<Integer,String> result = new HashMap<Integer,String>();
        for(int i:allTimeTables.keySet()){
            result.put(i,allTimeTables.get(i).getName());
        }
        return result;
    }
    public void setTimetableName(int id,String name){
        if(allTimeTables.containsKey(id)){
            allTimeTables.get(id).setName(name);
        }
    }
    public void removeTimetable(int id){
        if(allTimeTables.containsKey(id)){
            allTimeTables.remove(id);
        }
    }
}
