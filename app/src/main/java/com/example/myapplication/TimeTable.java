package com.example.myapplication;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeTable implements java.io.Serializable{
    private int id;
    private String name;
    private HashMap<java.time.LocalDate,ArrayList<event>> eventsForSearching;
    private ArrayList<event> events;
    private HashMap<Integer,event> idToEvent;
    public TimeTable(String name,int id){
        this.name=name;
        this.events=new ArrayList<event>();
        this.eventsForSearching=new HashMap<java.time.LocalDate,ArrayList<event>>();
        this.idToEvent=new HashMap<Integer,event>();
        this.id=id;
    }
    public void addEvent(java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text) {
        //System.out.print("aaa");
        int id=0;
        for(int i =0; i<=idToEvent.size();i++){
            if(!idToEvent.containsKey(i)){
                id=i;
                break;
            }
        }
        event e = new event(startTime, endTime, id, name, text);
        this.events.add(e);
        java.time.LocalDate t = startTime.toLocalDate();
        if (!eventsForSearching.containsKey(t)) {
            ArrayList<event> a = new ArrayList<event>();
            a.add(e);
            eventsForSearching.put(t, a);
        } else {
            ArrayList<event> a = eventsForSearching.get(t);
            a.add(e);
        }
        if (!idToEvent.containsKey(id)) {
            this.idToEvent.put(id, e);
        }
    }
    public void removeEvent(int eventId){
        if(idToEvent.containsKey(eventId)) {
            event e = idToEvent.get(eventId);
            if (events.contains(e)) {
                this.events.remove(e);
            }
            java.time.LocalDate t = e.getStartTime().toLocalDate();
            if (eventsForSearching.containsKey(t)) {
                ArrayList<event> a = eventsForSearching.get(t);
                a.remove(e);
            }
            this.idToEvent.remove(eventId);
        }

    }
    public ArrayList<HashMap<String,Object>> eventsInfoOnDay(java.time.LocalDate d){
        if(eventsForSearching.containsKey(d)){
            ArrayList<HashMap<String,Object>> result=new ArrayList<HashMap<String,Object>>();
            for (event e : eventsForSearching.get(d)){
                result.add(e.getInfo());
            }
            return result;
        }
        return null;
    }
    public HashMap<java.time.LocalDate,ArrayList<HashMap<String,Object>>> eventsInfoOnMonth(int year,java.time.Month m){
        HashMap<java.time.LocalDate,ArrayList<HashMap<String,Object>>> result = new HashMap<java.time.LocalDate,ArrayList<HashMap<String,Object>>>();
        for(java.time.LocalDate d: eventsForSearching.keySet() ){
            if(d.getMonth()==m&&d.getYear()==year){
                ArrayList<HashMap<String,Object>> eventsOnD=new ArrayList<HashMap<String,Object>>();
                for (event e : eventsForSearching.get(d)){
                    eventsOnD.add(e.getInfo());
                }
                result.put(d,eventsOnD);
            }
        }
        return result;
    }
    public void changeEvent(int eventId,java.time.LocalDateTime startTime,java.time.LocalDateTime endTime,String name,String text){
        if(idToEvent.containsKey(eventId)) {
            event e = idToEvent.get(eventId);
            eventsForSearching.get(e.getStartTime().toLocalDate()).remove(e);
            e.setAll(startTime,endTime,name,text);
            java.time.LocalDate t = startTime.toLocalDate();
            if (eventsForSearching.containsKey(t)) {
                eventsForSearching.get(t).add(e);
            }else {
                ArrayList<event> a = new ArrayList<event>();
                a.add(e);
                eventsForSearching.put(t, a);
            }
        }
    }

    public ArrayList<event> getEventsOnDate(java.time.LocalDate date) {
       if(eventsForSearching.containsKey(date)){
            return eventsForSearching.get(date);
        }
        return new ArrayList<event>();
    }
    public int getId(){
        return this.id;
    }
    public ArrayList<HashMap<String,Object>> getEventInFoOnDate(java.time.LocalDate date){
        ArrayList<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
        if(eventsForSearching.containsKey(date)){
            for(event i :eventsForSearching.get(date)){
                result.add(i.getInfo());
            }
        }
        return result;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
